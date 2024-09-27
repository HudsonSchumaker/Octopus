package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.annotations.controller.Get;
import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.annotations.controller.QueryParam;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpRequestHeader;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;

import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_GET;

/**
 * The GetHandler class.
 * This class is responsible for processing HTTP_GET requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class GetHandler implements RequestHandler {
    private final IoCContainer container = IoCContainer.getInstance();

    /**
     * Process the HTTP_GET request.
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
            var mappingAndMethodAndParams = controller.getMethod(methodPath, HTTP_GET);
            var mapping = mappingAndMethodAndParams.first();
            var parameters = mappingAndMethodAndParams.third();
            var arguments = new Object[parameters.size()];
            var pathVariables = controller.extractPathVariables(mapping, methodPath);
            var queryParameters = request.getQueryParams();

            for (short i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).isAnnotationPresent(PathVariable.class)) {
                    arguments[i] = AbstractRequestHandler.convertToType(pathVariables.get(i), parameters.get(i).getType());
                    continue;
                }

                if (parameters.get(i).isAnnotationPresent(QueryParam.class)) {
                    var queryParams = parameters.get(i).getAnnotation(QueryParam.class);
                    String paramName = queryParams.value();
                    String defaultValue = queryParams.defaultValue();
                    boolean required = queryParams.required();

                    String value = queryParameters.getOrDefault(paramName, defaultValue);
                    if (required && value.isEmpty()) {
                        throw new OctopusException("Missing required query parameter: " + paramName);
                    }

                    arguments[i] = AbstractRequestHandler.convertToType(value, parameters.get(i).getType());
                    continue;
                }

                if (parameters.get(i).getType().equals(HttpRequestHeader.class)) {
                    arguments[i] = new HttpRequestHeader(request.getRequestHeaders());
                }
            }

            var method = mappingAndMethodAndParams.second();
            var httpCode = method.getAnnotation(Get.class).httpCode();
            var applicationType = method.getAnnotation(Get.class).type();

            try {
                Object result = method.invoke(controller.getInstance(), arguments);
                var methodReturnType = method.getReturnType();
                return new HttpResponse(methodReturnType, result, httpCode, applicationType, request.exchange());
            } catch (Exception ex) {
                throw new OctopusException("Error invoking method.", ex);
            }
        } else {
            var httpCode = Http.HTTP_404;
            var response = "Controller not found.";
            return new HttpResponse(String.class, response, httpCode, Http.APPLICATION_JSON, request.exchange());
        }
    }
}
