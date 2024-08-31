package br.com.schumaker.octopus.framework.web.view;

import br.com.schumaker.octopus.framework.web.http.Http;

import java.util.HashMap;
import java.util.Map;

public class ResponseView<T> {
    private final T body;
    private final int httpCode;
    private final Map<String, String> headers;

    public ResponseView() {
        this(Http.HTTP_204, null, new HashMap<>());
    }

    public ResponseView(int httpCode) {
        this(httpCode, (T) null);
    }

    public ResponseView(int httpCode, T body) {
        this(httpCode, null, new HashMap<>());
    }

    public ResponseView(int httpCode, Map<String, String> headers) {
        this(httpCode, null, headers);
    }

    public ResponseView(int httpCode, T body, Map<String, String> headers) {
        this.body = body;
        this.headers = headers;
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public T getBody() {
        return this.body;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public static <T> ResponseViewBuilder<T> ok() {
        return new ResponseViewBuilder<T>(Http.HTTP_200);
    }

    public static <T> ResponseViewBuilder<T> created() {
        return new ResponseViewBuilder<T>(Http.HTTP_201);
    }

    public static <T> ResponseViewBuilder<T> accepted() {
        return new ResponseViewBuilder<T>(Http.HTTP_202);
    }

    public static <T> ResponseViewBuilder<T> noContent() {
        return new ResponseViewBuilder<T>(Http.HTTP_204);
    }

    public static <T> ResponseViewBuilder<T> badRequest() {
        return new ResponseViewBuilder<T>(Http.HTTP_400);
    }

    public static <T> ResponseViewBuilder<T> unauthorized() {
        return new ResponseViewBuilder<T>(Http.HTTP_401);
    }

    public static <T> ResponseViewBuilder<T> forbidden() {
        return new ResponseViewBuilder<T>(Http.HTTP_403);
    }

    public static <T> ResponseViewBuilder<T> notFound() {
        return new ResponseViewBuilder<T>(Http.HTTP_404);
    }

    public static <T> ResponseViewBuilder<T> conflict() {
        return new ResponseViewBuilder<T>(Http.HTTP_409);
    }

    public static <T> ResponseViewBuilder<T> internalServerError() {
        return new ResponseViewBuilder<T>(Http.HTTP_500);
    }
}
