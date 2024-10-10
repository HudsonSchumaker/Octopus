package br.com.schumaker.force.framework.ioc.reflection.validation;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.annotations.validations.*;
import br.com.schumaker.force.framework.ioc.reflection.validation.ValidationReflection;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ValidationReflectionTest class.
 * This class is responsible for testing the ValidationReflection class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ValidationReflectionTest {
    public static class TestClass {
        @NotNull
        private String notNullField;

        @NotBlank
        private String notBlankField;

        @NotEmpty
        private String notEmptyField;

        @Email
        private String emailField;

        @Min(10)
        private int minField;

        @Max(100)
        private int maxField;

        @Range(min = 1, max = 10)
        private int rangeField;

        @Past
        private LocalDate pastField;

        @Future
        private LocalDate futureField;

        @Past("Not in the past")
        private LocalDateTime pastDateTimeField;

        @Future("Not in the future")
        private LocalDateTime futureDateTimeField;

        @Regex(value = "^(\\d{4}[- ]?){3}\\d{4}$", message = "Invalid credit card number")
        private String creditCardField;

        // Getters and setters
        public void setNotNullField(String notNullField) { this.notNullField = notNullField; }
        public void setNotBlankField(String notBlankField) { this.notBlankField = notBlankField; }
        public void setNotEmptyField(String notEmptyField) { this.notEmptyField = notEmptyField; }
        public void setEmailField(String emailField) { this.emailField = emailField; }
        public void setMinField(int minField) { this.minField = minField; }
        public void setMaxField(int maxField) { this.maxField = maxField; }
        public void setRangeField(int rangeField) { this.rangeField = rangeField; }
        public void setPastField(LocalDate pastField) { this.pastField = pastField; }
        public void setFutureField(LocalDate futureField) { this.futureField = futureField; }
        public void setPastDateTimeField(LocalDateTime pastDateTimeField) { this.pastDateTimeField = pastDateTimeField; }
        public void setFutureDateTimeField(LocalDateTime futureDateTimeField) { this.futureDateTimeField = futureDateTimeField; }
        public void setCreditCardField(String creditCardField) { this.creditCardField = creditCardField; }
    }

    @Test
    public void testNotNullValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField(null);

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field notNullField cannot be null."));
    }

    @Test
    public void testNotBlankValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setNotBlankField(" ");

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field notBlankField cannot be blank."));
    }

    @Test
    public void testNotEmptyValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setNotEmptyField("");

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field notEmptyField cannot be empty."));
    }

    @Test
    public void testEmailValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setEmailField("invalid-email");

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field emailField is not a valid email address."));
    }

    @Test
    public void testMinValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setMinField(5);

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field minField is below min value of 10.0."));
    }

    @Test
    public void testMaxValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setMaxField(150);

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field maxField exceeds max value of 100.0."));
    }

    @Test
    public void testRangeValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setRangeField(15);

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field rangeField is out of range 1.0 to 10.0."));
    }

    @Test
    public void testPastValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().plusDays(1));

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field pastField must be a past date."));
    }

    @Test
    public void testFutureValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setFutureField(LocalDate.now().minusDays(1));

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Field futureField must be a future date."));
    }

    @Test
    public void testPastValidationWithLocalDateTime() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setFutureField(LocalDate.now().plusDays(1));
        testClass.setPastDateTimeField(LocalDateTime.now().plusDays(1));

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Not in the past"));
    }

    @Test
    public void testFutureValidationWithLocalDateTime() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setFutureField(LocalDate.now().plusDays(1));
        testClass.setFutureDateTimeField(LocalDateTime.now().minusDays(1));

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Not in the future"));
    }

    @Test
    public void testRegexValidation() {
        // Arrange
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));
        testClass.setFutureField(LocalDate.now().plusDays(1));
        testClass.setCreditCardField("1234-5678-1234-5678");

        // Act & Assert
        assertDoesNotThrow(() -> ValidationReflection.getInstance().validate(testClass));

        // Arrange with invalid credit card number
        testClass.setCreditCardField("1234-5678-1234-567");

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> ValidationReflection.getInstance().validate(testClass));

        // Assert
        assertTrue(exception.getMessage().contains("Invalid credit card number"));
    }
}
