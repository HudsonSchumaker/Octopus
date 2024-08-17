package br.com.schumaker.octopus.framework.io;

import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.io.InputStream;
import java.util.Properties;

import static br.com.schumaker.octopus.framework.ioc.AppProperties.APPLICATION_PROPERTIES_FILE_NAME;

public class PropertiesReader {
    public static Properties loadProperties(String environment) {
        Properties properties = new Properties();
        String fileName = APPLICATION_PROPERTIES_FILE_NAME;

        if (environment != null && !environment.isBlank()) {
            fileName = "application-" + environment + ".properties";
        }

        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                return properties;
            }

            properties.load(inputStream);
        } catch (Exception e) {
           throw new OctopusException(e.getMessage());
        }

        return properties;
    }
}
