package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.ioc.annotations.controller.PathVariable;
import br.com.schumaker.force.framework.ioc.annotations.controller.Payload;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.model.ObjectMapperConfig;
import br.com.schumaker.force.framework.model.TypeConverter;
import br.com.schumaker.force.framework.ioc.reflection.validation.ValidationReflection;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * The PutHandler class.
 * This class is responsible for processing HTTP_POST, HTTP_PUT requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public abstract class AbstractRequestHandler implements RequestHandler {
    protected static final IoCContainer container = IoCContainer.getInstance();
    protected static final ObjectMapper objectMapper = ObjectMapperConfig.getInstance();
    protected static final ValidationReflection validationReflection = ValidationReflection.getInstance();

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
                    arguments[i] = convertToType(pathVariables.get(i), parameters.get(i).getType());
                    continue;
                }

                if (parameters.get(i).isAnnotationPresent(Payload.class)) {
                    validateBody(request, parameters.get(i), i, arguments);
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
                throw new ForceException("Error invoking method.", ex);
            }
        } else {
            int httpCode = Http.HTTP_404;
            String response = "Controller not found.";
            return new HttpResponse(String.class, response, httpCode, Http.APPLICATION_JSON, request.exchange());
        }
    }

    /**
     * Validates the request body.
     *
     * @param request    the request to be validated.
     * @param parameter  the parameter to be validated.
     * @param index      the index of the argument.
     * @param arguments  the arguments to be validated.
     */
    public static void validateBody(HttpRequest request, Parameter parameter, short index, Object[] arguments) {
        try {
            if (parameter.isAnnotationPresent(Validate.class)) {
                var requestBody = request.readRequestBody();
                var paramType = parameter.getType();

                Object paramObject = objectMapper.readValue(requestBody, paramType);
                validationReflection.validate(paramObject);
                arguments[index] = paramObject;
            }
        } catch (Exception ex) {
            throw new ForceException("Error reading request body.", ex);
        }
    }

    /**
     * Converts the parameter to the specified type.
     *
     * @param param the parameter to be converted.
     * @param type  the type to convert to.
     * @return the converted parameter.
     */
    public static Object convertToType(Object param, Class<?> type) {
        var parser = TypeConverter.typeParsers.get(type);
        if (parser != null) {
            return parser.apply((String) param);
        }
        return param;
    }
}
