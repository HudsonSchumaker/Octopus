package br.com.schumaker.octopus.framework.reflection.validation;

import br.com.schumaker.octopus.framework.annotations.validations.*;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ValidationReflectionTest {

    static class TestClass {
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

        // Getters and setters
        public void setNotNullField(String notNullField) { this.notNullField = notNullField; }
        public void setNotBlankField(String notBlankField) { this.notBlankField = notBlankField; }
        public void setNotEmptyField(String notEmptyField) { this.notEmptyField = notEmptyField; }
        public void setEmailField(String emailField) { this.emailField = emailField; }
        public void setMinField(int minField) { this.minField = minField; }
        public void setMaxField(int maxField) { this.maxField = maxField; }
        public void setRangeField(int rangeField) { this.rangeField = rangeField; }
        public void setPastField(LocalDate pastField) { this.pastField = pastField; }
    }

    @Test
    void testNotNullValidation() {
        TestClass testClass = new TestClass();
        testClass.setNotNullField(null);
        OctopusException exception = assertThrows(OctopusException.class, () -> ValidationReflection.getInstance().validate(testClass));
        assertTrue(exception.getMessage().contains("Field notNullField cannot be null"));
    }

    @Test
    void testNotBlankValidation() {
        TestClass testClass = new TestClass();

        testClass.setNotNullField("notNull");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));

        testClass.setNotBlankField(" ");

        OctopusException exception = assertThrows(OctopusException.class, () -> ValidationReflection.getInstance().validate(testClass));
        assertTrue(exception.getMessage().contains("Field notBlankField cannot be blank"));
    }

    @Test
    void testNotEmptyValidation() {
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));

        testClass.setNotEmptyField("");
        OctopusException exception = assertThrows(OctopusException.class, () -> ValidationReflection.getInstance().validate(testClass));
        assertTrue(exception.getMessage().contains("Field notEmptyField cannot be empty"));
    }

    @Test
    void testEmailValidation() {
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));

        testClass.setEmailField("invalid-email");
        OctopusException exception = assertThrows(OctopusException.class, () -> ValidationReflection.getInstance().validate(testClass));
        assertTrue(exception.getMessage().contains("Field emailField is not a valid email address"));
    }

    @Test
    void testMinValidation() {
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMaxField(64);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));

        testClass.setMinField(5);
        OctopusException exception = assertThrows(OctopusException.class, () -> ValidationReflection.getInstance().validate(testClass));
        assertTrue(exception.getMessage().contains("Field minField is below min value of 10.0"));
    }

    @Test
    void testMaxValidation() {
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setRangeField(8);
        testClass.setPastField(LocalDate.now().minusDays(1));

        testClass.setMaxField(150);
        OctopusException exception = assertThrows(OctopusException.class, () -> ValidationReflection.getInstance().validate(testClass));
        assertTrue(exception.getMessage().contains("Field maxField exceeds max value of 100.0"));
    }

    @Test
    void testRangeValidation() {
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setPastField(LocalDate.now().minusDays(1));

        testClass.setRangeField(15);
        OctopusException exception = assertThrows(OctopusException.class, () -> ValidationReflection.getInstance().validate(testClass));
        assertTrue(exception.getMessage().contains("Field rangeField is out of range 1.0 to 10.0"));
    }

    @Test
    void testPastValidation() {
        TestClass testClass = new TestClass();
        testClass.setNotNullField("notNull");
        testClass.setNotBlankField("notBlank");
        testClass.setNotEmptyField("notEmpty");
        testClass.setEmailField("jhonny@mail.com");
        testClass.setMinField(15);
        testClass.setMaxField(64);
        testClass.setRangeField(8);

        testClass.setPastField(LocalDate.now().plusDays(1));
        OctopusException exception = assertThrows(OctopusException.class, () -> ValidationReflection.getInstance().validate(testClass));
        assertTrue(exception.getMessage().contains("Field pastField must be in the past"));
    }
}