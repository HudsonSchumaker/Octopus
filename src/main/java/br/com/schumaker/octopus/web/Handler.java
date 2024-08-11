package br.com.schumaker.octopus.web;

import br.com.schumaker.octopus.ioc.IoCContainer;
import br.com.schumaker.octopus.reflection.Pair;
import br.com.schumaker.octopus.web.view.ResponseView;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

public class Handler implements HttpHandler {
    private final IoCContainer container = IoCContainer.getInstance();

    @Override
    public void handle(HttpExchange exchange)  {
        String method = exchange.getRequestMethod();
        String fullUrl = getFullUrl(exchange).getFirst();

        try {
            if (method.equalsIgnoreCase(Http.GET)) {
                handleGetRequest(exchange, fullUrl);
                return;
            }

            if (method.equalsIgnoreCase(Http.POST)) {
                handlePostRequest(exchange, fullUrl);
                return;
            }

            handleUnsupportedMethod(exchange, fullUrl);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleGetRequest(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "";
        int httpCode = Http.HTTP_200;
    ///////////////////////////////////////////////////////////////////////
        var split = fullUrl.split("/");
        var controllerRoute = "/" + split[4];
        var methodName = split.length > 5 ? split[5] : "/";
    //////////////////////////////////////////////////////////////
        var header = exchange.getRequestHeaders();

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var pair = controller.getMethod(methodName, Http.GET);
            var method = pair.getFirst();
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
        // Read the request body
        InputStream is = exchange.getRequestBody();
        StringBuilder requestBody = new StringBuilder();
        int i;
        while ((i = is.read()) != -1) {
            requestBody.append((char) i);
        }

        // Process the request body and generate a response
        String response = "Received POST data: " + requestBody.toString() + ". Full URL: " + fullUrl;
        exchange.sendResponseHeaders(Http.HTTP_201, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleUnsupportedMethod(HttpExchange exchange, String fullUrl) throws Exception {
        String response = "Method not supported. Full URL: " + fullUrl;
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
