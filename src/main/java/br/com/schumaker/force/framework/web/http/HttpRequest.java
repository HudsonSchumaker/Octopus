package br.com.schumaker.force.framework.web.http;

import br.com.schumaker.force.framework.model.Pair;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a Http request.
 * It is a record class.
 *
 * @see HttpResponse
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
public record HttpRequest (String fullUrl, HttpExchange exchange) {

    /**
     * Get the controller route and method path.
     *
     * @return A Pair with the controller route and method path.
     */
    public Pair<String, String> getControllerRouteAndMethodPath() {
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodPath = split.length > 5 ? "/" + String.join("/", Arrays.copyOfRange(split, 5, split.length)) : "/";

        return new Pair<>(controllerRoute, methodPath);
    }

    /**
     * Get the request headers.
     *
     * @return `Map` with the request headers.
     */
    public Map<String, String> getRequestHeaders() {
        Map<String, String> headers = new HashMap<>();
        exchange.getRequestHeaders().forEach((k, v) -> headers.put(k, String.join(", ", v)));
        return headers;
    }

    /**
     * Reads the request body from the HttpExchange object.
     *
     * @return the request body as a string.
     * @throws Exception if an error occurs during request body reading.
     */
    public String readRequestBody() throws Exception {
        StringBuilder requestBody = new StringBuilder();
        try (InputStream is = exchange.getRequestBody();
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while ((line = br.readLine()) != null) {
                requestBody.append(line);
            }
        }
        return requestBody.toString();
    }

    /**
     * Parses the query parameters from the full URL.
     *
     * @return a map of query parameters.
     */
    public Map<String, String> getQueryParams() {
        Map<String, String> queryParams = new HashMap<>();

        if (fullUrl.contains("?")) {
            String query = fullUrl.substring(fullUrl.indexOf("?") + 1);
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
                queryParams.put(key, value);
            }
        }

        return queryParams;
    }
}
