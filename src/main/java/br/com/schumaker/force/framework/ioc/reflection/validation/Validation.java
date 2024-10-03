package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.exception.ForceException;

import java.lang.reflect.Field;

/**
 * The ValidationStrategy interface.
 * This interface is used to validate the fields of an object.
 * It provides a single method to validate the object and field.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@FunctionalInterface
public interface Validation {

    /**
     * Validates the object and field.
     *
     * @param object the object to be validated.
     * @param field the field to be validated.
     * @throws ForceException if the validation fails.
     */
    void validate(Object object, Field field) throws ForceException;
}
