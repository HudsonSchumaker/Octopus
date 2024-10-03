package br.com.schumaker.force.framework.web.view;

import br.com.schumaker.force.framework.jdbc.Sort;

/**
 * The PageRequest class.
 * It is responsible for pagination.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * Pageable pageable = new PageRequest(0, 10, new Sort(List.of(new Sort.Order(Sort.Direction.ASC, "name"))));
 * }
 * </pre>
 *
 * @see Pageable
 * @see Sort
 * @see Sort.Order
 * @see Sort.Direction
 * @see Page
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public record PageRequest(int pageNumber, int pageSize, Sort sort) implements Pageable {}
