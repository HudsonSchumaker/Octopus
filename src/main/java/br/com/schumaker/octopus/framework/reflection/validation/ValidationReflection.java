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

public class ValidationReflection {
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

    public void validate(Object object) {
        List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
        fields.forEach(field -> {

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
