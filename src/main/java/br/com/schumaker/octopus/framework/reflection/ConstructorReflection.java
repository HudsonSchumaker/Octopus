package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The ConstructorReflection class provides utility methods for instantiating classes using constructors with dependency injection.
 * It uses reflection to create instances of classes by injecting the required dependencies into the constructor parameters.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ConstructorReflection {
    private static final ConstructorReflection INSTANCE = new ConstructorReflection();
    private static final IoCContainer ioCContainer = IoCContainer.getInstance();

    private ConstructorReflection() {}

    public static ConstructorReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Instantiates a class using the specified constructor and injects the required dependencies into the constructor parameters.
     *
     * @param constructor the constructor to use for instantiation
     * @return the instantiated object
     * @throws OctopusException if an error occurs during instantiation
     */
    public Object instantiateWithInjectedBeans(Constructor<?> constructor) {
        List<Object> parameters = new ArrayList<>();
        for (Parameter parameter : this.getParameters(constructor)) {
            var parameterType = parameter.getType();
            var service = ioCContainer.getBean(parameterType.getName());
            if (service != null) {
                parameters.add(service.getInstance());
            }
        }

        return instantiate(constructor, parameters.toArray());
    }

    /**
     * Instantiates a class using the specified constructor and arguments.
     *
     * @param constructor the constructor to use for instantiation
     * @param args the arguments to pass to the constructor
     * @return the instantiated object
     * @throws OctopusException if an error occurs during instantiation
     */
    private Object instantiate(Constructor<?> constructor, Object... args) {
        try {
            if (constructor.getParameterCount() == args.length) {
                return constructor.newInstance(args);
            }

            // handle missing beans for injection in constructor parameters.
            var paramTypes = Arrays.stream(constructor.getParameters()).map(Parameter::getType).toList();
            var argTypes = Arrays.stream(args).map(Object::getClass).toList();
            var missingTypes = new ArrayList<Class<?>>();

            for (var paramType : paramTypes) {
                if (!argTypes.contains(paramType)) {
                    missingTypes.add(paramType);
                }
            }

            throw new OctopusException("Bean(s) missing for injection in constructor parameters: " + missingTypes);
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }

    /**
     * Retrieves the parameters of a constructor.
     *
     * @param constructor the constructor
     * @return a list of parameters
     * @throws OctopusException if an error occurs during retrieval
     */
    private List<Parameter> getParameters(Constructor<?> constructor) {
        try {
            return Arrays.stream(constructor.getParameters()).toList();
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }
}
