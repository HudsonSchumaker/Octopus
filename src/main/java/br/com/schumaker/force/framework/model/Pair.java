package br.com.schumaker.force.framework.model;

/**
 * A generic record that holds a pair of values.
 * Project: Force Framework
 *
 * @param <F> the type of the first value.
 * @param <S> the type of the second value.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public record Pair<F, S>(F first, S second) {}
