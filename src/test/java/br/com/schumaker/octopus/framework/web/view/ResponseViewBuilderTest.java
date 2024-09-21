package br.com.schumaker.octopus.framework.web.view;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The ResponseViewBuilderTest class.
 * This class is responsible for testing the ResponseViewBuilder class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ResponseViewBuilderTest {

    @Test
    public void testConstructorWithHttpCode() {
        // Arrange
        ResponseViewBuilder<String> builder = new ResponseViewBuilder<>(200);

        // Act
        ResponseView<String> responseView = builder.build();

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertNotNull(responseView.getHeaders());
    }

    @Test
    public void testBody() {
        // Arrange
        ResponseViewBuilder<String> builder = new ResponseViewBuilder<>(200);
        builder.body("Test Body");

        // Act
        ResponseView<String> responseView = builder.build();

        // Assert
        assertEquals("Test Body", responseView.getBody());
    }

    @Test
    public void testHttpCode() {
        // Arrange
        ResponseViewBuilder<String> builder = new ResponseViewBuilder<>(200);
        builder.httpCode(404);

        // Act
        ResponseView<String> responseView = builder.build();

        // Assert
        assertEquals(404, responseView.getHttpCode());
    }

    @Test
    public void testHeaders() {
        // Arrange
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ResponseViewBuilder<String> builder = new ResponseViewBuilder<>(200);
        builder.headers(headers);

        // Act
        ResponseView<String> responseView = builder.build();

        // Assert
        assertEquals(headers, responseView.getHeaders());
    }

    @Test
    public void testAddHeader() {
        // Arrange
        ResponseViewBuilder<String> builder = new ResponseViewBuilder<>(200);
        builder.headers("Content-Type", "application/json");

        // Act
        ResponseView<String> responseView = builder.build();

        // Assert
        assertEquals("application/json", responseView.getHeaders().get("Content-Type"));
    }

    @Test
    public void testBuild() {
        // Arrange
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ResponseViewBuilder<String> builder = new ResponseViewBuilder<>(200);
        builder.body("Test Body").headers(headers);

        // Act
        ResponseView<String> responseView = builder.build();

        // Assert
        assertEquals(200, responseView.getHttpCode());
        assertEquals("Test Body", responseView.getBody());
        assertEquals(headers, responseView.getHeaders());
    }
}
