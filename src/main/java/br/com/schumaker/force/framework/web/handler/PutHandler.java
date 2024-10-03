package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.annotations.controller.Put;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpResponse;

import static br.com.schumaker.force.framework.web.http.Http.HTTP_PUT;

/**
 * The PutHandler class.
 * This class is responsible for processing HTTP_PUT requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class PutHandler extends AbstractRequestHandler {

    /**
     * Process the HTTP_PUT request.
     *
     * @param request the HTTP request.
     * @return the HTTP response.
     */
    @Override
    public HttpResponse processRequest(HttpRequest request) {
        return processRequest(request, HTTP_PUT, Put.class);
    }
}
