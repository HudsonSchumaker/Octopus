package br.com.schumaker.octopus.framework.web.view;

import java.util.List;

/**
 * The PageImpl class.
 * It is responsible for controlling the page operations.
 *
 * @param <T> the type of the content.
 *
 * @see Page
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PageImpl<T> implements Page<T> {
    private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;
    private final int totalPages;

    public PageImpl(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public boolean hasNext() {
        return pageNumber < totalPages - 1;
    }

    @Override
    public boolean hasPrevious() {
        return pageNumber > 0;
    }
}
