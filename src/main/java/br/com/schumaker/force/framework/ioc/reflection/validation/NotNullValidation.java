package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.annotations.validations.NotNull;
import br.com.schumaker.force.framework.annotations.validations.Validate;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.web.http.Http;

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
     * @throws ForceException if the field is null.
     */
    @Override
    public void validate(Object object, Field field) throws ForceException {
        try {
            field.setAccessible(true);
            if (field.get(object) == null) {
                var message = field.getAnnotation(NotNull.class).value();
                if (message.equals(NOT_NULL_VALIDATION_MESSAGE)) {
                    throw new ForceException(String.format(message, field.getName()), Http.HTTP_400);
                }
                throw new ForceException(message);
            }
        } catch (IllegalAccessException e) {
            throw new ForceException(e.getMessage());
        }
    }
}
