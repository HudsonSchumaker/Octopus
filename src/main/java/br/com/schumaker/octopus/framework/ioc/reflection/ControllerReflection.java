package br.com.schumaker.octopus.framework.ioc.reflection;

import br.com.schumaker.octopus.framework.annotations.controller.Controller;
import br.com.schumaker.octopus.framework.annotations.controller.Delete;
import br.com.schumaker.octopus.framework.annotations.controller.Get;
import br.com.schumaker.octopus.framework.annotations.controller.Head;
import br.com.schumaker.octopus.framework.annotations.controller.Options;
import br.com.schumaker.octopus.framework.annotations.controller.Patch;
import br.com.schumaker.octopus.framework.annotations.controller.Post;
import br.com.schumaker.octopus.framework.annotations.controller.Put;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.model.Triple;
import br.com.schumaker.octopus.framework.web.http.HttpVerb;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The ControllerReflection class provides utility methods for handling controller classes and their annotated methods.
 * It retrieves the route of the controller and maps HTTP verbs to their corresponding methods and parameters.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ControllerReflection {
    private static final Map<Class<? extends Annotation>, HttpVerb> annotationMap = new HashMap<>();

    /*
     * Initializes the annotation map with the supported HTTP verbs.
     */
    static {
        annotationMap.put(Get.class, HttpVerb.GET);
        annotationMap.put(Post.class, HttpVerb.POST);
        annotationMap.put(Put.class, HttpVerb.PUT);
        annotationMap.put(Patch.class, HttpVerb.PATCH);
        annotationMap.put(Delete.class, HttpVerb.DELETE);
        annotationMap.put(Head.class, HttpVerb.HEAD);
        annotationMap.put(Options.class, HttpVerb.OPTIONS);
    }

    /**
     * Retrieves the route of the specified controller class.
     *
     * @param controller the controller class.
     * @return the route of the controller.
     */
    public static String getControllerRoute(Class<?> controller) {
        return controller.getAnnotation(Controller.class).value();
    }

    /**
     * Retrieves the methods of the specified controller class, mapped by HTTP verbs.
     *
     * @param controller the controller class.
     * @return a map of HTTP verbs to their corresponding methods and parameters.
     */
    public static Map<String, List<Triple<String, Method, List<Parameter>>>> getMethods(Class<?> controller) {
        // <VERB, List<Triple<mapping, method, List<parameters>>>>
        Map<String, List<Triple<String, Method, List<Parameter>>>> methods = new HashMap<>();

        // Get all methods annotated with the specified annotation and HTTP verb
        for (var entry : annotationMap.entrySet()) {
            methods.putAll(getMapping(controller, entry.getKey(), entry.getValue()));
        }

        return methods;
    }

    /**
     * Retrieves the methods of the specified controller class annotated with the specified annotation and HTTP verb.
     *
     * @param controller the controller class.
     * @param annotation the annotation class.
     * @param verb the HTTP verb.
     * @return a map of HTTP verbs to their corresponding methods and parameters.
     */
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

    /**
     * Retrieves the value of the specified route mapping annotation.
     *
     * @param routeMapping the route mapping annotation.
     * @throws OctopusException if an error occurs while retrieving the annotation value.
     * @return the value of the route mapping annotation.
     */
    private static String getAnnotationValue(Annotation routeMapping) {
        try {
            Method valueMethod = routeMapping.annotationType().getMethod("value");
            return (String) valueMethod.invoke(routeMapping);
        } catch (Exception e) {
            throw new OctopusException("Failed to retrieve annotation value.", e);
        }
    }
}
