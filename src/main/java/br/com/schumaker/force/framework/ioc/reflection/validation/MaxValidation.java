package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.ioc.annotations.validations.Max;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.web.http.Http;

import java.lang.reflect.Field;

/**
 * The MaxValidation class.
 * This class is used to validate that the specified field does not exceed the maximum value.
 *
 * @see Max
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class MaxValidation implements Validation {
    public static final String MAX_VALIDATION_MESSAGE = "Field %s exceeds max value of %s.";

    /**
     * Validates that the specified field does not exceed the maximum value.
     *
     * @param object the object to validate.
     * @param field the field to validate.
     * @throws ForceException if the field exceeds the maximum value.
     */
    @Override
    public void validate(Object object, Field field) throws ForceException {
        try {
            field.setAccessible(true);
            double maxValue = field.getAnnotation(Max.class).value();
            Object fieldValue = field.get(object);
            if (fieldValue instanceof Number) {
                double value = ((Number) fieldValue).doubleValue();
                if (value > maxValue) {
                    var message = field.getAnnotation(Max.class).message();
                    if (message.equals(MAX_VALIDATION_MESSAGE)) {
                        throw new ForceException(String.format(message, field.getName(), maxValue), Http.HTTP_400);
                    }
                    throw new ForceException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ForceException(e.getMessage());
        }
    }
}
