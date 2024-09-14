package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.model.TypeConverter;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;

import  java.util.Arrays;

import static br.com.schumaker.octopus.framework.web.http.Http.GET;

/**
 * The GetHandler class.
 * This class is responsible for processing GET requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class GetHandler implements RequestHandler {
    private final IoCContainer container = IoCContainer.getInstance();

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        String fullUrl = request.fullUrl();
        int httpCode = Http.HTTP_200;
        String applicationType = "";
        String response = "";

        var x = request.exchange().getRequestHeaders();

        // TODO: nightmare code, refactor this
        String[] split = fullUrl.split("/");
        String controllerRoute = split.length > 4 ? "/" + split[4] : "/";
        String methodPath = split.length > 5 ? "/" + String.join("/", Arrays.copyOfRange(split, 5, split.length)) : "/";

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var mappingAndMethodAndParams = controller.getMethod(methodPath, GET);

            if (mappingAndMethodAndParams != null) {
                var mapping = mappingAndMethodAndParams.first();
                var method = mappingAndMethodAndParams.second();
                var methodReturnType = method.getReturnType();
                var parameters = mappingAndMethodAndParams.third();
                var args = new Object[parameters.size()];
                var pathVariables = controller.extractPathVariables(mapping, methodPath);

                for (short i = 0; i < parameters.size(); i++) {
                    if (parameters.get(i).isAnnotationPresent(PathVariable.class)) {
                        args[i] = this.convertToType(pathVariables.get(i), parameters.get(i).getType());
                    }
                }

                // TODO: try to resolve this
                httpCode = method.getAnnotation(br.com.schumaker.octopus.framework.annotations.controller.Get.class).httpCode();
                applicationType = method.getAnnotation(br.com.schumaker.octopus.framework.annotations.controller.Get.class).type();

                try {
                    Object result = method.invoke(controller.getInstance(), args);
                    return new HttpResponse(methodReturnType, result, httpCode, applicationType, request.exchange());
                } catch (Exception e) {
                    throw new OctopusException("Error invoking method", e);
                }
            } else {
                httpCode = Http.HTTP_404;
                response = "Method not found!";
            }
        } else {
            httpCode = Http.HTTP_404;
            response = "Controller not found!";
        }

        return new HttpResponse(String.class, response, httpCode, applicationType, request.exchange());
    }

    private Object convertToType(Object param, Class<?> type) {
        var parser = TypeConverter.typeParsers.get(type);
        if (parser != null) {
            return parser.apply((String) param);
        }
        return param;
    }
}
