package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.ExceptionHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GlobalExceptionHandlerReflection class provides utility methods for handling global exception handler methods.
 * It retrieves methods annotated with @ExceptionHandler and maps them to their corresponding exception types and parameters.
 *
 * @see ExceptionHandler
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class GlobalExceptionHandlerReflection {

    /**
     * Retrieves the methods of the specified class that are annotated with \@ExceptionHandler.
     *
     * @param clazz the class to inspect
     * @return a map of exception types to their corresponding handler methods and parameters
     */
    public static Map<Class<?>, Pair<Method, List<Parameter>>> getMethods(Class<?> clazz) {
        Map<Class<?>, Pair<Method, List<Parameter>>> methods = new HashMap<>();

        var declaredMethods = clazz.getDeclaredMethods();
        for (var method : declaredMethods) {
            if (Modifier.isPublic(method.getModifiers())) {
                var handler = method.getAnnotation(ExceptionHandler.class);
                if (handler != null) {
                    var value = handler.value();
                    var params = new ArrayList<>(List.of(method.getParameters()));
                    methods.put(value, new Pair<>(method, params));
                }
            }
        }

        return methods;
    }
}
