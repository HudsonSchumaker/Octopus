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

    //TODO: improve and document this class

    private final int statusCode;

    public OctopusException(String message) {
        super(message);
        this.statusCode = Http.HTTP_500;
    }

    public OctopusException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public OctopusException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = Http.HTTP_500;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
