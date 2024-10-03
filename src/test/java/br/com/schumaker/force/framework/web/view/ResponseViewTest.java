package br.com.schumaker.force.framework.web.view;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The ResponseViewTest class.
 * This class is responsible for testing the ResponseView class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ResponseViewTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange & Act
        ResponseView<String> responseView = new ResponseView<>();

        // Assert
        assertEquals(204, responseView.getHttpCode());
        assertNull(responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testHttpCodeConstructor() {
        // Arrange & Act
        ResponseView<String> responseView = new ResponseView<>(200);

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertNull(responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testHttpCodeAndBodyConstructor() {
        // Arrange & Act
        ResponseView<String> responseView = new ResponseView<>(200, "Test Body");

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testHttpCodeAndHeadersConstructor() {
        // Arrange
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Act
        ResponseView<String> responseView = new ResponseView<>(200, headers);

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertNull(responseView.getBody());
        assertEquals(headers, responseView.getHeaders());
    }

    @Test
    public void testFullConstructor() {
        // Arrange
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        // Act
        ResponseView<String> responseView = new ResponseView<>(200, "Test Body", headers);

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertEquals(headers, responseView.getHeaders());
    }

    @Test
    public void testStaticOkBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.ok().body("Test Body").build();

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticCreatedBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.created().body("Test Body").build();

        // Assert
        assertEquals(201, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testStaticBadRequestBuilder() {
        // Arrange & Act
        ResponseView<String> responseView = ResponseView.badRequest().body("Test Body").build();

        // Assert
        assertEquals(400, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertNotNull(responseView.getHeaders());
    }
}
