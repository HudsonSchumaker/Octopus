package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.ioc.annotations.validations.Email;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.web.http.Http;

import java.lang.reflect.Field;

/**
 *
 * The EmailValidation class.
 * This class is used to validate that the specified field is a valid email address.
 *
 * @see Email
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class EmailValidation implements Validation {
    public static final String EMAIL_VALIDATION_MESSAGE = "Field %s is not a valid email address.";

    /**
     * Validate the object field.
     *
     * @param object the object to be validated.
     * @param field the field to be validated.
     * @throws ForceException if the field is not a valid email address.
     */
    @Override
    public void validate(Object object, Field field) throws ForceException {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue instanceof String email) {
                if (!EmailValidator.isValidEmail(email)) {
                    var message = field.getAnnotation(Email.class).value();
                    if (message.equals(EMAIL_VALIDATION_MESSAGE)) {
                        throw new ForceException(String.format(message, field.getName()), Http.HTTP_400);
                    }
                    throw new ForceException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ForceException("Failed to validate field " + field.getName(), e);
        }
    }
}
