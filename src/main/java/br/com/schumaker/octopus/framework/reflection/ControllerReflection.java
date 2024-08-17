package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.*;
import br.com.schumaker.octopus.framework.web.http.HttpVerb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerReflection {
    public static String getControllerRoute(Class<?> controller) {
        return controller.getAnnotation(Controller.class).value();
    }

    public static Map<String, List<Triple<String, Method, List<Parameter>>>> getMethods(Class<?> controller) {
        // <VERB, List<Triple<mapping, method, List<parameters>>>>
        Map<String, List<Triple<String, Method, List<Parameter>>>> methods = new HashMap<>();

        var getMappingMethods = getMapping(controller, Get.class, HttpVerb.GET);
        var postMappingMethods = getMapping(controller, Post.class, HttpVerb.POST);
        var putMappingMethods = getMapping(controller, Put.class, HttpVerb.PUT);
        var patchMappingMethods = getMapping(controller, Patch.class, HttpVerb.PATCH);
        var deleteMappingMethods = getMapping(controller, Delete.class, HttpVerb.DELETE);

        methods.putAll(getMappingMethods);
        methods.putAll(postMappingMethods);
        methods.putAll(putMappingMethods);
        methods.putAll(patchMappingMethods);
        methods.putAll(deleteMappingMethods);

        return methods;
    }

    private static Map<String, List<Triple<String, Method, List<Parameter>>>> getMapping(Class<?> controller, Class<? extends Annotation> annotation, HttpVerb verb) {
        Map<String, List<Triple<String, Method, List<Parameter>>>> methods = new HashMap<>();
        List<Triple<String, Method, List<Parameter>>> list = new ArrayList<>();

        var declaredMethods = controller.getDeclaredMethods();
        for (var method : declaredMethods) {
            if (Modifier.isPublic(method.getModifiers())) {
                var routeMapping = method.getAnnotation(annotation);
                if (routeMapping != null) {
                    var value = getAnnotationValue(routeMapping);
                    var params = new ArrayList<>(List.of(method.getParameters()));
                    list.add(new Triple<>(value, method, params));
                }
            }
        }
        
        methods.put(verb.name(), list);
        return methods;
    }

    private static String getAnnotationValue(Annotation routeMapping) {
        if (routeMapping instanceof Get) {
            return ((Get) routeMapping).value();
        }

        if (routeMapping instanceof Post) {
            return ((Post) routeMapping).value();
        }

        if (routeMapping instanceof Put) {
            return ((Put) routeMapping).value();
        }

        if (routeMapping instanceof Patch) {
            return ((Patch) routeMapping).value();
        }

        if (routeMapping instanceof Delete) {
            return ((Delete) routeMapping).value();
        }

        return "";
    }
}
