package br.com.schumaker.octopus.framework.exception;

public class OctopusException extends RuntimeException {

    private final int statusCode;

    public OctopusException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
