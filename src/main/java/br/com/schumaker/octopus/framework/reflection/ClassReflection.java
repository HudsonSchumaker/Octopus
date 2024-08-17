package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.Inject;
import br.com.schumaker.octopus.framework.annotations.Value;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ClassReflection {
    private static final ClassReflection INSTANCE = new ClassReflection();
    private static final ConstructorReflection constructorReflection = ConstructorReflection.getInstance();
    private static final ValueReflection valueReflection = ValueReflection.getInstance();
    private static final InjectReflection injectReflection = InjectReflection.getInstance();
    private static final IoCContainer iocContainer = IoCContainer.getInstance();

    private ClassReflection() {}

    public static ClassReflection getInstance() {
        return INSTANCE;
    }

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
            throw new OctopusException(e.getMessage());
        }
    }

    private Object handleParameterValueAnnotation(Parameter parameter) {
        return valueReflection.injectParameterValue(parameter);
    }

    private Object handleFieldInjectionAndValueAnnotation(Object instance) {
        injectReflection.injectFieldBean(instance);
        return valueReflection.injectFieldValue(instance);
    }

    private Optional<Constructor<?>> getDefaultConstructor(Class<?> clazz) {
        try {
            return Stream.of(clazz.getDeclaredConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 0)
                    .findFirst();

        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }

    private Constructor<?> getFirstAvailableConstructor(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructors()[0];
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }

    private List<Constructor<?>> getConstructors(Class<?> clazz) {
        try {
            return Arrays.stream(clazz.getDeclaredConstructors()).toList();
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }

    private List<Parameter> getParameters(Constructor<?> constructor) {
        try {
            return Arrays.stream(constructor.getParameters()).toList();
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }
}
