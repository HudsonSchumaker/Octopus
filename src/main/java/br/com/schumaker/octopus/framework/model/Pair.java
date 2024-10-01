package br.com.schumaker.octopus.framework.model;

/**
 * A generic record that holds a pair of values.
 * Project: Octopus Framework
 *
 * @param <F> the type of the first value.
 * @param <S> the type of the second value.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public record Pair<F, S>(F first, S second) {}
