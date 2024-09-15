package br.com.schumaker.octopus.framework.web.http;

import br.com.schumaker.octopus.framework.web.handler.RequestHandler;
import com.sun.net.httpserver.HttpExchange;

/**
 * This class represents a Http response.
 *
 * @see RequestHandler
 * @see HttpRequest
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
public record HttpResponse(Class<?> typeResponseBody, Object body, int httpCode, String applicationType, HttpExchange exchange) {}
