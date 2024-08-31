package br.com.schumaker.octopus.framework.exception;

/**
 * The ErrorView record represents an error view with a message and a code.
 * It is used to encapsulate error details in a structured format.
 *
 * @param message the error message
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public record ErrorView(String message, String code) {}
