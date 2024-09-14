package br.com.schumaker.octopus.framework.exception;

import br.com.schumaker.octopus.framework.web.http.Http;

/**
 * The OctopusException class represents a custom runtime exception for the Octopus framework.
 * It includes an additional status code to indicate the HTTP status associated with the exception.
 *
 * @see RuntimeException
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class OctopusException extends RuntimeException {
    private final int statusCode;

    /**
     * Constructs a new OctopusException with the specified detail message.
     * The status code is set to HTTP 500.
     *
     * @param message the detail message
     */
    public OctopusException(String message) {
        super(message);
        this.statusCode = Http.HTTP_500;
    }

    /**
     * Constructs a new OctopusException with the specified detail message and status code.
     *
     * @param message the detail message
     * @param statusCode the HTTP status code
     */
    public OctopusException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    /**
     * Constructs a new OctopusException with the specified detail message and cause.
     * The status code is set to HTTP 500.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public OctopusException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = Http.HTTP_500;
    }

    /**
     * Returns the HTTP status code associated with this exception.
     *
     * @return the HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }
}
