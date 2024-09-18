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

/**
 * The HttpRestTemplate class provides utility methods for making HTTP requests.
 * It supports HTTP_GET, HTTP_POST, HTTP_PUT, and HTTP_DELETE methods and can handle JSON responses.
 * This class uses Java's HttpClient and Jackson's ObjectMapper for HTTP communication and JSON parsing.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class HttpRestTemplate {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public HttpRestTemplate() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Sends an HTTP_GET request to the specified URL with the given headers and returns the response body as an object of the specified type.
     *
     * @param url the URL to send the HTTP_GET request to.
     * @param headers the headers to include in the request.
     * @param responseType the class of the response type.
     * @param <T> the type of the response.
     * @return the response body as an object of the specified type.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
    public <T> T get(String url, Map<String, String> headers, Class<T> responseType) throws IOException, InterruptedException {
        String responseBody = get(url, headers);
        return objectMapper.readValue(responseBody, responseType);
    }

    /**
     * Sends an HTTP_GET request to the specified URL with the given headers and returns the response body as a string.
     *
     * @param url the URL to send the HTTP_GET request to.
     * @param headers the headers to include in the request.
     * @return the response body as a string.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
    public String get(String url, Map<String, String> headers) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET();

        headers.forEach(requestBuilder::header);
        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body();
    }

    /**
     * Sends an HTTP_POST request to the specified URL with the given body and headers and returns the response body as an object of the specified type.
     *
     * @param url the URL to send the HTTP_POST request to.
     * @param body the body to include in the request.
     * @param headers the headers to include in the request.
     * @param responseType the class of the response type.
     * @param <T> the type of the response.
     * @return the response body as an object of the specified type.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
    public <T> T post(String url, String body, Map<String, String> headers, Class<T> responseType) throws IOException, InterruptedException {
        String responseBody = post(url, body, headers);
        return objectMapper.readValue(responseBody, responseType);
    }

    /**
     * Sends an HTTP_POST request to the specified URL with the given body and headers and returns the response body as a string.
     *
     * @param url the URL to send the HTTP_POST request to.
     * @param body the body to include in the request.
     * @param headers the headers to include in the request.
     * @return the response body as a string.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
    public String post(String url, String body, Map<String, String> headers) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(BodyPublishers.ofString(body));

        headers.forEach(requestBuilder::header);
        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body();
    }

    /**
     * Sends an HTTP_PUT request to the specified URL with the given body and headers and returns the response body as an object of the specified type.
     *
     * @param url the URL to send the HTTP_PUT request to.
     * @param body the body to include in the request.
     * @param headers the headers to include in the request.
     * @param responseType the class of the response type.
     * @param <T> the type of the response.
     * @return the response body as an object of the specified type.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
    public <T> T put(String url, String body, Map<String, String> headers, Class<T> responseType) throws IOException, InterruptedException {
        String responseBody = put(url, body, headers);
        return objectMapper.readValue(responseBody, responseType);
    }

    /**
     * Sends an HTTP_PUT request to the specified URL with the given body and headers and returns the response body as a string.
     *
     * @param url the URL to send the HTTP_PUT request to.
     * @param body the body to include in the request.
     * @param headers the headers to include in the request.
     * @return the response body as a string.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
    public String put(String url, String body, Map<String, String> headers) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(BodyPublishers.ofString(body));

        headers.forEach(requestBuilder::header);
        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        return response.body();
    }

    /**
     * Sends an HTTP_DELETE request to the specified URL with the given headers and returns the response body as an object of the specified type.
     *
     * @param url the URL to send the HTTP_DELETE request to.
     * @param headers the headers to include in the request.
     * @param responseType the class of the response type.
     * @param <T> the type of the response.
     * @return the response body as an object of the specified type.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
    public <T> T delete(String url, Map<String, String> headers, Class<T> responseType) throws IOException, InterruptedException {
        String responseBody = delete(url, headers);
        return objectMapper.readValue(responseBody, responseType);
    }

    /**
     * Sends an HTTP_DELETE request to the specified URL with the given headers and returns the response body as a string.
     *
     * @param url the URL to send the HTTP_DELETE request to.
     * @param headers the headers to include in the request.
     * @return the response body as a string.
     * @throws IOException if an I/O error occurs.
     * @throws InterruptedException if the operation is interrupted.
     */
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
