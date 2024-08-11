package br.com.schumaker.octopus.web.view;

public class ResponseView<T> {

    private final T data;
    private final int status;

    private ResponseView(T data, int status) {
        this.data = data;
        this.status = status;
    }

    public static <T> ResponseView<T> of(T data, int status) {
        return new ResponseView<>(data, status);
    }

    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
