package br.com.schumaker.octopus.framework.ioc;

/**
 * The \@AppProperties class contains constants for application configuration properties.
 * These properties include application name, version, database configuration, and server settings.
 * The class is designed to be used as a centralized place for accessing application properties.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class AppProperties {
    public static final String APP_NAME = "Octopus";
    public static final String APP_VERSION = "0.10.0";
    public static final String DB_DRIVER = "oc.db.driver";
    public static final String DB_URL = "oc.db.url";
    public static final String DB_USER = "oc.db.user";
    public static final String DB_PASSWORD = "oc.db.password";
    public static final String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";
    public static final String DEFAULT_VALUE_NAME = "oc.annotation.default.value";

    protected static final String SERVER_PORT = "oc.server.port";
    protected static final String SERVER_CONTEXT = "oc.server.context";

    private AppProperties() {}
}
