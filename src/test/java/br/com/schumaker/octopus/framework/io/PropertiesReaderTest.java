package br.com.schumaker.octopus.framework.io;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
