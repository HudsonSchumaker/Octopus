package br.com.schumaker.octopus.framework.ioc;

/**
 * The AppProperties class contains constants for application configuration properties.
 * These properties include application name, version, database configuration, and server settings.
 * The class is designed to be used as a centralized place for accessing application properties.
 *
 * @author Hudson Schumaker
 * @version 1.1.0
 */
public class AppProperties {
    // Framework properties
    public static final String FMK_NAME = "Octopus";
    public static final String FMK_VERSION = "0.19.4";
    public static final String FMK_AUTHOR = "Hudson Schumaker";

    // Application properties
    public static final String APP_NAME = "oc.app.name";
    public static final String APP_PROPERTIES_FILE_NAME = "application.properties";
    public static final String DEFAULT_VALUE_NAME = "oc.annotation.default.value";

    // Database properties
    public static final String DB_TYPE = "oc.db.type";
    public static final String DB_URL = "oc.db.url";
    public static final String DB_USER = "oc.db.user";
    public static final String DB_PASSWORD = "oc.db.password";

    // JWT properties
    public static final String JWT_SECRET = "oc.jwt.secret";
    public static final String JWT_EXPIRATION = "oc.jwt.expiration";

    // Server properties
    protected static final String SERVER_PORT = "oc.server.port";
    protected static final String SERVER_CONTEXT = "oc.server.context";

    private AppProperties() {}
}
