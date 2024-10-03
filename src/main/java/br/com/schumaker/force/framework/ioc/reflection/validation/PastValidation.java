package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.ioc.annotations.validations.Past;
import br.com.schumaker.force.framework.ioc.annotations.validations.Validate;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.web.http.Http;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

/**
 * The PastValidation class.
 * This class is used to validate that the specified field is in the past.
 *
 * @see Past
 * @see Validate
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PastValidation implements Validation {
    public static final String PAST_VALIDATION_MESSAGE = "Field %s must be a past date.";

    /**
     * Validates if the field is in the past.
     *
     * @param object the object to be validated.
     * @param field the field to be validated.
     * @throws ForceException if the field is not in the past.
     */
    @Override
    public void validate(Object object, Field field) throws ForceException {
        try {
            field.setAccessible(true);
            Past pastAnnotation = field.getAnnotation(Past.class);
            Object fieldValue = field.get(object);

            if (fieldValue != null) {
                boolean isValidType = Arrays.stream(pastAnnotation.allowedTypes())
                        .anyMatch(type -> type.isInstance(fieldValue));

                if (!isValidType) {
                    throw new ForceException("Field " + field.getName() + " is not of a valid type for @Past annotation");
                }

                boolean isPast = false;
                switch (fieldValue) {
                    case Date date -> isPast = date.before(new Date());
                    case LocalDate localDate -> isPast = localDate.isBefore(LocalDate.now());
                    case LocalDateTime localDateTime -> isPast = localDateTime.isBefore(LocalDateTime.now());
                    case Instant instant -> isPast = instant.isBefore(Instant.now());
                    default -> {}
                }

                if (!isPast) {
                    var message = pastAnnotation.value();
                    if (message.equals(PAST_VALIDATION_MESSAGE)) {
                        throw new ForceException(String.format(message, field.getName()), Http.HTTP_400);
                    }
                    throw new ForceException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ForceException("Failed to validate: " + field.getName(), e);
        }
    }
}
