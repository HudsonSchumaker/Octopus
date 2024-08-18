package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;

import  java.util.Arrays;

import static br.com.schumaker.octopus.framework.web.http.Http.GET;

public class GetHandler implements Handler {
    private final IoCContainer container = IoCContainer.getInstance();

    @Override
    public HttpResponse processRequest(HttpRequest request) {
        int httpCode = Http.HTTP_200;
        String fullUrl = request.fullUrl();
        String response = "";

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
                // TODO: try to resolve this
                httpCode = method.getAnnotation(br.com.schumaker.octopus.framework.annotations.controller.Get.class).httpCode();

                var parameters = mappingAndMethodAndParams.third();
                var args = new Object[parameters.size()];
                var pathVariables = controller.extractPathVariables(mapping, methodPath);

                for (int i = 0; i < parameters.size(); i++) {
                    if (parameters.get(i).isAnnotationPresent(PathVariable.class)) {
                        args[i] = this.convertToType(pathVariables.get(i), parameters.get(i).getType());
                    }
                }

                try {
                    Object result = method.invoke(controller.getInstance(), args);
                    return new HttpResponse(methodReturnType, result, httpCode, request.exchange());
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

        // Send the response
       return new HttpResponse(String.class, response, httpCode, request.exchange());
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


    public static class PostHandler implements Handler {

        @Override
        public HttpResponse processRequest(HttpRequest request) {
            return null;
        }
    }
}
