package br.com.schumaker.octopus.framework.reflection.validation;

import br.com.schumaker.octopus.framework.annotations.validations.Min;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.reflect.Field;

/**
 * The MinValidation class.
 * This class is used to validate that the specified field is not below the minimum value.
 *
 * @see Min
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class MinValidation implements Validation {
    public static final String MIN_VALIDATION_MESSAGE = "Field %s is below min value of %s.";

    /**
     * Validates that the specified field is not below the minimum value.
     *
     * @param object the object to validate.
     * @param field the field to validate.
     * @throws OctopusException if the field is below the minimum value.
     */
    @Override
    public void validate(Object object, Field field) throws OctopusException {
        try {
            field.setAccessible(true);
            double minValue = field.getAnnotation(Min.class).value();
            Object fieldValue = field.get(object);
            if (fieldValue instanceof Number) {
                double value = ((Number) fieldValue).doubleValue();
                if (value < minValue) {
                    var message = field.getAnnotation(Min.class).message();
                    if (message.equals(MIN_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName(), minValue), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate: " + field.getName(), e);
        }
    }
}
