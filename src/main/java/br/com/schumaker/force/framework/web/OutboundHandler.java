package br.com.schumaker.force.framework.web;

import br.com.schumaker.force.framework.model.ObjectMapperConfig;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpResponse;
import br.com.schumaker.force.framework.web.view.ResponseView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

import static br.com.schumaker.force.framework.web.http.Http.APPLICATION_JSON;
import static br.com.schumaker.force.framework.web.http.Http.CONTENT_TYPE;

/**
 * The OutboundHandler class.
 * This class is responsible for processing the response and sending it to the client.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
final class OutboundHandler {
    private final ObjectMapper objectMapper = ObjectMapperConfig.getInstance();

    /**
     * Processes the response and sends it to the client.
     *
     * @param response the HttpResponse object containing the response data.
     * @throws Exception if an error occurs during response processing.
     */
    public void processResponse(HttpResponse response) throws Exception {
        var result = response.body();
        var httpCode = response.httpCode();
        var exchange = response.exchange();
        var returnType = response.typeResponseBody();
        var contentType = response.applicationType();

        // TODO: Check if this is necessary more types
        if (returnType.equals(String.class)) {
            this.sendResponse(exchange, httpCode, contentType, (String) result);
        } else if (returnType.equals(ResponseView.class)) {
            var resultBody = objectMapper.writeValueAsString(((ResponseView<?>) result).getBody());
            this.processResponseHeaders(exchange, (ResponseView<?>) result);
            this.sendResponse(exchange, httpCode,contentType, resultBody.equals("null") ? "" : resultBody);
        } else {
            this.sendResponse(exchange, httpCode, contentType, result.toString());
        }
    }

    /**
     * Sends the response to the client.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @throws Exception if an error occurs during response sending.
     */
    public void sendResponse(HttpExchange exchange) throws Exception {
        this.sendResponse(exchange, Http.HTTP_418);
    }

    /**
     * Sends the response to the client.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @param httpCode the HTTP status code.
     * @throws Exception if an error occurs during response sending.
     */
    public void sendResponse(HttpExchange exchange, int httpCode) throws Exception {
        this.sendResponse(exchange, httpCode, "");
    }

    /**
     * Sends the response to the client.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @param httpCode the HTTP status code.
     * @param response the response body.
     * @throws Exception if an error occurs during response sending.
     */
    public void sendResponse(HttpExchange exchange, int httpCode, String response) throws Exception {
        this.sendResponse(exchange, httpCode, APPLICATION_JSON, response);
    }

    /**
     * Sends the response to the client.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @param httpCode the HTTP status code.
     * @param response the response body.
     * @throws Exception if an error occurs during response sending.
     */
    public void sendResponse(HttpExchange exchange, int httpCode, String contentType, String response) throws Exception {
        exchange.getResponseHeaders().add(CONTENT_TYPE, contentType);
        // TODO: create a way to set the security, and check CORS
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, HEAD, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        response = Optional.ofNullable(response).orElse("");
        exchange.sendResponseHeaders(httpCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    /**
     * Sends the response to the client.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @param httpCode the HTTP status code.
     * @param response the response body.
     * @param headers  the response headers.
     * @throws Exception if an error occurs during response sending.
     */
    public void sendResponse(HttpExchange exchange, int httpCode, String response, Map<String, String> headers) throws Exception {
       this.sendResponse(exchange, httpCode, APPLICATION_JSON, response, headers);
    }

    /**
     * Sends the response to the client.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @param httpCode the HTTP status code.
     * @param response the response body.
     * @param headers  the response headers.
     * @throws Exception if an error occurs during response sending.
     */
    public void sendResponse(HttpExchange exchange, int httpCode, String contentType, String response, Map<String, String> headers) throws Exception {
        headers.forEach((k, v) -> exchange.getResponseHeaders().set(k, v));
        this.sendResponse(exchange, httpCode, contentType, response);
    }

    /**
     * Processes the response headers.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @param responseView the ResponseView object containing the response data.
     */
    private void processResponseHeaders(HttpExchange exchange, ResponseView<?> responseView) {
        responseView.getHeaders().forEach((k, v) -> {
            exchange.getResponseHeaders().set(k, v);
        });
    }
}
