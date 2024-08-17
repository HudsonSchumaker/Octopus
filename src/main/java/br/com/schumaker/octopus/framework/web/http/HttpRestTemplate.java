package br.com.schumaker.octopus.framework.web.http;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

public class HttpRestTemplate {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpRestTemplate() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public <T> T get(String url, Map<String, String> headers, Class<T> responseType) throws IOException, InterruptedException {
        String responseBody = get(url, headers);
        return objectMapper.readValue(responseBody, responseType);
    }

    public String get(String url, Map<String, String> headers) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        headers.forEach(requestBuilder::header);

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body();
    }

    public <T> T post(String url, String body, Map<String, String> headers, Class<T> responseType) throws IOException, InterruptedException {
        String responseBody = post(url, body, headers);
        return objectMapper.readValue(responseBody, responseType);
    }

    public String post(String url, String body, Map<String, String> headers) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(body));

        headers.forEach(requestBuilder::header);

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body();
    }

    public <T> T put(String url, String body, Map<String, String> headers, Class<T> responseType) throws IOException, InterruptedException {
        String responseBody = put(url, body, headers);
        return objectMapper.readValue(responseBody, responseType);
    }

    public String put(String url, String body, Map<String, String> headers) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(BodyPublishers.ofString(body));

        headers.forEach(requestBuilder::header);

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body();
    }

    public <T> T delete(String url, Map<String, String> headers, Class<T> responseType) throws IOException, InterruptedException {
        String responseBody = delete(url, headers);
        return objectMapper.readValue(responseBody, responseType);
    }

    public String delete(String url, Map<String, String> headers) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE();

        headers.forEach(requestBuilder::header);

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body();
    }
}
