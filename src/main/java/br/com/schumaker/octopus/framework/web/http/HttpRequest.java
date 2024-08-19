package br.com.schumaker.octopus.framework.web.http;

import com.sun.net.httpserver.HttpExchange;

/**
 * This class represents a Http request.
 * It is a record class.
 *
 * @see HttpResponse
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
public record HttpRequest (String fullUrl, HttpExchange exchange) {
    public String getVerb() {
        return exchange.getRequestMethod();
    }
}
