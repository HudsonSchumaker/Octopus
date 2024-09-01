package br.com.schumaker.octopus.framework.model;

/**
 * A generic record that holds a trio of values.
 * Project: Octopus Framework
 *
 * @param <F> the type of the first value
 * @param <S> the type of the second value
 * @param <T> the type of the third value
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public record Triple<F, S, T>(F first, S second, T third) {}
