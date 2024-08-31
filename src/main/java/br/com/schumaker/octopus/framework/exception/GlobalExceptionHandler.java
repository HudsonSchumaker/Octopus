package br.com.schumaker.octopus.framework.exception;

import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.reflection.Pair;
import br.com.schumaker.octopus.framework.web.view.ResponseView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

/**
 * The GlobalExceptionHandler class is responsible for handling exceptions that occur during the execution of the application.
 * It uses a singleton pattern to ensure that only one instance of the handler exists. The class interacts with the IoCContainer
 * to retrieve global exception handlers and processes exceptions accordingly.
 *
 * @see IoCContainer
 * @see HttpExchange
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class GlobalExceptionHandler {
    private static final GlobalExceptionHandler INSTANCE = new GlobalExceptionHandler();
    private static final IoCContainer container = IoCContainer.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private GlobalExceptionHandler() {}

    /**
     * Returns the singleton instance of the \@GlobalExceptionHandler class.
     *
     * @return the singleton instance
     */
    public static GlobalExceptionHandler getInstance() {
        return INSTANCE;
    }

    /**
     * Handles the given exception by invoking the appropriate global exception handler method, if available.
     * Sends the response back to the client.
     *
     * @param exchange the HttpExchange object
     * @param exception the exception to handle
     */
    public void handleException(HttpExchange exchange, Exception exception) {
        var handler = container.getGlobalExceptionHandler();
        if (handler != null) {
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

        if (exception instanceof OctopusException) {
            sendResponse(exchange, exception, ((OctopusException) exception).getStatusCode());
            return;
        }
        sendResponse(exchange, exception, 500);
    }

    /**
     * Sends a response with the given string message and HTTP status code.
     *
     * @param exchange the HttpExchange object
     * @param response the response message
     * @param httpCode the HTTP status code
     */
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

    /**
     * Sends a response with the exception message and HTTP status code.
     *
     * @param exchange the HttpExchange object
     * @param exception the exception to handle
     * @param httpCode the HTTP status code
     */
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
        //TODO: check this
    }

    /**
     * Processes the result returned by the global exception handler method and converts it to a string and HTTP status code.
     *
     * @param result the result object
     * @param returnType the return type of the handler method
     * @return a pair containing the response string and HTTP status code
     * @throws Exception if an error occurs during processing
     */
    private Pair<String, Integer> processResult(Object result, Class<?> returnType) throws Exception {
        if (returnType.equals(String.class)) {
            return new Pair<>((String) result, 500)  ;
        } else if (returnType.equals(ResponseView.class)) {
            var response = (ResponseView<?>) result;
            return new Pair<>(objectMapper.writeValueAsString(response.getBody()), response.getHttpCode());
        } else {
            return new Pair<>(result.toString(), 500) ;
        }
    }
}
