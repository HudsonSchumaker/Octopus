package br.com.schumaker.force.framework.security;

import br.com.schumaker.force.framework.annotations.bean.Filter;
import br.com.schumaker.force.framework.annotations.controller.Secured;
import br.com.schumaker.force.framework.exception.ForceSecurityException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.web.http.Http;
import br.com.schumaker.force.framework.web.http.HttpFilter;
import br.com.schumaker.force.framework.web.http.HttpRequest;

/**
 * The SecurityFilter class.
 * This class is responsible for processing the security filter.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Filter
public class SecurityFilter implements HttpFilter {
    private static final IoCContainer container = IoCContainer.getInstance();

    @Override
    public void doFilter(HttpRequest request) {
        var routeAndMethodPath = request.getControllerRouteAndMethodPath();
        String controllerRoute = routeAndMethodPath.first();
        String methodPath = routeAndMethodPath.second();

        var controller = container.getController(controllerRoute);
        if (controller != null) {
            var httpMethod = request.exchange().getRequestMethod();
            var mappingAndMethodAndParams = controller.getMethod(methodPath, httpMethod);
            var method = mappingAndMethodAndParams.second();

            if (method.isAnnotationPresent(Secured.class)) {
                var security = method.getAnnotation(Secured.class);
                var token = request.getRequestHeaders().get(JwtManager.JWT_HEADER);
                try {
                    var claims = JwtManager.validateToken(token);
                    if (claims == null) {
                        throw new ForceSecurityException(security.value(), Http.HTTP_401);
                    }
               } catch (Exception e) {
                   throw new ForceSecurityException(security.value(), Http.HTTP_401);
               }
            }
        }
    }
}
