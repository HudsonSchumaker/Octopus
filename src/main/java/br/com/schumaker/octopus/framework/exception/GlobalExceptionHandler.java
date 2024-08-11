package br.com.schumaker.octopus.framework.exception;

import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.web.view.ResponseView;
import com.sun.net.httpserver.HttpExchange;

public class GlobalExceptionHandler {

    private static final GlobalExceptionHandler INSTANCE = new GlobalExceptionHandler();
    private static final IoCContainer container = IoCContainer.getInstance();

    private GlobalExceptionHandler() {}

    public static GlobalExceptionHandler getInstance() {
        return INSTANCE;
    }

    public void handleException(HttpExchange exchange, Exception exception) {
        if (container.getGlobalExceptionHandler() != null) {
            var handler = container.getGlobalExceptionHandler();
            var methodAndParams = handler.geMethod(exception.getClass());

            if (methodAndParams != null) {
                var method = methodAndParams.first();
                try {
                    var response = (ResponseView) method.invoke(handler.getInstance(), exception);
                    sendResponse(exchange, response.getData().toString(), response.getStatus());
                } catch (Exception e) {
                    sendResponse(exchange, e.toString(), 500);
                }
                return;
            }
        }
        sendResponse(exchange, exception, 500);
    }

    private void sendResponse(HttpExchange exchange, String response, int httpCode) {
        try {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(httpCode, response.getBytes().length);
            var os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponse(HttpExchange exchange, Exception exception, int httpCode) {
        try {
            var response = exception.getMessage();
            if (response.isBlank() || response.isEmpty()) {
                response = "";
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(httpCode, response.getBytes().length);
            var os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void sendResponse(HttpExchange exchange, Object response, int httpCode) {

    }
}
