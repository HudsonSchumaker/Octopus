package br.com.schumaker.octopus.framework.web.http;

/**
 * The HttpFilter interface.
 * It is responsible for filtering the request.
 *
 * @see HttpRequest
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public interface HttpFilter {

    /**
     * Do the filter.
     *
     * @param request the HttpRequest
     */
    void doFilter(HttpRequest request);
}
