package br.com.schumaker.octopus.framework.io;

import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.io.InputStream;
import java.util.Properties;

import static br.com.schumaker.octopus.framework.ioc.AppProperties.APPLICATION_PROPERTIES_FILE_NAME;

/**
 * The \@PropertiesReader class is responsible for loading properties from a properties file.
 * It provides a method to load properties based on the specified environment.
 *
 * @see OctopusException
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class PropertiesReader {

    /**
     * Loads properties from a properties file based on the specified environment.
     * If the environment is not specified or is blank, it loads the default properties file.
     *
     * @param environment the environment name (e.g., "development", "production")
     * @return the loaded properties
     * @throws OctopusException if an error occurs while loading the properties
     */
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
