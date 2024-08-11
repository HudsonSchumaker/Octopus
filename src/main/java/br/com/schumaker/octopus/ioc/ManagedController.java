package br.com.schumaker.octopus.ioc;

import br.com.schumaker.octopus.reflection.ControllerReflection;
import br.com.schumaker.octopus.reflection.ClassReflection;
import br.com.schumaker.octopus.reflection.Pair;
import br.com.schumaker.octopus.reflection.Triple;
import br.com.schumaker.octopus.web.Http;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

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

    public static ManagedController builder(Class<?> controller) {
        var fqn = controller.getName();
        var route = ControllerReflection.getControllerRoute(controller);
        var methods = ControllerReflection.getMethods(controller);

        var managedController = new ManagedController(fqn, route, methods);
        managedController.instance = ClassReflection.getInstance().getInstance(controller);

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

        throw new RuntimeException(Http.HTTP_NOT_IMPLEMENTED);
    }

    private Pair<Method, List<Parameter>> getGetMethod(String mapping) {
        var list = methods.get(Http.GET);
        for (var triple : list) {
            if (triple.getFirst().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.getSecond(), triple.getThird());
            }
        }

        throw new RuntimeException(Http.HTTP_NOT_FOUND);
    }

    private Pair<Method, List<Parameter>> getPostMethod(String mapping) {
        var list = methods.get(Http.POST);
        for (var triple : list) {
            if (triple.getFirst().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.getSecond(), triple.getThird());
            }
        }

        throw new RuntimeException(Http.HTTP_NOT_FOUND);
    }

    private Pair<Method, List<Parameter>> getPutMethod(String mapping) {
        var list = methods.get(Http.PUT);
        for (var triple : list) {
            if (triple.getFirst().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.getSecond(), triple.getThird());
            }
        }

        throw new RuntimeException(Http.HTTP_NOT_FOUND);
    }

    private Pair<Method, List<Parameter>> getPatchMethod(String mapping) {
        var list = methods.get(Http.PATCH);
        for (var triple : list) {
            if (triple.getFirst().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.getSecond(), triple.getThird());
            }
        }

        throw new RuntimeException(Http.HTTP_NOT_FOUND);
    }

    private Pair<Method, List<Parameter>> getDeleteMethod(String mapping) {
        var list = methods.get(Http.DELETE);
        for (var triple : list) {
            if (triple.getFirst().equalsIgnoreCase(mapping)) {
                return new Pair<>(triple.getSecond(), triple.getThird());
            }
        }

        throw new RuntimeException(Http.HTTP_NOT_FOUND);
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
