package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.ioc.annotations.controller.Delete;
import br.com.schumaker.force.framework.ioc.annotations.controller.PathVariable;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.HttpResponse;

import static br.com.schumaker.force.framework.web.handler.AbstractRequestHandler.container;
import static br.com.schumaker.force.framework.web.http.Http.HTTP_DELETE;

/**
 * The DeleteHandler class.
 * This class is responsible for processing HTTP_DELETE requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class DeleteHandler implements RequestHandler {

    /**
     * Process the HTTP_DELETE request.
     *
     * @param request the HTTP request.
     * @return the HTTP response.
     */
    @Override
    public HttpResponse processRequest(HttpRequest request) {
        var routeAndMethodPath = request.getControllerRouteAndMethodPath();
        String controllerRoute = routeAndMethodPath.first();
        String methodPath = routeAndMethodPath.second();

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var mappingAndMethodAndParams = controller.getMethod(methodPath, HTTP_DELETE);
            var mapping = mappingAndMethodAndParams.first();
            var parameters = mappingAndMethodAndParams.third();
            var arguments = new Object[parameters.size()];
            var pathVariables = controller.extractPathVariables(mapping, methodPath);

            for (short i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).isAnnotationPresent(PathVariable.class)) {
                    arguments[i] = AbstractRequestHandler.convertToType(pathVariables.get(i), parameters.get(i).getType());
                    continue;
                }

                if (parameters.get(i).getType().equals(HttpRequestHeader.class)) {
                    arguments[i] = new HttpRequestHeader(request.getRequestHeaders());
                }
            }

            var method = mappingAndMethodAndParams.second();
            var httpCode = method.getAnnotation(Delete.class).httpCode();
            var applicationType = method.getAnnotation(Delete.class).type();

            try {
                Object result = method.invoke(controller.getInstance(), arguments);
                var methodReturnType = method.getReturnType();
                return new HttpResponse(methodReturnType, result, httpCode, applicationType, request.exchange());
            } catch (Exception ex) {
                throw new ForceException("Error invoking method.", ex);
            }
        } else {
            var httpCode = Http.HTTP_404;
            var response = "Controller not found.";
            return new HttpResponse(String.class, response, httpCode, Http.APPLICATION_JSON, request.exchange());
        }
    }
}
