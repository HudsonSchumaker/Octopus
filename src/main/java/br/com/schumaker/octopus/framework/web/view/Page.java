package br.com.schumaker.octopus.framework.web.view;

import java.util.List;

/**
 * The Page interface.
 * It is responsible for controlling the page operations.
 *
 * @param <T> the type of the content.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public interface Page<T> {
    List<T> getContent();
    int getPageNumber();
    int getPageSize();
    long getTotalElements();
    int getTotalPages();
    boolean hasNext();
    boolean hasPrevious();
}
