package br.com.schumaker.octopus.framework.io;

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
        assertEquals("8080", properties.getProperty("oc.server.port"));
        assertEquals("/", properties.getProperty("oc.server.context"));
    }
}
