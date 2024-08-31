package br.com.schumaker.octopus.framework.web.view;

import java.util.HashMap;
import java.util.Map;

public class ResponseViewBuilder<T> {
    private T body;
    private int httpCode;
    private Map<String, String> headers;

    public ResponseViewBuilder(int httpCode) {
        this.body = null;
        this.headers = new HashMap<>();
        this.httpCode = httpCode;
    }

    public ResponseViewBuilder<T> body(T body) {
        this.body = body;
        return this;
    }

    public ResponseViewBuilder<T> httpCode(int httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    public ResponseViewBuilder<T> headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public <T> ResponseView<T> build() {
        return new ResponseView<T>(httpCode, (T) body, headers);
    }
}
