package br.com.schumaker.force.framework.io;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The PropertiesReaderTest class.
 * This class is responsible for testing the PropertiesReader class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PropertiesReaderTest {

    @Test
    public void testLoadProperties() {
        // Arrange
        Properties properties = PropertiesReader.loadProperties("");

        // Act & Assert
        assertNotNull(properties);
        assertEquals("testIssuer", properties.getProperty("force.app.name"));
        assertEquals("8080", properties.getProperty("force.server.port"));
        assertEquals("/", properties.getProperty("force.server.context"));
        assertEquals("testSecretKey", properties.getProperty("force.jwt.secret"));
        assertEquals("7200", properties.getProperty("force.jwt.expiration"));
        assertEquals("beer", properties.getProperty("product.name"));
    }

    @Test
    public void testLoadPropertiesWithEnvironmentVariable() {
        // Arrange
        Properties properties = PropertiesReader.loadProperties("");

        // Act & Assert
        assertNotNull(properties);
        assertEquals("testSecretKey", properties.getProperty("force.jwt.secret"));
    }

    @Test
    @Disabled("Ignoring this test for now")
    public void testResolvePlaceholders() {
        // Arrange
        System.setProperty("SECRET", "testSecret");
        String valueWithPlaceholder = "${SECRET}";
        String expectedValue = "testSecret";

        // Act
        String resolvedValue = PropertiesReader.resolvePlaceholders(valueWithPlaceholder);

        // Assert
        assertEquals(expectedValue, resolvedValue);
    }
}