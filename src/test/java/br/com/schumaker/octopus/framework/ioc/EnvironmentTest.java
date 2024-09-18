package br.com.schumaker.octopus.framework.ioc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnvironmentTest {

    @Test
    public void testGetKey() {
        // Arrange
        Environment env = Environment.getInstance();

        // Act
        String result = env.getKey("oc.server.port");

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
