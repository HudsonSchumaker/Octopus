package br.com.schumaker.force.framework.jdbc;

import java.util.List;

/**
 * The Sort class.
 * It is responsible for sorting the query results.
 *
 * @param orders the orders.
 *
 * @author Hudson Schumaker
 * @version 1.0.1
 */
public record Sort(List<Order> orders) {

    /**
     * Creates a Sort instance.
     *
     * @param direction the direction.
     * @param property  the property.
     */
    public record Order(Direction direction, String property) {}

    public enum Direction {
        ASC, DESC
    }
}
