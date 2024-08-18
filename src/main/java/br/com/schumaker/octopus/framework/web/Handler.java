package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.annotations.controller.Payload;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.exception.GlobalExceptionHandler;
import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.reflection.Pair;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.reflection.validation.ValidationReflection;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.view.ResponseView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;

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
public class Handler implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IoCContainer container = IoCContainer.getInstance();
    private final ValidationReflection validationReflection = ValidationReflection.getInstance();

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        String fullUrl = getFullUrl(exchange).first();

        try {
            if (method.equalsIgnoreCase(GET)) {
                handleGetRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(POST)) {
                handlePostRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(PUT)) {
                handlePutRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(PATCH)) {
                handlePutRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(DELETE)) {
                handlePutRequest(exchange, fullUrl);
                return;
            }

            handleUnsupportedMethod(exchange);

        } catch (Exception e) {
            handleException(exchange, e);
        }
    }

    /**
     * Handles a GET request and sends the response.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @param fullUrl the full URL of the request
     * @throws Exception if an error occurs during request handling
     */
    private void handleGetRequest(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "";
        int httpCode = Http.HTTP_200;
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodName = split.length > 5 ? split[5] : "/";

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var pair = controller.getMethod(methodName, GET);
            var method = pair.first();
            if (method != null) {
                var returnType = method.getReturnType();
                Object result = method.invoke(controller.getInstance());

                response = this.processResponse(result, returnType);
            }
        } else {
            response = "Controller not found!";
            httpCode = Http.HTTP_404;
        }

        sendResponse(exchange, httpCode, response);
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
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodName = split.length > 5 ? split[5] : "/";

        String requestBody = readRequestBody(exchange);

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var pair = controller.getMethod(methodName, POST);
            var method = pair.first();
            if (method != null) {
                var returnType = method.getReturnType();
                var parameters = method.getParameters();
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
                response = this.processResponse(result, returnType);
            }
        }

        sendResponse(exchange, httpCode, response);
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
            var pair = controller.getMethod(methodName, PUT);
            var method = pair.first();
            if (method != null) {
                var returnType = method.getReturnType();
                Object result = method.invoke(controller.getInstance());

                response = this.processResponse(result, returnType);
            }
        } else {
            response = "Controller not found!";
            httpCode = Http.HTTP_404;
        }

        sendResponse(exchange, httpCode, response);
    }

    /**
     * Handles unsupported HTTP methods and sends a 405 response.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @throws Exception if an error occurs during request handling
     */
    private void handleUnsupportedMethod(HttpExchange exchange) throws Exception {
        String response = "Method not supported";
        sendResponse(exchange, Http.HTTP_405, response);
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
     * Sends the response to the client.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @param httpCode the HTTP status code
     * @param response the response body
     * @throws Exception if an error occurs during response sending
     */
    private void sendResponse(HttpExchange exchange, int httpCode, String response) throws Exception {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(httpCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    /**
     * Processes the result of a controller method invocation and converts it to a string.
     *
     * @param result the result of the method invocation
     * @param returnType the return type of the method
     * @return the result as a string
     * @throws Exception if an error occurs during result processing
     */
    private String processResponse(Object result, Class<?> returnType) throws Exception {
        if (returnType.equals(String.class)) {
            return (String) result;
        } else if (returnType.equals(ResponseView.class)) {
            return objectMapper.writeValueAsString(((ResponseView<?>) result).getData());
        } else {
            return result.toString();
        }
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
