package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;

/**
 * The Handler interface.
 * This interface is used to process the HTTP request and response.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public interface Handler {

    /**
     * Process the HTTP request.
     * @param request the HTTP request to be processed.
     * @return the HTTP response.
     */
    HttpResponse processRequest(HttpRequest request);
}
