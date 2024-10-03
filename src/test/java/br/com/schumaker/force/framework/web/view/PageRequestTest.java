package br.com.schumaker.force.framework.web.view;

import br.com.schumaker.force.framework.jdbc.Sort;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The PageRequestTest class.
 * It is responsible for PageRequest test.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PageRequestTest {

    @Test
    void testGetPageNumber() {
        PageRequest pageRequest = new PageRequest(1, 10, null);
        assertEquals(1, pageRequest.pageNumber());
    }

    @Test
    void testGetPageSize() {
        PageRequest pageRequest = new PageRequest(1, 10, null);
        assertEquals(10, pageRequest.pageSize());
    }

    @Test
    void testGetSort() {
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name");
        Sort sort = new Sort(List.of(order));
        PageRequest pageRequest = new PageRequest(1, 10, sort);
        assertEquals(sort, pageRequest.sort());
    }
}
