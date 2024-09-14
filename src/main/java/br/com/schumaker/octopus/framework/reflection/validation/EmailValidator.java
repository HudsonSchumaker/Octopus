package br.com.schumaker.octopus.framework.reflection.validation;

import java.util.regex.Pattern;

/**
 * The EmailValidator class.
 * This class is responsible for validating email.
 *
 * @author Hudson Schumaker
 * @since 1.0.0
 */
public final class EmailValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private EmailValidator() {}

    /**
     * Validate an email.
     *
     * @param email the email to be validated.
     * @return true if the email is valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
