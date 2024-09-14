package br.com.schumaker.octopus.framework.reflection.validation;

import br.com.schumaker.octopus.framework.annotations.validations.Email;
import br.com.schumaker.octopus.framework.annotations.validations.Max;
import br.com.schumaker.octopus.framework.annotations.validations.Min;
import br.com.schumaker.octopus.framework.annotations.validations.NotBlank;
import br.com.schumaker.octopus.framework.annotations.validations.NotEmpty;
import br.com.schumaker.octopus.framework.annotations.validations.NotNull;
import br.com.schumaker.octopus.framework.annotations.validations.Past;
import br.com.schumaker.octopus.framework.annotations.validations.Range;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.web.http.Http;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The ValidationReflection class provides utility methods for validating objects using reflection.
 * It validates fields annotated with validation annotations such as @NotNull, @NotBlank, @NotEmpty, @Email, @Min, @Max, @Range, and @Past.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @see NotNull
 * @see NotBlank
 * @see NotEmpty
 * @see Email
 * @see Min
 * @see Max
 * @see Range
 * @see Past
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ValidationReflection {
    private static final ValidationReflection INSTANCE = new ValidationReflection();

    public static final String NOT_NULL_VALIDATION_MESSAGE = "Field %s cannot be null";
    public static final String NOT_BLANK_VALIDATION_MESSAGE = "Field %s cannot be blank";
    public static final String NOT_EMPTY_VALIDATION_MESSAGE = "Field %s cannot be empty";
    public static final String MIN_VALIDATION_MESSAGE = "Field %s is below min value of %s";
    public static final String MAX_VALIDATION_MESSAGE = "Field %s exceeds max value of %s";
    public static final String EMAIL_VALIDATION_MESSAGE = "Field %s is not a valid email address";
    public static final String RANGE_VALIDATION_MESSAGE = "Field %s is out of range %s to %s";
    public static final String PAST_VALIDATION_MESSAGE = "Field %s must be in the past";

    private ValidationReflection() {}

    public static ValidationReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Validates the specified object using reflection.
     * Fields annotated with validation annotations are validated according to their constraints.
     *
     * @param object the object to validate
     * @throws OctopusException if a validation error occurs
     */
    public void validate(Object object) {
        List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
        fields.forEach(field -> {

            // TODO: improve conditional statements
            if (field.isAnnotationPresent(NotNull.class)) {
                validateNotNull(object, field);
            }

            if (field.isAnnotationPresent(NotBlank.class)) {
                validateNotBlank(object, field);
            }

            if (field.isAnnotationPresent(NotEmpty.class)) {
                validateNotEmpty(object, field);
            }

            if (field.isAnnotationPresent(Email.class)) {
                validateEmail(object, field);
            }

            if (field.isAnnotationPresent(Min.class)) {
                validateMin(object, field);
            }

            if (field.isAnnotationPresent(Max.class)) {
                validateMax(object, field);
            }

            if (field.isAnnotationPresent(Range.class)) {
                validateRange(object, field);
            }

            if (field.isAnnotationPresent(Past.class)) {
                validatePast(object, field);
            }
        });
    }

    /**
     * Validates that the specified field is not null.
     *
     * @param object the object to validate
     * @param field the field to validate
     */
    private void validateNotNull(Object object, Field field) {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue == null) {
                var message = field.getAnnotation(NotNull.class).value();
                if (message.equals(NOT_NULL_VALIDATION_MESSAGE)) {
                    throw new OctopusException(String.format(message, field.getName()), Http.HTTP_400);
                }
                throw new OctopusException(message);
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate: " + field.getName(), e);
        }
    }

    /**
     * Validates that the specified field is not blank.
     *
     * @param object the object to validate
     * @param field the field to validate
     */
    private void validateNotBlank(Object object, Field field) {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue instanceof String value) {
                if (value.isBlank()) {
                    var message = field.getAnnotation(NotBlank.class).value();
                    if (message.equals(NOT_BLANK_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName()), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate: " + field.getName(), e);
        }
    }

    /**
     * Validates that the specified field is not empty.
     *
     * @param object the object to validate
     * @param field the field to validate
     */
    private void validateNotEmpty(Object object, Field field) {
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

    /**
     * Validates that the specified field is a valid email address.
     *
     * @param object the object to validate
     * @param field the field to validate
     */
    private void validateEmail(Object object, Field field) {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue instanceof String email) {
                if (!EmailValidator.isValidEmail(email)) {
                    var message = field.getAnnotation(Email.class).value();
                    if (message.equals(EMAIL_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName()), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate field " + field.getName(), e);
        }
    }

    /**
     * Validates that the specified field is greater than or equal to the specified minimum value.
     *
     * @param object the object to validate
     * @param field the field to validate
     */
    public void validateMin(Object object, Field field) {
        try {
            field.setAccessible(true);
            double minValue = field.getAnnotation(Min.class).value();
            Object fieldValue = field.get(object);
            if (fieldValue instanceof Number) {
                double value = ((Number) fieldValue).doubleValue();
                if (value < minValue) {
                    var message = field.getAnnotation(Min.class).message();
                    if (message.equals(MIN_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName(), minValue), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate: " + field.getName(), e);
        }
    }

    /**
     * Validates that the specified field is less than or equal to the specified maximum value.
     *
     * @param object the object to validate
     * @param field the field to validate
     */
    private void validateMax(Object object, Field field) {
        try {
            field.setAccessible(true);
            double maxValue = field.getAnnotation(Max.class).value();
            Object fieldValue = field.get(object);
            if (fieldValue instanceof Number) {
                double value = ((Number) fieldValue).doubleValue();
                if (value > maxValue) {
                    var message = field.getAnnotation(Max.class).message();
                    if (message.equals(MAX_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName(), maxValue), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate: " + field.getName(), e);
        }
    }

    /**
     * Validates that the specified field is within the specified range.
     *
     * @param object the object to validate
     * @param field the field to validate
     */
    private void validateRange(Object object, Field field) {
        try {
            field.setAccessible(true);
            double minValue = field.getAnnotation(Range.class).min();
            double maxValue = field.getAnnotation(Range.class).max();
            Object fieldValue = field.get(object);
            if (fieldValue instanceof Number) {
                double value = ((Number) fieldValue).doubleValue();
                if (value < minValue || value > maxValue) {
                    var message = field.getAnnotation(Range.class).message();
                    if (message.equals(RANGE_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName(), minValue, maxValue), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate: " + field.getName(), e);
        }
    }

    /**
     * Validates that the specified field is in the past.
     *
     * @param object the object to validate
     * @param field the field to validate
     */
    private void validatePast(Object object, Field field) {
        try {
            field.setAccessible(true);
            Past pastAnnotation = field.getAnnotation(Past.class);
            Object fieldValue = field.get(object);

            if (fieldValue != null) {
                boolean isValidType = Arrays.stream(pastAnnotation.allowedTypes())
                        .anyMatch(type -> type.isInstance(fieldValue));

                if (!isValidType) {
                    throw new OctopusException("Field " + field.getName() + " is not of a valid type for @Past annotation");
                }

                boolean isPast = false;
                switch (fieldValue) {
                    case Date date -> isPast = date.before(new Date());
                    case LocalDate localDate -> isPast = localDate.isBefore(LocalDate.now());
                    case LocalDateTime localDateTime -> isPast = localDateTime.isBefore(LocalDateTime.now());
                    default -> {}
                }

                if (!isPast) {
                    var message = pastAnnotation.value();
                    if (message.equals(PAST_VALIDATION_MESSAGE)) {
                        throw new OctopusException(String.format(message, field.getName()), Http.HTTP_400);
                    }
                    throw new OctopusException(message);
                }
            }
        } catch (IllegalAccessException e) {
            throw new OctopusException("Failed to validate field " + field.getName(), e);
        }
    }
}
