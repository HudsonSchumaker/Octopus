package br.com.schumaker.octopus.framework.ioc;

import br.com.schumaker.octopus.framework.io.PropertiesReader;

import java.util.Properties;

import static br.com.schumaker.octopus.framework.ioc.Environment.AppProperties.DEFAULT_VALUE_NAME;
import static br.com.schumaker.octopus.framework.ioc.Environment.AppProperties.SERVER_CONTEXT;
import static br.com.schumaker.octopus.framework.ioc.Environment.AppProperties.SERVER_PORT;

public class Environment {
    private static final String DEFAULT_VALUE_VALUE = "0";
    private static final String SERVER_PORT_DEFAULT = "8080";
    private static final String SERVER_CONTEXT_DEFAULT = "/";
    private static final Environment INSTANCE = new Environment();
    private final Properties properties;

    private Environment() {
        properties = PropertiesReader.loadProperties();
        properties.putIfAbsent(DEFAULT_VALUE_NAME, DEFAULT_VALUE_VALUE);
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public String getKey(String key) {
        return properties.getProperty(key);
    }

    public Integer getServerPort() {
        return Integer.parseInt(properties.getProperty(SERVER_PORT, SERVER_PORT_DEFAULT));
    }

    public String getServerContext() {
        return properties.getProperty(SERVER_CONTEXT, SERVER_CONTEXT_DEFAULT);
    }

    public static class AppProperties {

        public static final String APP_NAME = "Octopus";
        public static final String APP_VERSION = "0.0.0";
        public static final String SERVER_PORT = "server.port";
        public static final String SERVER_CONTEXT = "server.context";
        public static final String DB_DRIVER = "db.driver";
        public static final String DB_URL = "db.url";
        public static final String DB_USER = "db.user";
        public static final String DB_PASSWORD = "db.password";

        public static final String DEFAULT_VALUE_NAME = "oc.annotation.default.value";
    }
}
