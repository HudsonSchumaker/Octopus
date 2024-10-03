package br.com.schumaker.force.framework.web.view;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The PageImplTest class.
 * It is responsible for testing the PageImpl class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PageImplTest {

    @Test
    void testGetContent() {
        List<String> content = List.of("item1", "item2", "item3");
        PageImpl<String> page = new PageImpl<>(content, 0, 10, 3);
        assertEquals(content, page.getContent());
    }

    @Test
    void testGetPageNumber() {
        PageImpl<String> page = new PageImpl<>(List.of(), 1, 10, 0);
        assertEquals(1, page.getPageNumber());
    }

    @Test
    void testGetPageSize() {
        PageImpl<String> page = new PageImpl<>(List.of(), 0, 10, 0);
        assertEquals(10, page.getPageSize());
    }

    @Test
    void testGetTotalElements() {
        PageImpl<String> page = new PageImpl<>(List.of(), 0, 10, 100);
        assertEquals(100, page.getTotalElements());
    }

    @Test
    void testGetTotalPages() {
        PageImpl<String> page = new PageImpl<>(List.of(), 0, 10, 25);
        assertEquals(3, page.getTotalPages());
    }

    @Test
    void testHasNext() {
        PageImpl<String> page = new PageImpl<>(List.of(), 0, 10, 25);
        assertTrue(page.hasNext());
    }

    @Test
    void testHasPrevious() {
        PageImpl<String> page = new PageImpl<>(List.of(), 1, 10, 25);
        assertTrue(page.hasPrevious());
    }

    @Test
    void testHasNextWhenLastPage() {
        PageImpl<String> page = new PageImpl<>(List.of(), 2, 10, 25);
        assertFalse(page.hasNext());
    }

    @Test
    void testHasPreviousWhenFirstPage() {
        PageImpl<String> page = new PageImpl<>(List.of(), 0, 10, 25);
        assertFalse(page.hasPrevious());
    }
}
