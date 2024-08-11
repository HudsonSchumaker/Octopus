package br.com.schumaker.octopus.web;

public enum HttpVerb {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE"),
    HEADER("HEADER"),
    OPTIONS("OPTIONS");

    private final String verb;

    HttpVerb(String verb) {
        this.verb = verb;
    }

    public String getVerb() {
        return verb;
    }
}
