package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.reflection.GlobalExceptionHandlerReflection;
import br.com.schumaker.octopus.framework.model.Pair;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

/**
 * The ManagedGlobalExceptionHandler class represents a managed global exception handler within the IoC container.
 * It provides methods to retrieve the fully qualified name (FQN), the instance of the managed global exception handler,
 * and the method to handle specific exceptions.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ManagedGlobalExceptionHandler {
    private final String fqn;
    private final Map<Class<?>, Pair<Method, List<Parameter>>> methods;
    private Object instance;

    private ManagedGlobalExceptionHandler(String fqn, Map<Class<?>, Pair<Method, List<Parameter>>> methods) {
        this.fqn = fqn;
        this.methods = methods;
    }

    /**
     * Creates a new ManagedGlobalExceptionHandler instance using the specified handler class.
     *
     * @param handler the handler class.
     * @return a new ManagedGlobalExceptionHandler instance.
     */
    public static ManagedGlobalExceptionHandler builder(Class<?> handler) {
        var fqn = handler.getName();
        var methods = GlobalExceptionHandlerReflection.getMethods(handler);

        var managedGlobalExceptionHandler = new ManagedGlobalExceptionHandler(fqn, methods);
        managedGlobalExceptionHandler.instance = ClassReflection.getInstance().instantiate(handler);

        return managedGlobalExceptionHandler;
    }

    /**
     * Retrieves the method and its parameters to handle the specified exception.
     *
     * @param exception the exception class.
     * @return a pair containing the method and its parameters.
     */
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
