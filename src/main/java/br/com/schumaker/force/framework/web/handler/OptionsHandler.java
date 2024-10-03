package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpResponse;

/**
 * The OptionsHandler class.
 * This class is responsible for processing HTTP_OPTIONS requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class OptionsHandler implements RequestHandler {

    /**
     * Process the HTTP_OPTIONS request.
     *
     * @param request the HTTP request.
     * @return the HTTP response.
     */
    @Override
    public HttpResponse processRequest(HttpRequest request) {
        //TODO: implement
        return null;
    }
}
