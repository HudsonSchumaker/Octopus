package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.web.http.HttpResponse;
import br.com.schumaker.octopus.framework.web.view.ResponseView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.util.Map;

final class OutboundHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Processes the response and sends it to the client.
     *
     * @param response the HttpResponse object containing the response data
     * @throws Exception if an error occurs during response processing
     */
    public void processResponse(HttpResponse response) throws Exception {
        var result = response.body();
        var httpCode = response.httpCode();
        var exchange = response.exchange();
        var returnType = response.typeResponseBody();

        // TODO: Check if this is necessary more types
        if (returnType.equals(String.class)) {
            this.sendResponse(exchange, httpCode, (String) result);
        } else if (returnType.equals(ResponseView.class)) {
            processResponseHeaders(exchange, (ResponseView<?>) result);
            var resultBody = objectMapper.writeValueAsString(((ResponseView<?>) result).getBody());
            this.sendResponse(exchange, httpCode, resultBody);
        } else {
            this.sendResponse(exchange, httpCode, result.toString());
        }
    }

    public void sendResponse(HttpExchange exchange, int httpCode) throws Exception {
        this.sendResponse(exchange, httpCode, "");
    }

    /**
     * Sends the response to the client.
     *
     * @param exchange the HttpExchange object containing the request and response
     * @param httpCode the HTTP status code
     * @param response the response body
     * @throws Exception if an error occurs during response sending
     */
    public void sendResponse(HttpExchange exchange, int httpCode, String response) throws Exception {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(httpCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public void sendResponse(HttpExchange exchange, int httpCode, String response, Map<String, String> headers) throws Exception {
        headers.forEach((k, v) -> exchange.getResponseHeaders().set(k, v));
        this.sendResponse(exchange, httpCode, response);
    }

    private void processResponseHeaders(HttpExchange exchange, ResponseView<?> responseView) {
        responseView.getHeaders().forEach((k, v) -> {
            exchange.getResponseHeaders().set(k, v);
        });
    }
}
