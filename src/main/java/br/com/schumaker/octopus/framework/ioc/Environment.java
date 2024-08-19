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
    private static final Environment INSTANCE = new Environment();
    private final Properties properties;
    private String environment = "";


    //TODO: documentation

    private Environment() {
        properties = PropertiesReader.loadProperties(environment);
        properties.putIfAbsent(DEFAULT_VALUE_NAME, DEFAULT_VALUE_VALUE);
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
        properties.clear();
        properties.putAll(PropertiesReader.loadProperties(environment));
    }

    public String getKey(String key) {
        return properties.getProperty(key);
    }

    public void setKey(String key, String value) {
        properties.putIfAbsent(key, value);
    }

    public Integer getServerPort() {
        return Integer.parseInt(properties.getProperty(SERVER_PORT, SERVER_PORT_DEFAULT));
    }

    public String getServerContext() {
        return properties.getProperty(SERVER_CONTEXT, SERVER_CONTEXT_DEFAULT);
    }
}
