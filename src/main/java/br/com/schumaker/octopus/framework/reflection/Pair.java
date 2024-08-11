package br.com.schumaker.octopus.framework.reflection;

/**
 * A generic record that holds a pair of values.
 * Project: Octopus Framework
 *
 * @param <F> the type of the first value
 * @param <S> the type of the second value
 */
public record Pair<F, S>(F first, S second) {}
