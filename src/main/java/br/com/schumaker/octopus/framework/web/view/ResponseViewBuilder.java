package br.com.schumaker.octopus.framework.web.view;

import java.util.HashMap;
import java.util.Map;

/**
 * The ResponseViewBuilder class is a builder for creating ResponseView objects.
 *
 * @param <T> the type of the response body
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ResponseViewBuilder<T> {
    private T body;
    private int httpCode;
    private Map<String, String> headers;

    /**
     * Creates a new ResponseViewBuilder with an HTTP status code of 200 (OK).
     */
    public ResponseViewBuilder(int httpCode) {
        this.body = null;
        this.headers = new HashMap<>();
        this.httpCode = httpCode;
    }

    /**
     * Sets the response body for the response.
     *
     * @param body the response body
     * @return this ResponseViewBuilder
     */
    public ResponseViewBuilder<T> body(T body) {
        this.body = body;
        return this;
    }

    /**
     * Sets the HTTP status code for the response.
     *
     * @param httpCode the HTTP status code
     * @return this ResponseViewBuilder
     */
    public ResponseViewBuilder<T> httpCode(int httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    /**
     * Sets the headers for the response.
     *
     * @param headers a map of header names and values
     * @return this ResponseViewBuilder
     */
    public ResponseViewBuilder<T> headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * Builds a new ResponseView object with the specified HTTP status code, response body,and headers.
     *
     * @return a new ResponseView object
     */
    @SuppressWarnings("unchecked")
    public <T> ResponseView<T> build() {
        return new ResponseView<T>(httpCode, (T) body, headers);
    }
}
