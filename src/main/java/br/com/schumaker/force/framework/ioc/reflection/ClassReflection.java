package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.annotations.bean.Inject;
import br.com.schumaker.force.framework.annotations.bean.Value;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The ClassReflection class provides utility methods for instantiating classes and handling dependency injection.
 * It uses reflection to create instances of classes, inject field values, and handle constructor parameters.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ClassReflection {
    private static final ClassReflection INSTANCE = new ClassReflection();
    private static final IoCContainer iocContainer = IoCContainer.getInstance();
    private static final ValueReflection valueReflection = ValueReflection.getInstance();
    private static final InjectReflection injectReflection = InjectReflection.getInstance();
    private static final ConstructorReflection constructorReflection = ConstructorReflection.getInstance();

    private ClassReflection() {}

    public static ClassReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Instantiates a class using reflection, handling field injection and value annotations.
     *
     * @param clazz the class to instantiate.
     * @return the instantiated object.
     * @throws ForceException if an error occurs during instantiation.
     */
    public Object instantiate(Class<?> clazz) {
        try {
            var defaultConstructor = this.getDefaultConstructor(clazz);
            if (defaultConstructor.isPresent()) {
                Object instance = defaultConstructor.get().newInstance();
                return this.handleFieldInjectionAndValueAnnotation(instance);
            } else {
                var firstAvailableConstructor = this.getFirstAvailableConstructor(clazz);
                if (firstAvailableConstructor.getAnnotation(Inject.class) != null) {
                    Object instance = constructorReflection.instantiateWithInjectedBeans(firstAvailableConstructor);
                    return this.handleFieldInjectionAndValueAnnotation(instance);
                }

                List<Object> parameters = new ArrayList<>();
                for (Parameter parameter : this.getParameters(firstAvailableConstructor)) {
                    var parameterType = parameter.getType();
                    if (parameter.getAnnotation(Value.class) != null) {
                        var value = this.handleParameterValueAnnotation(parameter);
                        parameters.add(value);
                    } else {
                        var service = iocContainer.getService(parameterType.getName());
                        if (service != null) {
                            parameters.add(service.getInstance());
                            continue;
                        }
                        parameters.add(instantiate(parameterType));
                    }
                }

                Object instance = firstAvailableConstructor.newInstance(parameters.toArray());
                return handleFieldInjectionAndValueAnnotation(instance);
            }
        } catch (Exception e) {
            throw new ForceException(e.getMessage());
        }
    }

    /**
     * Handles the @Value annotation for a constructor parameter.
     *
     * @param parameter the constructor parameter.
     * @return the value to inject.
     */
    private Object handleParameterValueAnnotation(Parameter parameter) {
        return valueReflection.injectParameterValue(parameter);
    }

    /**
     * Handles field injection and value annotations for an instance.
     *
     * @param instance the instance to handle.
     * @return the instance with injected fields and values.
     */
    private Object handleFieldInjectionAndValueAnnotation(Object instance) {
        injectReflection.injectFieldBean(instance);
        return valueReflection.injectFieldValue(instance);

    }

    /**
     * Retrieves the default constructor of a class.
     *
     * @param clazz the class.
     * @return an optional containing the default constructor, or empty if not found.
     */
    private Optional<Constructor<?>> getDefaultConstructor(Class<?> clazz) {
        try {
            return Stream.of(clazz.getDeclaredConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 0)
                    .findFirst();

        } catch (Exception e) {
            throw new ForceException(e.getMessage());
        }
    }

    /**
     * Retrieves the first available constructor of a class.
     *
     * @param clazz the class.
     * @return the first available constructor.
     * @throws ForceException if an error occurs during retrieval.
     */
    private Constructor<?> getFirstAvailableConstructor(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructors()[0];
        } catch (Exception e) {
            throw new ForceException(e.getMessage());
        }
    }

    /**
     * Retrieves all constructors of a class.
     *
     * @param clazz the class.
     * @return a list of constructors.
     * @throws ForceException if an error occurs during retrieval.
     */
    private List<Constructor<?>> getConstructors(Class<?> clazz) {
        try {
            return Arrays.stream(clazz.getDeclaredConstructors()).toList();
        } catch (Exception e) {
            throw new ForceException(e.getMessage());
        }
    }

    /**
     * Retrieves the parameters of a constructor.
     *
     * @param constructor the constructor.
     * @return a list of parameters.
     * @throws ForceException if an error occurs during retrieval.
     */
    private List<Parameter> getParameters(Constructor<?> constructor) {
        try {
            return Arrays.stream(constructor.getParameters()).toList();
        } catch (Exception e) {
            throw new ForceException(e.getMessage());
        }
    }
}
