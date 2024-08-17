package br.com.schumaker.octopus.framework.exception;

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

    public OctopusException(String message) {
        super(message);
        this.statusCode = 500;
    }

    public OctopusException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
