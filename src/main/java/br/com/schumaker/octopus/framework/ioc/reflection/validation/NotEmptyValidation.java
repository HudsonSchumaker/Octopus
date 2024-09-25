package br.com.schumaker.octopus.framework.ioc.reflection.validation;

import br.com.schumaker.octopus.framework.annotations.validations.NotEmpty;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.reflect.Field;

/**
 * The NotEmptyValidation class.
 * This class is used to validate that the specified field is not empty.
 *
 * @see NotEmpty
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class NotEmptyValidation implements Validation {
    public static final String NOT_EMPTY_VALIDATION_MESSAGE = "Field %s cannot be empty.";

    /**
     * Validates that the specified field is not empty.
     *
     * @param object the object to validate.
     * @param field the field to validate.
     * @throws OctopusException if the field is empty.
     */
    @Override
    public void validate(Object object, Field field) throws OctopusException {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue instanceof String value) {
                if (value.isEmpty()) {
                    var message = field.getAnnotation(NotEmpty.class).value();
                    if (message.equals(NOT_EMPTY_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName()), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate: " + field.getName(), e);
        }
    }
}
