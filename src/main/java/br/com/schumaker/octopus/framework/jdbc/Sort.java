package br.com.schumaker.octopus.framework.jdbc;

import java.util.List;

/**
 * The Sort class.
 * @param orders
 *
 * @see Direction
 */
public record Sort(List<Order> orders) {

    public record Order(Direction direction, String property) {}

    public enum Direction {
        ASC, DESC
    }
}
