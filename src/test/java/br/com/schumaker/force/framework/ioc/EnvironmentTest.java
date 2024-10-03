package br.com.schumaker.force.framework.ioc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The EnvironmentTest class.
 * This class is responsible for testing the Environment class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class EnvironmentTest {

    @Test
    public void testGetKey() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        String result = env.getKey("force.server.port");

        // Assert
        assertEquals("8080", result);
    }

    @Test
    public void testGetServerPort() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        Integer result = env.getServerPort();

        // Assert
        assertEquals(8080, result);
    }

    @Test
    public void testGetServerContext() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        String result = env.getServerContext();

        // Assert
        assertEquals("/", result);
    }
}
