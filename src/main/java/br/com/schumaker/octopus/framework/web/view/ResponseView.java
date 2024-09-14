package br.com.schumaker.octopus.framework.web.view;

import br.com.schumaker.octopus.framework.web.http.Http;

import java.util.HashMap;
import java.util.Map;

/**
 * The ResponseView class represents a response view that can be returned by a controller method.
 * This class contains the HTTP status code, the response body, and the response headers.
 *
 * @param <T> the type of the response body.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ResponseView<T> {
    private final T body;
    private final int httpCode;
    private final Map<String, String> headers;

    /**
     * Creates a new ResponseView with an HTTP status code of 204 (No Content).
     */
    public ResponseView() {
        this(Http.HTTP_204, null, new HashMap<>());
    }

    /**
     * Creates a new ResponseView with the specified HTTP status code.
     *
     * @param httpCode the HTTP status code.
     */
    public ResponseView(int httpCode) {
        this(httpCode, (T) null);
    }

    /**
     * Creates a new ResponseView with the specified HTTP status code and response body.
     *
     * @param httpCode the HTTP status code.
     * @param body the response body.
     */
    public ResponseView(int httpCode, T body) {
        this(httpCode, body, new HashMap<>());
    }

    /**
     * Creates a new ResponseView with the specified HTTP status code and response body.
     *
     * @param httpCode the HTTP status code.
     * @param headers the response body.
     */
    public ResponseView(int httpCode, Map<String, String> headers) {
        this(httpCode, null, headers);
    }

    /**
     * Creates a new ResponseView with the specified HTTP status code, response body, and headers.
     *
     * @param httpCode the HTTP status code.
     * @param body the response body.
     * @param headers a map of header names and values.
     */
    public ResponseView(int httpCode, T body, Map<String, String> headers) {
        this.body = body;
        this.headers = headers;
        this.httpCode = httpCode;
    }

    /**
     * Returns the HTTP status code for the response.
     *
     * @return the HTTP status code.
     */
    public int getHttpCode() {
        return this.httpCode;
    }

    /**
     * Returns the response body.
     *
     * @return the response body.
     */
    public T getBody() {
        return this.body;
    }

    /**
     * Returns the headers for the response.
     *
     * @return a map of header names and values.
     */
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 200 (OK).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> ok() {
        return new ResponseViewBuilder<T>(Http.HTTP_200);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 201 (Created).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> created() {
        return new ResponseViewBuilder<T>(Http.HTTP_201);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 202 (Accepted).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> accepted() {
        return new ResponseViewBuilder<T>(Http.HTTP_202);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 204 (No Content).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> noContent() {
        return new ResponseViewBuilder<T>(Http.HTTP_204);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 400 (Bad Request).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> badRequest() {
        return new ResponseViewBuilder<T>(Http.HTTP_400);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 401 (Unauthorized).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> unauthorized() {
        return new ResponseViewBuilder<T>(Http.HTTP_401);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 403 (Forbidden).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> forbidden() {
        return new ResponseViewBuilder<T>(Http.HTTP_403);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 404 (Not Found).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> notFound() {
        return new ResponseViewBuilder<T>(Http.HTTP_404);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 409 (Conflict).
     *
     * @param <T> the type of the response body
     * @return a new ResponseViewBuilder
     */
    public static <T> ResponseViewBuilder<T> conflict() {
        return new ResponseViewBuilder<T>(Http.HTTP_409);
    }

    /**
     * Returns a new ResponseViewBuilder with an HTTP status code of 500 (Internal Server Error).
     *
     * @param <T> the type of the response body.
     * @return a new ResponseViewBuilder.
     */
    public static <T> ResponseViewBuilder<T> internalServerError() {
        return new ResponseViewBuilder<T>(Http.HTTP_500);
    }
}
