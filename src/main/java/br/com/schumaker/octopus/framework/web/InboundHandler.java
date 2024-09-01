package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.exception.GlobalExceptionHandler;
import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.model.Pair;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.reflection.validation.ValidationReflection;
import br.com.schumaker.octopus.framework.web.handler.GetHandler;
import br.com.schumaker.octopus.framework.web.handler.RequestHandler;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static br.com.schumaker.octopus.framework.web.http.Http.DELETE;
import static br.com.schumaker.octopus.framework.web.http.Http.GET;
import static br.com.schumaker.octopus.framework.web.http.Http.PATCH;
import static br.com.schumaker.octopus.framework.web.http.Http.POST;
import static br.com.schumaker.octopus.framework.web.http.Http.PUT;

/**
 * The Handler class implements the HttpHandler interface to handle HTTP requests.
 * It supports GET, POST, PUT, PATCH, DELETE, HEADER and OPTIONS methods and delegates the request handling to appropriate methods.
 * This class uses an IoC container to retrieve controllers and their methods, and processes the request and response accordingly.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
final class InboundHandler implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IoCContainer container = IoCContainer.getInstance();
    private final ValidationReflection validationReflection = ValidationReflection.getInstance();
    private final OutboundHandler outboundHandler = new OutboundHandler();

    private final Map<String, RequestHandler> handlers = new HashMap<>();

    public InboundHandler() {
        handlers.put(GET, new GetHandler());
    }


    // TODO: improve the conditional
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        String fullUrl = this.getFullUrl(exchange).first();
        HttpRequest request = new HttpRequest(fullUrl, exchange);

        try {
            RequestHandler handler = handlers.get(method.toUpperCase());
            if (handler != null) {
                HttpResponse response = handler.processRequest(request);
                outboundHandler.processResponse(response);
                return;
            } else {
                this.handleUnsupportedMethod(exchange);
            }
        } catch (Exception e) {
            this.handleException(exchange, e);
        }
        // TOBE removed after refactor
        try {
            if (method.equalsIgnoreCase(POST)) {
                handlePostRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(PUT)) {
                handlePutRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(PATCH)) {
                // TODO: Implement PATCH
                return;
            }

            if (method.equalsIgnoreCase(DELETE)) {
                // TODO: Implement DELETE
                return;
            }

            handleUnsupportedMethod(exchange);
        } catch (Exception e) {
            handleException(exchange, e);
        }
    }

    /**
     * Handles a POST request and sends the response.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @param fullUrl the full URL of the request
     * @throws Exception if an error occurs during request handling
     */
    private void handlePostRequest(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "";
        int httpCode = Http.HTTP_201;
        String applicationType = "application/json";
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodName = split.length > 5 ? split[5] : "/";

        String requestBody = this.readRequestBody(exchange);

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var mappingAndMethodAndParams = controller.getMethod(methodName, POST);
            var method = mappingAndMethodAndParams.second();
            if (method != null) {
                var returnType = method.getReturnType();
                var parameters = method.getParameters();
                applicationType = method.getAnnotation(br.com.schumaker.octopus.framework.annotations.controller.Post.class).type();
                Object result;
                if (parameters.length == 1) {
                    var valueAnnotation = parameters[0].getAnnotation(Payload.class);
                    var param = parameters[0].getType();
                    if (valueAnnotation != null) {
                        Object paramObject = objectMapper.readValue(requestBody, param);
                        if (parameters[0].getAnnotation(Validate.class) != null) {
                            validationReflection.validate(paramObject);
                        }
                        result = method.invoke(controller.getInstance(), paramObject);
                    } else {
                        result = method.invoke(controller.getInstance(), ClassReflection.getInstance().instantiate(param));
                    }
                } else {
                    result = method.invoke(controller.getInstance());
                }
                HttpResponse httpResponse = new HttpResponse(returnType, result, httpCode, applicationType, exchange);
                outboundHandler.processResponse(httpResponse);
            }
        }

        outboundHandler.sendResponse(exchange, httpCode, applicationType, response);
    }

    /**
     * Handles a PUT request and sends the response.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @param fullUrl the full URL of the request
     * @throws Exception if an error occurs during request handling
     */
    private void handlePutRequest(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "";
        int httpCode = Http.HTTP_202;
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodName = split.length > 5 ? split[5] : "/";

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var mappingAndMethodAndParams = controller.getMethod(methodName, PUT);

            var method = mappingAndMethodAndParams.second();
            if (method != null) {
                var returnType = method.getReturnType();
                Object result = method.invoke(controller.getInstance());

               // response = outboundHandler.processResponse(result, returnType);
            }
        } else {
            response = "Controller not found!";
            httpCode = Http.HTTP_404;
        }

        // outboundHandler.sendResponse(exchange, httpCode, response);
    }

    /**
     * Handles unsupported HTTP methods and sends a 405 response.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @throws Exception if an error occurs during request handling
     */
    private void handleUnsupportedMethod(HttpExchange exchange) throws Exception {
        String response = "Method not supported";
        outboundHandler.sendResponse(exchange, Http.HTTP_405, response);
    }

    /**
     * Handles exceptions that occur during request handling.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @param e the exception that occurred
     */
    private void handleException(HttpExchange exchange, Exception e) {
        Throwable originalException = e.getCause();
        if (originalException == null) {
            GlobalExceptionHandler.getInstance().handleException(exchange, e);
        } else {
            var ex = originalException instanceof Exception ? (Exception) originalException : new RuntimeException(originalException);
            GlobalExceptionHandler.getInstance().handleException(exchange, ex);
        }
    }

    /**
     * Reads the request body from the HttpExchange object.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @return the request body as a string
     * @throws Exception if an error occurs during request body reading
     */
    private String readRequestBody(HttpExchange exchange) throws Exception {
        StringBuilder requestBody = new StringBuilder();
        try (InputStream is = exchange.getRequestBody();
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }

    /**
     * Retrieves the full URL of the request.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @return a Pair containing the full URL and the request URI
     */
    private Pair<String, String> getFullUrl(HttpExchange exchange) {
        URI requestUri = exchange.getRequestURI();
        String scheme = exchange.getProtocol().startsWith("HTTP") ? "http" : "https";
        String host = exchange.getLocalAddress().getHostName();
        int port = exchange.getLocalAddress().getPort();
        return new Pair<>(scheme + "://" + host + ":" + port + requestUri.toString(), requestUri.toString());
    }
}
