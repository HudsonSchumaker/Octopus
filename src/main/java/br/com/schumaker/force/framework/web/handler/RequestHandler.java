package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpResponse;

/**
 * The RequestHandler interface.
 * This interface is used to process the HTTP request and response.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@FunctionalInterface
public interface RequestHandler {

    /**
     * Process the HTTP request.
     *
     * @param request the HTTP request to be processed.
     * @return the HTTP response.
     */
    HttpResponse processRequest(HttpRequest request);
}
