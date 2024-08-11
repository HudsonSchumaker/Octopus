package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.Value;
import br.com.schumaker.octopus.framework.ioc.Environment;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class ClassReflection {
    private static final ClassReflection INSTANCE = new ClassReflection();
    private static final Environment environment = Environment.getInstance();
    private static final IoCContainer iocContainer = IoCContainer.getInstance();
    private static final Map<Class<?>, Function<String, Object>> typeParsers = new HashMap<>();

    static {
        typeParsers.put(String.class, environment::getKey);
        typeParsers.put(Integer.class, key -> Integer.parseInt(environment.getKey(key)));
        typeParsers.put(int.class, key -> Integer.parseInt(environment.getKey(key)));
        typeParsers.put(Float.class, key -> Float.parseFloat(environment.getKey(key)));
        typeParsers.put(float.class, key -> Float.parseFloat(environment.getKey(key)));
        typeParsers.put(Double.class, key -> Double.parseDouble(environment.getKey(key)));
        typeParsers.put(double.class, key -> Double.parseDouble(environment.getKey(key)));
        typeParsers.put(Long.class, key -> Long.parseLong(environment.getKey(key)));
        typeParsers.put(long.class, key -> Long.parseLong(environment.getKey(key)));
        typeParsers.put(Boolean.class, key -> Boolean.parseBoolean(environment.getKey(key)));
        typeParsers.put(boolean.class, key -> Boolean.parseBoolean(environment.getKey(key)));
        typeParsers.put(Short.class, key -> Short.parseShort(environment.getKey(key)));
        typeParsers.put(short.class, key -> Short.parseShort(environment.getKey(key)));
        typeParsers.put(Character.class, key -> environment.getKey(key).charAt(0));
        typeParsers.put(char.class, key -> environment.getKey(key).charAt(0));
    }

    private ClassReflection() {}

    public static ClassReflection getInstance() {
        return INSTANCE;
    }

    public Object getInstance(Class<?> clazz) {
        try {
            var defaultConstructor = getDefaultConstructor(clazz);
            if (defaultConstructor.isPresent()) {
                return defaultConstructor.get().newInstance();
            } else {
                var firstAvailableConstructor = getFirstAvailableConstructor(clazz);
                List<Object> parameters = new ArrayList<>();

                for (Parameter parameter : getParameters(firstAvailableConstructor)) {
                    var parameterType = parameter.getType();
                    var valueAnnotation = parameter.getAnnotation(Value.class);
                    if (valueAnnotation != null) {
                        var value = handleParameterValueAnnotation(parameter);
                        parameters.add(value);
                    } else {
                        var service = iocContainer.getService(parameterType.getName());
                        if (service != null) {
                            parameters.add(service.getInstance());
                            continue;
                        }
                        parameters.add(getInstance(parameterType));
                    }
                }
                return firstAvailableConstructor.newInstance(parameters.toArray());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Constructor<?>> getDefaultConstructor(Class<?> clazz) {
        try {
            return Stream.of(clazz.getDeclaredConstructors())
                    .filter(constructor -> constructor.getParameterCount() == 0)
                    .findFirst();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Constructor<?> getFirstAvailableConstructor(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructors()[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Constructor<?>> getConstructors(Class<?> clazz) {
        try {
            return Arrays.stream(clazz.getDeclaredConstructors()).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Parameter> getParameters(Constructor<?> constructor) {
        try {
            return Arrays.stream(constructor.getParameters()).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object handleParameterValueAnnotation(Parameter parameter) {
        try {
            var valueAnnotation = parameter.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                var key = valueAnnotation.value();
                var type = parameter.getType();
                var parser = typeParsers.get(type);
                if (parser != null) {
                    return parser.apply(key);
                }
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
