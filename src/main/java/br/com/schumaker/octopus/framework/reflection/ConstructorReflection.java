package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstructorReflection {
    private static final ConstructorReflection INSTANCE = new ConstructorReflection();
    private static final IoCContainer ioCContainer = IoCContainer.getInstance();

    private ConstructorReflection() {}

    public static ConstructorReflection getInstance() {
        return INSTANCE;
    }

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

    private Object instantiate(Constructor<?> constructor, Object... args) {
        try {
            if (constructor.getParameterCount() == args.length) {
                return constructor.newInstance(args);
            }

            throw new OctopusException("No constructor found with the given parameters.");
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
