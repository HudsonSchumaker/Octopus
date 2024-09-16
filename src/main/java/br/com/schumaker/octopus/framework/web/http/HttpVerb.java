package br.com.schumaker.octopus.framework.web.http;

/**
 * The HttpVerb enum represents the various HTTP methods.
 * Each enum constant corresponds to a standard HTTP method.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public enum HttpVerb {
    GET("HTTP_GET"),
    POST("HTTP_POST"),
    PUT("HTTP_PUT"),
    PATCH("HTTP_PATCH"),
    DELETE("HTTP_DELETE"),
    HEAD("HTTP_HEAD"),
    OPTIONS("HTTP_OPTIONS");

    private final String verb;

    HttpVerb(String verb) {
        this.verb = verb;
    }

    public String getVerb() {
        return verb;
    }
}
