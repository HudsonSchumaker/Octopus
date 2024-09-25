package br.com.schumaker.octopus.framework.ioc.reflection.validation;

import br.com.schumaker.octopus.framework.annotations.validations.Max;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.web.http.Http;

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
     * @throws OctopusException if the field exceeds the maximum value.
     */
    @Override
    public void validate(Object object, Field field) throws OctopusException {
        try {
            field.setAccessible(true);
            double maxValue = field.getAnnotation(Max.class).value();
            Object fieldValue = field.get(object);
            if (fieldValue instanceof Number) {
                double value = ((Number) fieldValue).doubleValue();
                if (value > maxValue) {
                    var message = field.getAnnotation(Max.class).message();
                    if (message.equals(MAX_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName(), maxValue), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException(e.getMessage());
        }
    }
}
