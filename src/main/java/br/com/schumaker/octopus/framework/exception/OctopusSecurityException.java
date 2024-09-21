package br.com.schumaker.octopus.framework.exception;

/**
 * The OctopusSecurityException class is a custom exception that represents a security exception within the Octopus framework.
 * It extends the OctopusException class and provides a constructor to create a new security exception with a message and status code.
 *
 * @see OctopusException
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class OctopusSecurityException extends OctopusException {

    /**
     * Constructs a new OctopusSecurityException with the specified detail message.
     *
     * @param message the detail message.
     */
    public OctopusSecurityException(String message, int statusCode) {
        super(message, statusCode);
    }
}
