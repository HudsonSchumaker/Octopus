package br.com.schumaker.octopus.framework.ioc;

import br.com.schumaker.octopus.framework.io.PropertiesReader;
import java.util.Properties;

import static br.com.schumaker.octopus.framework.ioc.AppProperties.DEFAULT_VALUE_NAME;
import static br.com.schumaker.octopus.framework.ioc.AppProperties.SERVER_CONTEXT;
import static br.com.schumaker.octopus.framework.ioc.AppProperties.SERVER_PORT;

/**
 * The Environment class represents the environment configuration within the IoC container.
 * It provides methods to retrieve and set environment-specific properties, including server port and context.
 *
 * @see PropertiesReader
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class Environment {
    private static final String DEFAULT_VALUE_VALUE = "0";
    private static final String SERVER_PORT_DEFAULT = "8080";
    private static final String SERVER_CONTEXT_DEFAULT = "/";
    private static final String JWT_EXPIRATION_DEFAULT = "3600";

    private static final Environment INSTANCE = new Environment();
    private final Properties properties;
    private String environment = "";

    private Environment() {
        properties = PropertiesReader.loadProperties(environment);
        properties.putIfAbsent(DEFAULT_VALUE_NAME, DEFAULT_VALUE_VALUE);
    }

    /**
     * Retrieves the singleton instance of the Environment class.
     *
     * @return the Environment instance.
     */
    public static Environment getInstance() {
        return INSTANCE;
    }

    /**
     * Sets the environment configuration using the specified environment name.
     * The properties are loaded from the corresponding properties file.
     *
     * @param environment the environment name.
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
        properties.clear();
        properties.putAll(PropertiesReader.loadProperties(environment));
    }

    /**
     * Retrieves the value of the specified key from the environment properties.
     *
     * @param key the key.
     * @return the value of the key.
     */
    public String getKey(String key) {
        return properties.getProperty(key);
    }

    /**
     * Sets the value of the specified key in the environment properties.
     *
     * @param key the key.
     * @param value the value.
     */
    public void setKey(String key, String value) {
        properties.putIfAbsent(key, value);
    }

    /**
     * Retrieves the server port from the environment properties.
     *
     * @return the server port.
     */
    public Integer getServerPort() {
        return Integer.parseInt(properties.getProperty(SERVER_PORT, SERVER_PORT_DEFAULT));
    }

    /**
     * Retrieves the server context from the environment properties.
     *
     * @return the server context.
     */
    public String getServerContext() {
        return properties.getProperty(SERVER_CONTEXT, SERVER_CONTEXT_DEFAULT);
    }

    /**
     * Retrieves the JWT expiration time from the environment properties.
     *
     * @return the JWT expiration time.
     */
    public Long getJwtExpiration() {
        return Long.parseLong(properties.getProperty(AppProperties.JWT_EXPIRATION, JWT_EXPIRATION_DEFAULT));
    }
}
