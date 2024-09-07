package br.com.schumaker.octopus.framework.web.view;

import java.util.Iterator;

public class ListResponseView<T extends Iterator<T>> {
    private final T body;
    private final int httpCode;
    private final String message;

    public ListResponseView(T body, int httpCode, String message) {
        this.body = body;
        this.httpCode = httpCode;
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public String getMessage() {
        return message;
    }
}
