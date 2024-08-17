package br.com.schumaker.octopus.framework.exception;

/**
 * The ExceptionCodes enum defines a set of error codes for various exceptions that can occur within the application.
 * Each error code is associated with a specific integer value.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public enum ExceptionCodes {
    WEB_SERVER_INIT_ERROR(100),
    WEB_SERVER_STOP_ERROR(101);

    private final int code;

    /**
     * Constructs an ExceptionCodes enum with the specified error code.
     *
     * @param code the error code
     */
    ExceptionCodes(int code) {
        this.code = code;
    }

    /**
     * Returns the error code associated with the enum constant.
     *
     * @return the error code
     */
    public int getCode() {
        return code;
    }
}
