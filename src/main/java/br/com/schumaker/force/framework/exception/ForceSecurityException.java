package br.com.schumaker.force.framework.exception;

/**
 * The ForceSecurityException class is a custom exception that represents a security exception within the Force framework.
 * It extends the ForceException class and provides a constructor to create a new security exception with a message and status code.
 *
 * @see ForceException
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ForceSecurityException extends ForceException {

    /**
     * Constructs a new ForceSecurityException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ForceSecurityException(String message, int statusCode) {
        super(message, statusCode);
    }
}
