package br.com.schumaker.octopus.framework.web.view;

import br.com.schumaker.octopus.framework.jdbc.Sort;

/**
 * The Pageable interface.
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
 * @see PageRequest
 * @see Sort
 * @see Sort.Order
 * @see Sort.Direction
 * @see Page
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public interface Pageable {
    int pageNumber();
    int pageSize();
    Sort sort();
}
