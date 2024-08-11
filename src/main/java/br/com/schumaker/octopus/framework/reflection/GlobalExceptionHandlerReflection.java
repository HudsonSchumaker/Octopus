package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.ExceptionHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalExceptionHandlerReflection {

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
