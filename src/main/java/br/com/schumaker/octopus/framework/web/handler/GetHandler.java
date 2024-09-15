package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.annotations.controller.Get;
import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.model.TypeConverter;
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
        int httpCode = Http.HTTP_200;
        String applicationType = "";
        String response = "";

        var routeAndMethodPath = request.getControllerRouteAndMethodPath();
        String controllerRoute = routeAndMethodPath.first();
        String methodPath = routeAndMethodPath.second();

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var mappingAndMethodAndParams = controller.getMethod(methodPath, HTTP_GET);

            var mapping = mappingAndMethodAndParams.first();
            var method = mappingAndMethodAndParams.second();
            var methodReturnType = method.getReturnType();
            var parameters = mappingAndMethodAndParams.third();
            var arguments = new Object[parameters.size()];
            var pathVariables = controller.extractPathVariables(mapping, methodPath);

            for (short i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).isAnnotationPresent(PathVariable.class)) {
                    arguments[i] = this.convertToType(pathVariables.get(i), parameters.get(i).getType());
                    continue;
                }

                if (parameters.get(i).getType().equals(HttpRequestHeader.class)) {
                    arguments[i] = new HttpRequestHeader(request.getRequestHeaders());
                }
            }

            httpCode = method.getAnnotation(Get.class).httpCode();
            applicationType = method.getAnnotation(Get.class).type();

            try {
                Object result = method.invoke(controller.getInstance(), arguments);
                return new HttpResponse(methodReturnType, result, httpCode, applicationType, request.exchange());
            } catch (Exception e) {
                throw new OctopusException("Error invoking method", e);
            }
        } else {
            httpCode = Http.HTTP_404;
            response = "Controller not found!";
        }

        return new HttpResponse(String.class, response, httpCode, applicationType, request.exchange());
    }

    /**
     * Converts the given parameter to the specified type.
     *
     * @param param the parameter to convert
     * @param type the type to convert to
     * @return the converted parameter
     */
    private Object convertToType(Object param, Class<?> type) {
        var parser = TypeConverter.typeParsers.get(type);
        if (parser != null) {
            return parser.apply((String) param);
        }
        return param;
    }
}
