package br.com.schumaker.octopus.framework.io;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PropertiesReaderTest {

    @Test
    public void testLoadProperties() {
        Properties properties = PropertiesReader.loadProperties();
        assertNotNull(properties);
        assertEquals("8080", properties.getProperty("server.port"));
        assertEquals("/", properties.getProperty("server.context"));
    }
}
