package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;

import  java.util.Arrays;

import static br.com.schumaker.octopus.framework.web.http.Http.GET;

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
        //TODO: check this
        if (type == int.class || type == Integer.class) {
            var rawCast = (String) param;
            return Integer.parseInt(rawCast);
        }

        if (type == double.class || type == Double.class) {
            var rawCast = (String) param;
            return Double.parseDouble(rawCast);
        }

        // Add more type conversions as needed
        return param;
    }
}
