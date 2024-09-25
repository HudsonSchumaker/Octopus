package br.com.schumaker.octopus.framework.ioc.reflection.validation;

import br.com.schumaker.octopus.framework.annotations.validations.Regex;
import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * The RegexValidation class.
 * This class is used to validate that the specified field matches a regular expression.
 *
 * @see Regex
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class RegexValidation implements Validation {

    @Override
    public void validate(Object object, Field field) throws OctopusException {
        Regex regex = field.getAnnotation(Regex.class);
        if (regex != null) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value != null && !Pattern.matches(regex.value(), value.toString())) {
                    throw new OctopusException(regex.message());
                }
            } catch (IllegalAccessException e) {
                throw new OctopusException("Failed to access field value", e);
            }
        }
    }
}
