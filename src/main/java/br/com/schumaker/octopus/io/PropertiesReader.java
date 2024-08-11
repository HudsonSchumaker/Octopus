package br.com.schumaker.octopus.io;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    public static Properties loadProperties() {
        Properties properties = new Properties();
        String propertiesFileName = "application.properties";

        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (inputStream == null) {
                return properties;
            }

            properties.load(inputStream);
            String propertyValue = properties.getProperty("server.port");
            System.out.println("server.port: " + propertyValue);
        } catch (Exception e) {
           throw new RuntimeException(e);
        }

        return properties;
    }
}
