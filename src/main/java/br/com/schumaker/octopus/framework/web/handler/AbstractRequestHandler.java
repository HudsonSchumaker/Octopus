package br.com.schumaker.octopus.framework.web.handler;

import br.com.schumaker.octopus.framework.annotations.controller.PathVariable;
import br.com.schumaker.octopus.framework.annotations.controller.Payload;
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * The PutHandler class.
 * This class is responsible for processing HTTP_POST, HTTP_PUT requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public abstract class AbstractRequestHandler implements RequestHandler {
    protected final IoCContainer container = IoCContainer.getInstance();
    protected final ObjectMapper objectMapper = new ObjectMapper();
    protected final ValidationReflection validationReflection = ValidationReflection.getInstance();

    /**
     * Processes the request.
     *
     * @param request         the request to be processed.
     * @param httpMethod      the HTTP method to be processed.
     * @param annotationClass the annotation class to be processed.
     * @return the response.
     */
    protected HttpResponse processRequest(HttpRequest request, String httpMethod, Class<? extends Annotation> annotationClass) {
        var routeAndMethodPath = request.getControllerRouteAndMethodPath();
        String controllerRoute = routeAndMethodPath.first();
        String methodPath = routeAndMethodPath.second();

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var mappingAndMethodAndParams = controller.getMethod(methodPath, httpMethod);
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

            try {
                Annotation annotationInstance = method.getAnnotation(annotationClass);
                Method httpCodeMethod = annotationClass.getMethod("httpCode");
                Method typeMethod = annotationClass.getMethod("type");

                int httpCode = (int) httpCodeMethod.invoke(annotationInstance);
                String applicationType = (String) typeMethod.invoke(annotationInstance);

                Object result = method.invoke(controller.getInstance(), arguments);
                return new HttpResponse(methodReturnType, result, httpCode, applicationType, request.exchange());
            } catch (Exception ex) {
                throw new OctopusException("Error invoking method.", ex);
            }
        } else {
            int httpCode = Http.HTTP_404;
            String response = "Controller not found!";
            return new HttpResponse(String.class, response, httpCode, Http.APPLICATION_JSON, request.exchange());
        }
    }

    /**
     * Validates the request body.
     *
     * @param request    the request to be validated.
     * @param parameters the parameters to be validated.
     * @param index      the index of the parameter to be validated.
     * @param arguments  the arguments to be validated.
     */
    private void validateBody(HttpRequest request, List<Parameter> parameters, short index, Object[] arguments) {
        try {
            if (parameters.get(index).isAnnotationPresent(Validate.class)) {
                var requestBody = request.readRequestBody();
                var paramType = parameters.get(index).getType();

                Object paramObject = objectMapper.readValue(requestBody, paramType);
                validationReflection.validate(paramObject);
                arguments[index] = paramObject;
            }
        } catch (Exception ex) {
            throw new OctopusException("Error reading request body.", ex);
        }
    }

    /**
     * Converts the parameter to the specified type.
     *
     * @param param the parameter to be converted.
     * @param type  the type to convert to.
     * @return the converted parameter.
     */
    private Object convertToType(Object param, Class<?> type) {
        var parser = TypeConverter.typeParsers.get(type);
        if (parser != null) {
            return parser.apply((String) param);
        }
        return param;
    }
}
