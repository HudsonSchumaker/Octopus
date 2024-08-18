package br.com.schumaker.octopus.framework.reflection.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {

    @Test
    public void testValidEmail() {
        assertTrue(EmailValidator.isValidEmail("test@example.com"));
        assertTrue(EmailValidator.isValidEmail("user.name+tag+sorting@example.com"));
        assertTrue(EmailValidator.isValidEmail("user_name@example.co.in"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(EmailValidator.isValidEmail("plainaddress"));
        assertFalse(EmailValidator.isValidEmail("@missingusername.com"));
        assertFalse(EmailValidator.isValidEmail("username@.com"));
        assertFalse(EmailValidator.isValidEmail("username@.com."));
        assertFalse(EmailValidator.isValidEmail("username@com"));
    }

    @Test
    public void testNullEmail() {
        assertFalse(EmailValidator.isValidEmail(null));
    }
}