package br.com.schumaker.octopus.framework.ioc;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.reflection.GlobalExceptionHandlerReflection;
import br.com.schumaker.octopus.framework.reflection.Pair;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public class ManagedGlobalExceptionHandler {
    private final String fqn;
    private final Map<Class<?>, Pair<Method, List<Parameter>>> methods;
    private Object instance;

    private ManagedGlobalExceptionHandler(String fqn, Map<Class<?>, Pair<Method, List<Parameter>>> methods) {
        this.fqn = fqn;
        this.methods = methods;
    }

    public static ManagedGlobalExceptionHandler builder(Class<?> handler) {
        var fqn = handler.getName();
        var methods = GlobalExceptionHandlerReflection.getMethods(handler);

        var managedGlobalExceptionHandler = new ManagedGlobalExceptionHandler(fqn, methods);
        managedGlobalExceptionHandler.instance = ClassReflection.getInstance().instantiate(handler);

        return managedGlobalExceptionHandler;
    }

    public Pair<Method, List<Parameter>> geMethod(Class<?> exception) {
        return methods.get(exception);
    }

    public String getFqn() {
        return fqn;
    }

    public Object getInstance() {
        return instance;
    }
}
