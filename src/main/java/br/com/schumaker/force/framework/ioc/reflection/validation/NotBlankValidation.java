package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.ioc.annotations.validations.NotBlank;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.web.http.Http;

import java.lang.reflect.Field;

/**
 * The NotBlankValidation class.
 * This class is used to validate that the specified field is not blank.
 *
 * @see NotBlank
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class NotBlankValidation implements Validation {
    public static final String NOT_BLANK_VALIDATION_MESSAGE = "Field %s cannot be blank.";

    /**
     * Validates that the specified field is not blank.
     *
     * @param object the object to validate.
     * @param field the field to validate.
     * @throws ForceException if the field is blank.
     */
    @Override
    public void validate(Object object, Field field) throws ForceException {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue instanceof String value) {
                if (value.isBlank()) {
                    var message = field.getAnnotation(NotBlank.class).value();
                    if (message.equals(NOT_BLANK_VALIDATION_MESSAGE)) {
                        throw new ForceException(String.format(message, field.getName()), Http.HTTP_400);
                    }
                    throw new ForceException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ForceException(e.getMessage());
        }
    }
}
