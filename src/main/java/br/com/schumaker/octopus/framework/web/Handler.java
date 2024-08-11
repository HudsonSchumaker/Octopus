package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.exception.GlobalExceptionHandler;
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

public class Handler implements HttpHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final IoCContainer container = IoCContainer.getInstance();

    @Override
    public void handle(HttpExchange exchange)  {
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

            handleUnsupportedMethod(exchange, fullUrl);

        } catch (Exception e) {
            Throwable originalException = e.getCause();
            if (originalException == null) {
                GlobalExceptionHandler.getInstance().handleException(exchange, e);
            } else {
                var ex = originalException instanceof Exception ? (Exception) originalException : new RuntimeException(originalException);
                GlobalExceptionHandler.getInstance().handleException(exchange, ex);
            }
        }
    }

    private void handleGetRequest(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "";
        int httpCode = Http.HTTP_200;
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodName = split.length > 5 ? split[5] : "/";
        var header = exchange.getRequestHeaders();

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var pair = controller.getMethod(methodName, Http.GET);
            var method = pair.first();
            if (method != null) {
                var returnType = method.getReturnType();
                Object result = method.invoke(controller.getInstance());

                if (returnType.equals(String.class)) {
                    response = (String) result;
                } else if (returnType.equals(ResponseView.class)) {
                    response = ((ResponseView<?>) result).getData().toString();
                } else {
                    response = result.toString();
                }
            }
        } else {
            response = "Controller not found!";
            httpCode = Http.HTTP_404;
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(httpCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handlePostRequest(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "";
        int httpCode = Http.HTTP_201;
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodName = split.length > 5 ? split[5] : "/";
        var header = exchange.getRequestHeaders();

        // Read the request body
        StringBuilder requestBody = new StringBuilder();
        try (InputStream is = exchange.getRequestBody();
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr))
        {
            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
        }

        Object result = null;
        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var pair = controller.getMethod(methodName, Http.POST);
            var method = pair.first();
            if (method != null) {
                var returnType = method.getReturnType();
                var parameters = method.getParameters();
                if (parameters.length == 1) {
                    var param = parameters[0].getType();
                    Object paramObject = objectMapper.readValue(requestBody.toString(), param);
                    result = method.invoke(controller.getInstance(), paramObject);
                } else {
                    result = method.invoke(controller.getInstance());
                }

                if (result.equals(String.class)) {
                    assert result instanceof String;
                    response = (String) result;
                } else if (returnType.equals(ResponseView.class)) {
                    var json = ((ResponseView<?>) result).getData();
                    response = objectMapper.writeValueAsString(json);
                } else {
                    response = result.toString();
                }
            }
        }

        // Process the request body and generate a response
        exchange.sendResponseHeaders(Http.HTTP_201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handlePutRequest(HttpExchange exchange, String fullUrl) throws Exception {
        int httpCode = Http.HTTP_202;
        ///////////////////////////////////////////////////////////////////////
        var split = fullUrl.split("/");
        var controllerRoute = "/" + split[4];
        var methodName = split.length > 5 ? split[5] : "/";
        //////////////////////////////////////////////////////////////
        var header = exchange.getRequestHeaders();
        String response = "";
        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var pair = controller.getMethod(methodName, Http.PUT);
            var method = pair.first();
            if (method != null) {
                var returnType = method.getReturnType();
                Object result = null;

                 result = method.invoke(controller.getInstance());


                if (returnType.equals(String.class)) {
                    response = (String) result;
                } else if (returnType.equals(ResponseView.class)) {
                    response = ((ResponseView<?>) result).getData().toString();
                } else {
                    response = result.toString();
                }
            }
        } else {
            response = "Controller not found!";
            httpCode = Http.HTTP_404;
        }


        // Read the request body
        InputStream is = exchange.getRequestBody();
        StringBuilder requestBody = new StringBuilder();
        int i;
        while ((i = is.read()) != -1) {
            requestBody.append((char) i);
        }

        // Process the request body and generate a response
        exchange.sendResponseHeaders(Http.HTTP_202, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleUnsupportedMethod(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "Method not supported";
        exchange.sendResponseHeaders(Http.HTTP_405, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Pair<String, String> getFullUrl(HttpExchange exchange) {
        URI requestUri = exchange.getRequestURI();
        String scheme = exchange.getProtocol().startsWith("HTTP") ? "http" : "https";
        String host = exchange.getLocalAddress().getHostName();
        int port = exchange.getLocalAddress().getPort();
        return new Pair<>(scheme + "://" + host + ":" + port + requestUri.toString(), requestUri.toString());
    }
}
