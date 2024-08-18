package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ControllerReflection;
import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.reflection.Pair;
import br.com.schumaker.octopus.framework.reflection.Triple;
import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * The ManagedController class represents a managed controller within the IoC container.
 * It provides methods to retrieve the fully qualified name (FQN), route, and instance of the managed controller.
 * Additionally, it provides methods to retrieve specific HTTP methods based on the mapping and type.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ManagedController {
    private final String fqn;
    private final String route;
    private final Map<String, List<Triple<String, Method, List<Parameter>>>> methods;  // <VERB, List<Triple<mapping, method, List<parameters>>>>
    private Object instance;

    private ManagedController(String fqn, String route, Map<String, List<Triple<String, Method, List<Parameter>>>> methods) {
        this.fqn = fqn;
        this.route = route;
        this.methods = methods;
    }

    /**
     * Creates a new ManagedController instance using the specified controller class.
     *
     * @param controller the controller class
     * @return a new ManagedController instance
     */
    public static ManagedController builder(Class<?> controller) {
        var fqn = controller.getName();
        var route = ControllerReflection.getControllerRoute(controller);
        var methods = ControllerReflection.getMethods(controller);

        var managedController = new ManagedController(fqn, route, methods);
        managedController.instance = ClassReflection.getInstance().instantiate(controller);

        return managedController;
    }

    public Pair<Method, List<Parameter>> getMethod(String mapping, String type) {
        if (type.equalsIgnoreCase(Http.GET)) {
            return getGetMethod(mapping);
        }

        if (type.equalsIgnoreCase(Http.POST)) {
            return getPostMethod(mapping);
        }

        if (type.equalsIgnoreCase(Http.PUT)) {
            return getPutMethod(mapping);
        }

        if (type.equalsIgnoreCase(Http.PATCH)) {
            return getPatchMethod(mapping);
        }

        if (type.equalsIgnoreCase(Http.DELETE)) {
            return getDeleteMethod(mapping);
        }

        throw new RuntimeException(Http.HTTP_501_NOT_IMPLEMENTED);
    }

    private Pair<Method, List<Parameter>> getGetMethod(String mapping) {
        var list = methods.get(Http.GET);
        for (var triple : list) {
            if (triple.first().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.second(), triple.third());
            }
        }

        throw new RuntimeException(Http.HTTP_404_NOT_FOUND);
    }

    private Pair<Method, List<Parameter>> getPostMethod(String mapping) {
        var list = methods.get(Http.POST);
        for (var triple : list) {
            if (triple.first().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.second(), triple.third());
            }
        }

        throw new RuntimeException(Http.HTTP_404_NOT_FOUND);
    }

    private Pair<Method, List<Parameter>> getPutMethod(String mapping) {
        var list = methods.get(Http.PUT);
        for (var triple : list) {
            if (triple.first().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.second(), triple.third());
            }
        }

        throw new RuntimeException(Http.HTTP_404_NOT_FOUND);
    }

    private Pair<Method, List<Parameter>> getPatchMethod(String mapping) {
        var list = methods.get(Http.PATCH);
        for (var triple : list) {
            if (triple.first().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.second(), triple.third());
            }
        }

        throw new RuntimeException(Http.HTTP_404_NOT_FOUND);
    }

    private Pair<Method, List<Parameter>> getDeleteMethod(String mapping) {
        var list = methods.get(Http.DELETE);
        for (var triple : list) {
            if (triple.first().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.second(), triple.third());
            }
        }

        throw new RuntimeException(Http.HTTP_404_NOT_FOUND);
    }

    public String getFqn() {
        return fqn;
    }

    public String getRoute() {
        return route;
    }

    public Object getInstance() {
        return instance;
    }
}
