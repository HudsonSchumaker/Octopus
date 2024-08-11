package br.com.schumaker.octopus.framework.exception;

import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.reflection.Pair;
import br.com.schumaker.octopus.framework.web.view.ResponseView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class GlobalExceptionHandler {

    private static final GlobalExceptionHandler INSTANCE = new GlobalExceptionHandler();
    private static final IoCContainer container = IoCContainer.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

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
                    var returnType = method.getReturnType();
                    var response = method.invoke(handler.getInstance(), exception);
                    var data = processResult(response, returnType);

                    sendResponse(exchange, data.first(), data.second());
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
            var response = exception.getCause();
            if (response != null) {
                var message = response.getMessage();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(httpCode, message.getBytes().length);
                var os = exchange.getResponseBody();
                os.write(message.getBytes());
                os.close();
            } else {
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(httpCode, exception.getMessage().getBytes().length);
                var os = exchange.getResponseBody();
                os.write(exception.getMessage().getBytes());
                os.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendResponse(HttpExchange exchange, Object response, int httpCode) {

    }

    private Pair<String, Integer> processResult(Object result, Class<?> returnType) throws Exception {
        if (returnType.equals(String.class)) {
            return new Pair<>((String) result, 500)  ;
        } else if (returnType.equals(ResponseView.class)) {
            var response = (ResponseView<?>) result;
            return new Pair<>(objectMapper.writeValueAsString(response.getData()), response.getStatus());
        } else {
            return new Pair<>(result.toString(), 500) ;
        }
    }
}
