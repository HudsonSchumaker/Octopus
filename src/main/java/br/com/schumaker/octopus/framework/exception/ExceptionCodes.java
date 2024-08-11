package br.com.schumaker.octopus.framework.exception;

public enum ExceptionCodes {
    WEB_SERVER_INIT_ERROR(100),
    WEB_SERVER_STOP_ERROR(101);

    private final int code;

    ExceptionCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
