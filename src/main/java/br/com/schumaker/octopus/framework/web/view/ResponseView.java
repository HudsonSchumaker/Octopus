package br.com.schumaker.octopus.framework.web.view;

import br.com.schumaker.octopus.framework.web.http.Http;

/**
 * The ResponseView class represents a standardized response structure for HTTP responses.
 * It encapsulates the response data and the HTTP status code.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * ResponseView<String> response = ResponseView.of("Success", Http.HTTP_200);
 * int status = response.getStatus();
 * String data = response.getData();
 * }
 * </pre>
 *
 * @param <T> the type of the response data
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
// TODO: fluent Api for builder
public class ResponseView<T> {
    private final T data;
    private final int status;

    private ResponseView(T data) {
        this.data = data;
        this.status = Http.HTTP_200;
    }

    private ResponseView(T data, int status) {
        this.data = data;
        this.status = status;
    }

    /**
     * Creates a new ResponseView instance with the specified data.
     * The HTTP status code is set to 200 (OK) by default.
     *
     * @param data the response data
     * @param <T> the type of the response data
     * @return a new ResponseView instance
     */
    public static <T> ResponseView<T> of(T data) {
        return new ResponseView<>(data);
    }

    /**
     * Creates a new ResponseView instance with the specified data and status.
     *
     * @param data the response data
     * @param status the HTTP status code
     * @param <T> the type of the response data
     * @return a new ResponseView instance
     */
    public static <T> ResponseView<T> of(T data, int status) {
        return new ResponseView<>(data, status);
    }

    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
