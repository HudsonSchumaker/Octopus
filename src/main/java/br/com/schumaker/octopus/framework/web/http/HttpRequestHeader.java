package br.com.schumaker.octopus.framework.web.http;

import java.util.Map;

/**
 * This class represents a Http request header.
 * The key is the header name and the value is the header value.
 *
 * @see HttpRequest
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
public record HttpRequestHeader(Map<String, String> headers) {}
