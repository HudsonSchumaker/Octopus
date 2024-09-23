package br.com.schumaker.octopus.framework.io;

import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.com.schumaker.octopus.framework.ioc.AppProperties.APP_PROPERTIES_FILE_NAME;

/**
 * The PropertiesReader class is responsible for loading properties from a properties file.
 * It provides a method to load properties based on the specified environment.
 *
 * @see OctopusException
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class PropertiesReader {
    private static final Pattern ENV_PATTERN = Pattern.compile("\\$\\{([^}]+)}");

    /**
     * Loads properties from a properties file based on the specified environment.
     * If the environment is not specified or is blank, it loads the default properties file.
     *
     * @param environment the environment name (e.g., "development", "production").
     * @return the loaded properties.
     * @throws OctopusException if an error occurs while loading the properties.
     */
    public static Properties loadProperties(String environment) {
        Properties properties = new Properties();
        String fileName = APP_PROPERTIES_FILE_NAME;

        if (environment != null && !environment.isBlank()) {
            fileName = "application-" + environment + ".properties";
        }

        try (InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                return properties;
            }

            properties.load(inputStream);

            // Resolve placeholders with environment variables
            properties.forEach((key, value) -> {
                String resolvedValue = resolvePlaceholders((String) value);
                properties.setProperty((String) key, resolvedValue);
            });

        } catch (Exception ex) {
           throw new OctopusException(ex.getMessage());
        }

        return properties;
    }

    /**
     * Resolves placeholders in the specified value with environment variables.
     *
     * @param value the value to resolve.
     * @return the resolved value.
     */
    public static String resolvePlaceholders(String value) {
        Matcher matcher = ENV_PATTERN.matcher(value);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            String envVar = matcher.group(1);
            String envValue = System.getenv(envVar);
            matcher.appendReplacement(buffer, envValue != null ? envValue : "");
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
