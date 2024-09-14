package br.com.schumaker.octopus.framework.reflection.validation;

import br.com.schumaker.octopus.framework.annotations.validations.NotNull;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.reflect.Field;

/**
 * The NotNullValidation class.
 * This class is used to validate that the specified field is not null.
 *
 * @see NotNull
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class NotNullValidation implements Validation {
    public static final String NOT_NULL_VALIDATION_MESSAGE = "Field %s cannot be null.";

    /**
     * Validates that the specified field is not null.
     *
     * @param object the object to validate.
     * @param field the field to validate.
     * @throws OctopusException if the field is null.
     */
    @Override
    public void validate(Object object, Field field) throws OctopusException {
        try {
            field.setAccessible(true);
            if (field.get(object) == null) {
                var message = field.getAnnotation(NotNull.class).value();
                if (message.equals(NOT_NULL_VALIDATION_MESSAGE)) {
                    throw new OctopusException(String.format(message, field.getName()), Http.HTTP_400);
                }
                throw new OctopusException(message);
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException(e.getMessage());
        }
    }
}
