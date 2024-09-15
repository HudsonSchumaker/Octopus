package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.annotations.controller.Payload;
import br.com.schumaker.octopus.framework.annotations.controller.Post;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.model.TypeConverter;
import br.com.schumaker.octopus.framework.reflection.validation.ValidationReflection;
import br.com.schumaker.octopus.framework.web.http.Http;
import br.com.schumaker.octopus.framework.web.http.HttpRequest;
import br.com.schumaker.octopus.framework.web.http.HttpRequestHeader;
import br.com.schumaker.octopus.framework.web.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Parameter;
import java.util.List;

import static br.com.schumaker.octopus.framework.web.http.Http.HTTP_POST;

/**
 * The PostHandler class.
 * This class is responsible for processing HTTP_POST requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class PostHandler implements RequestHandler {
    private final IoCContainer container = IoCContainer.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ValidationReflection validationReflection = ValidationReflection.getInstance();

    /**
     * Process the HTTP_POST request.
     *
     * @param request the HTTP request.
     * @return the HTTP response.
     */
    @Override
    public HttpResponse processRequest(HttpRequest request) {
        int httpCode = Http.HTTP_201;
        String applicationType = "";
        String response = "";

        var routeAndMethodPath = request.getControllerRouteAndMethodPath();
        String controllerRoute = routeAndMethodPath.first();
        String methodPath = routeAndMethodPath.second();

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var mappingAndMethodAndParams = controller.getMethod(methodPath, HTTP_POST);
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

                if (parameters.get(i).isAnnotationPresent(Payload.class)) {
                    this.validateBody(request, parameters, i, arguments);
                    continue;
                }

                if (parameters.get(i).getType().equals(HttpRequestHeader.class)) {
                    arguments[i] = new HttpRequestHeader(request.getRequestHeaders());
                }
            }

            httpCode = method.getAnnotation(Post.class).httpCode();
            applicationType = method.getAnnotation(Post.class).type();

            try {
                Object result = method.invoke(controller.getInstance(), arguments);
                return new HttpResponse(methodReturnType, result, httpCode, applicationType, request.exchange());
            } catch (Exception e) {
                throw new OctopusException("Error invoking method.", e);
            }
        } else {
            httpCode = Http.HTTP_404;
            response = "Controller not found!";
        }

        return new HttpResponse(String.class, response, httpCode, applicationType, request.exchange());
    }

    /**
     * Validates the request body.
     *
     * @param request the request to validate.
     * @param parameters the parameters to validate
     * @param index the index of the parameter to validate.
     * @param arguments the arguments to validate.
     * @throws OctopusException if an error occurs while validating the request body.
     */
    private void validateBody(HttpRequest request, List<Parameter> parameters, short index, Object[] arguments) {
        try {
            var requestBody = request.readRequestBody();
            var paramType = parameters.get(index).getType();
            Object paramObject = objectMapper.readValue(requestBody, paramType);

            if (parameters.get(index).isAnnotationPresent(Validate.class)) {
                validationReflection.validate(paramObject);
                arguments[index] = paramObject;
            }
        } catch (Exception e) {
            throw new OctopusException("Error reading request body.", e);
        }
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
