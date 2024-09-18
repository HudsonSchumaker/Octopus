package br.com.schumaker.octopus.framework.exception;

public class OctopusSecurityException extends OctopusException {
    public OctopusSecurityException(String message) {
        super(message);
    }

    public OctopusSecurityException(String message, int statusCode) {
        super(message, statusCode);
    }
}
