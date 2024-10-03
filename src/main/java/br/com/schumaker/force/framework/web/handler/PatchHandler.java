package br.com.schumaker.force.framework.web.handler;

import br.com.schumaker.force.framework.ioc.annotations.controller.Patch;
import br.com.schumaker.force.framework.ioc.annotations.controller.PathVariable;
import br.com.schumaker.force.framework.ioc.annotations.controller.Payload;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpRequest;
import br.com.schumaker.force.framework.web.http.HttpRequestHeader;
import br.com.schumaker.force.framework.web.http.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static br.com.schumaker.force.framework.web.http.Http.HTTP_PATCH;

/**
 * The PatchHandler class.
 * This class is responsible for processing HTTP_PATCH requests.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class PatchHandler implements RequestHandler {
    private final IoCContainer container = IoCContainer.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Process the HTTP_PATCH request.
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
            var mappingAndMethodAndParams = controller.getMethod(methodPath, HTTP_PATCH);
            var mapping = mappingAndMethodAndParams.first();
            var method = mappingAndMethodAndParams.second();
            var methodReturnType = method.getReturnType();
            var parameters = mappingAndMethodAndParams.third();
            var arguments = new Object[parameters.size()];
            var pathVariables = controller.extractPathVariables(mapping, methodPath);

            for (short i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).isAnnotationPresent(PathVariable.class)) {
                    arguments[i] = AbstractRequestHandler.convertToType(pathVariables.get(i), parameters.get(i).getType());
                    continue;
                }

                if (parameters.get(i).isAnnotationPresent(Payload.class)) {
                    if (parameters.get(i).getType().equals(Map.class)) {
                        arguments[i] = extractPatchMessage(request);
                    } else {
                        AbstractRequestHandler.validateBody(request, parameters.get(i), i, arguments);
                    }
                    continue;
                }

                if (parameters.get(i).getType().equals(HttpRequestHeader.class)) {
                    arguments[i] = new HttpRequestHeader(request.getRequestHeaders());
                }
                // TODO: check if need to handle param without annotation.
            }

            try {
                var httpCode = method.getAnnotation(Patch.class).httpCode();
                var applicationType = method.getAnnotation(Patch.class).type();

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
     * Extracts the patch message from the request body.
     *
     * @param request the HTTP request.
     * @return a Map<String, Object> containing the patch message.
     */
    private Map<String, Object> extractPatchMessage(HttpRequest request) {
        try {
            String requestBody = request.readRequestBody();
            return objectMapper.readValue(requestBody, new TypeReference<Map<String, Object>>() {});
        } catch (Exception ex) {
            throw new ForceException("Error reading patch message from request body.", ex);
        }
    }
}
