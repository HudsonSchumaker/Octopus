package br.com.schumaker.octopus.framework.ioc.reflection.validation;

import br.com.schumaker.octopus.framework.annotations.validations.Future;
import br.com.schumaker.octopus.framework.annotations.validations.Validate;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * The FutureValidation class.
 * This class is used to validate that the specified field is in the future.
 *
 * @see Future
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class FutureValidation implements Validation {
    public static final String FUTURE_VALIDATION_MESSAGE = "Field %s must be a future date.";

    /**
     * Validates if the field is in the future.
     *
     * @param object the object to be validated.
     * @param field the field to be validated.
     * @throws OctopusException if the field is not in the future.
     */
    @Override
    public void validate(Object object, Field field) throws OctopusException {
        try {
            field.setAccessible(true);
            Future pastAnnotation = field.getAnnotation(Future.class);
            Object fieldValue = field.get(object);

            if (fieldValue != null) {
                boolean isValidType = Arrays.stream(pastAnnotation.allowedTypes())
                        .anyMatch(type -> type.isInstance(fieldValue));

                if (!isValidType) {
                    throw new OctopusException("Field " + field.getName() + " is not of a valid type for @Future annotation");
                }

                boolean isFuture = false;
                switch (fieldValue) {
                    case Date date -> isFuture = date.after(new Date());
                    case LocalDate localDate -> isFuture = localDate.isAfter(LocalDate.now());
                    case LocalDateTime localDateTime -> isFuture = localDateTime.isAfter(LocalDateTime.now());
                    case Instant instant -> isFuture = instant.isAfter(Instant.now());
                    default -> {}
                }

                if (!isFuture) {
                    var message = pastAnnotation.value();
                    if (message.equals(FUTURE_VALIDATION_MESSAGE)) {
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
