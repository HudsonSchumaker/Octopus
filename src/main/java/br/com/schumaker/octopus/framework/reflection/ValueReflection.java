package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.bean.Value;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.Environment;
import br.com.schumaker.octopus.framework.model.TypeConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

/**
 * The ValueReflection class provides utility methods for injecting values into fields and parameters annotated with @Value.
 * It uses reflection to set the field values of an instance and parameter values with the corresponding values from the environment.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ValueReflection {
    private static final ValueReflection INSTANCE = new ValueReflection();
    private static final Environment environment = Environment.getInstance();

    private ValueReflection() {}

    public static ValueReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Injects the field values of the specified instance with the corresponding values from the environment.
     * Fields annotated with @Value are set with the appropriate values.
     *
     * @param instance the instance whose fields are to be injected
     * @return the instance with injected field values
     * @throws OctopusException if an error occurs during field injection
     */
    public Object injectFieldValue(Object instance) {
        try {
            for (Field field : instance.getClass().getDeclaredFields()) {
                var valueAnnotation = field.getAnnotation(Value.class);
                if (valueAnnotation != null) {
                    var key = valueAnnotation.value();
                    var type = field.getType();
                    var parser = TypeConverter.typeParsers.get(type);
                    if (parser != null) {
                        field.setAccessible(true);
                        String value = environment.getKey(key);
                        field.set(instance, parser.apply(value));
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }

    /**
     * Injects the value of the specified parameter with the corresponding value from the environment.
     * Parameters annotated with @Value are set with the appropriate values.
     *
     * @param parameter the parameter whose value is to be injected
     * @return the injected parameter value
     * @throws OctopusException if an error occurs during parameter injection
     */
    public Object injectParameterValue(Parameter parameter) {
        try {
            var valueAnnotation = parameter.getAnnotation(Value.class);
            if (valueAnnotation != null) {
                var key = valueAnnotation.value();
                var type = parameter.getType();
                var parser = TypeConverter.typeParsers.get(type);
                if (parser != null) {
                    String value = environment.getKey(key);
                    return parser.apply(value);
                }
            }
            return null;
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }
}
