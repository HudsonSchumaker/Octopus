package br.com.schumaker.force.framework.web.http;

import java.util.Map;

/**
 * This class represents the Http request headers.
 * The key is the header name and the value is the header value.
 * In endpoint methods, you can use this class to get the request headers, the IoC container will inject it.
 * <p>
 * Example usage:
 * </p>
 * <pre>
 * {@code
 * @Controller("/api")
 * public class MyController {
 *      @Get("/{id}")
 *      public String getById(@PathVariable("id") int key, HttpRequestHeader headers) {
 *
 *          var userAgent = headers.headers().get("User-agent");
 *          return ResponseView.ok().body(userAgent).build();
 *      }
 * }
 * }
 * </pre>
 * @see HttpRequest
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
public record HttpRequestHeader(Map<String, String> headers) {}
