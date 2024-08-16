package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.annotations.Payload;
import br.com.schumaker.octopus.framework.exception.GlobalExceptionHandler;
import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.reflection.Pair;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
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

import static br.com.schumaker.octopus.framework.web.Http.GET;

public class Handler implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IoCContainer container = IoCContainer.getInstance();

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        String fullUrl = getFullUrl(exchange).first();

        try {
            if (method.equalsIgnoreCase(Http.GET)) {
                handleGetRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(Http.POST)) {
                handlePostRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(Http.PUT)) {
                handlePutRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(Http.PATCH)) {
                handlePutRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(Http.DELETE)) {
                handlePutRequest(exchange, fullUrl);
                return;
            }

            handleUnsupportedMethod(exchange);

        } catch (Exception e) {
            handleException(exchange, e);
        }
    }

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

                response = processResult(result, returnType);
            }
        } else {
            response = "Controller not found!";
            httpCode = Http.HTTP_404;
        }

        sendResponse(exchange, httpCode, response);
    }

    private void handlePostRequest(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "";
        int httpCode = Http.HTTP_201;
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodName = split.length > 5 ? split[5] : "/";

        String requestBody = readRequestBody(exchange);

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var pair = controller.getMethod(methodName, Http.POST);
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
                        result = method.invoke(controller.getInstance(), paramObject);
                    } else {
                        result = method.invoke(controller.getInstance(), ClassReflection.getInstance().instantiate(param));
                    }
                } else {
                    result = method.invoke(controller.getInstance());
                }

                response = processResult(result, returnType);
            }
        }

        sendResponse(exchange, httpCode, response);
    }

    private void handlePutRequest(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "";
        int httpCode = Http.HTTP_202;
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodName = split.length > 5 ? split[5] : "/";

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var pair = controller.getMethod(methodName, Http.PUT);
            var method = pair.first();
            if (method != null) {
                var returnType = method.getReturnType();
                Object result = method.invoke(controller.getInstance());

                response = processResult(result, returnType);
            }
        } else {
            response = "Controller not found!";
            httpCode = Http.HTTP_404;
        }

        sendResponse(exchange, httpCode, response);
    }

    private void handleUnsupportedMethod(HttpExchange exchange) throws Exception {
        String response = "Method not supported";
        sendResponse(exchange, Http.HTTP_405, response);
    }

    private void handleException(HttpExchange exchange, Exception e) {
        Throwable originalException = e.getCause();
        if (originalException == null) {
            GlobalExceptionHandler.getInstance().handleException(exchange, e);
        } else {
            var ex = originalException instanceof Exception ? (Exception) originalException : new RuntimeException(originalException);
            GlobalExceptionHandler.getInstance().handleException(exchange, ex);
        }
    }

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

    private void sendResponse(HttpExchange exchange, int httpCode, String response) throws Exception {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(httpCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private String processResult(Object result, Class<?> returnType) throws Exception {
        if (returnType.equals(String.class)) {
            return (String) result;
        } else if (returnType.equals(ResponseView.class)) {
            return objectMapper.writeValueAsString(((ResponseView<?>) result).getData());
        } else {
            return result.toString();
        }
    }

    private Pair<String, String> getFullUrl(HttpExchange exchange) {
        URI requestUri = exchange.getRequestURI();
        String scheme = exchange.getProtocol().startsWith("HTTP") ? "http" : "https";
        String host = exchange.getLocalAddress().getHostName();
        int port = exchange.getLocalAddress().getPort();
        return new Pair<>(scheme + "://" + host + ":" + port + requestUri.toString(), requestUri.toString());
    }
}
