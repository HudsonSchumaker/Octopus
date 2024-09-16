package br.com.schumaker.octopus.framework.web;

import br.com.schumaker.octopus.framework.exception.GlobalExceptionHandler;
import br.com.schumaker.octopus.framework.model.Pair;
import br.com.schumaker.octopus.framework.web.handler.DeleteHandler;
import br.com.schumaker.octopus.framework.web.handler.GetHandler;
import br.com.schumaker.octopus.framework.web.handler.HeaderHandler;
import br.com.schumaker.octopus.framework.web.handler.OptionsHandler;
import br.com.schumaker.octopus.framework.web.handler.PatchHandler;
import br.com.schumaker.octopus.framework.web.handler.PostHandler;
import br.com.schumaker.octopus.framework.web.handler.PutHandler;
import br.com.schumaker.octopus.framework.web.handler.RequestHandler;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_DELETE;
import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_GET;
import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_HEADER;
import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_OPTIONS;
import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_PATCH;
import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_POST;
import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_PUT;

/**
 * The Handler class implements the HttpHandler interface to handle HTTP requests.
 * It supports HTTP_GET, HTTP_POST, HTTP_PUT, HTTP_PATCH, HTTP_DELETE, HTTP_HEADER and HTTP_OPTIONS methods and delegates the request handling to appropriate methods.
 * This class uses an IoC container to retrieve controllers and their methods, and processes the request and response accordingly.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
final class InboundHandler implements HttpHandler {
    private final OutboundHandler outboundHandler = new OutboundHandler();
    private final Map<String, RequestHandler> handlers = new HashMap<>();

    public InboundHandler() {
        handlers.put(HTTP_GET, new GetHandler());
        handlers.put(HTTP_POST, new PostHandler());
        handlers.put(HTTP_PUT, new PutHandler());
        handlers.put(HTTP_PATCH, new PatchHandler());
        handlers.put(HTTP_DELETE, new DeleteHandler());
        handlers.put(HTTP_HEADER, new HeaderHandler());
        handlers.put(HTTP_OPTIONS, new OptionsHandler());
    }

    /**
     * Handles the HTTP request and delegates the request handling to the appropriate method.
     *
     * @param exchange the HttpExchange object containing the request and response.
     */
    @Override
    public void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            String fullUrl = this.getFullUrl(exchange).first();
            HttpRequest request = new HttpRequest(fullUrl, exchange);

            // TODO: interceptors?
            RequestHandler handler = handlers.get(method.toUpperCase());
            if (handler != null) {
                HttpResponse response = handler.processRequest(request);
                outboundHandler.processResponse(response);
            } else {
                this.handleUnsupportedMethod(exchange);
            }
        } catch (Exception e) {
            this.handleException(exchange, e);
        }
    }

    /**
     * Handles unsupported HTTP methods and sends a 405 response.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @throws Exception if an error occurs during request handling.
     */
    private void handleUnsupportedMethod(HttpExchange exchange) throws Exception {
        String response = "Method not supported.";
        outboundHandler.sendResponse(exchange, Http.HTTP_405, response);
    }

    /**
     * Handles exceptions that occur during request handling.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @param e the exception that occurred.
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
     * Retrieves the full URL of the request.
     *
     * @param exchange the HttpExchange object containing the request and response.
     * @return a Pair containing the full URL and the request URI.
     */
    private Pair<String, String> getFullUrl(HttpExchange exchange) {
        URI requestUri = exchange.getRequestURI();
        String scheme = exchange.getProtocol().startsWith("HTTP") ? "http" : "https";
        String host = exchange.getLocalAddress().getHostName();
        int port = exchange.getLocalAddress().getPort();
        return new Pair<>(scheme + "://" + host + ":" + port + requestUri.toString(), requestUri.toString());
    }
}
