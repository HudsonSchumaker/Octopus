package br.com.schumaker.force.framework.exception;

import br.com.schumaker.force.framework.web.http.Http;

/**
 * The ForceException class represents a custom runtime exception for the Force framework.
 * It includes an additional status code to indicate the HTTP status associated with the exception.
 *
 * @see RuntimeException
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ForceException extends RuntimeException {
    private final int statusCode;

    /**
     * Constructs a new ForceException with the specified detail message.
     * The status code is set to HTTP 500.
     *
     * @param message the detail message.
     */
    public ForceException(String message) {
        super(message);
        this.statusCode = Http.HTTP_500;
    }

    /**
     * Constructs a new ForceException with the specified detail message and status code.
     *
     * @param message the detail message.
     * @param statusCode the HTTP status code.
     */
    public ForceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Constructs a new ForceException with the specified detail message and cause.
     * The status code is set to HTTP 500.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public ForceException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = Http.HTTP_500;
    }

    /**
     * Returns the HTTP status code associated with this exception.
     *
     * @return the HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }
}
