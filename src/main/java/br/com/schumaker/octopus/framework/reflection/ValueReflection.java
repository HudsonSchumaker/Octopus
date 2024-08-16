package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.Value;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.Environment;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ValueReflection {
    private static final ValueReflection INSTANCE = new ValueReflection();
    private static final Environment environment = Environment.getInstance();
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

    private ValueReflection() {}

    public static ValueReflection getInstance() {
        return INSTANCE;
    }

    public Object injectFieldValue(Object instance) {
        try {
            for (Field field : instance.getClass().getDeclaredFields()) {
                var valueAnnotation = field.getAnnotation(Value.class);
                if (valueAnnotation != null) {
                    var key = valueAnnotation.value();
                    var type = field.getType();
                    var parser = typeParsers.get(type);
                    if (parser != null) {
                        field.setAccessible(true);
                        field.set(instance, parser.apply(key));
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }

    public Object injectParameterValue(Parameter parameter) {
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
            throw new OctopusException(e.getMessage());
        }
    }
}
