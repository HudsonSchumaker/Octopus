package br.com.schumaker.octopus.framework.ioc.reflection.validation;

import br.com.schumaker.octopus.framework.exception.OctopusException;

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
     * @throws OctopusException if the validation fails.
     */
    void validate(Object object, Field field) throws OctopusException;
}
