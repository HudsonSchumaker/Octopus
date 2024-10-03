package br.com.schumaker.force.framework.ioc;

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
    public static final String FMK_NAME = "Force";
    public static final String FMK_VERSION = "0.27.1";
    public static final String FMK_AUTHOR = "Hudson Schumaker";

    // Application properties
    public static final String APP_NAME = "force.app.name";
    public static final String APP_PROPERTIES_FILE_NAME = "application.properties";
    public static final String DEFAULT_VALUE_NAME = "force.annotation.default.value";

    // Database properties
    public static final String DB_TYPE = "force.db.type";
    public static final String DB_URL = "force.db.url";
    public static final String DB_USER = "force.db.user";
    public static final String DB_PASSWORD = "force.db.password";
    public static final String DB_MAX_POOL_SIZE = "force.db.max.pool.size";

    // JWT properties
    public static final String JWT_SECRET = "force.jwt.secret";
    public static final String JWT_EXPIRATION = "force.jwt.expiration";

    // Server properties
    protected static final String SERVER_PORT = "force.server.port";
    protected static final String SERVER_CONTEXT = "force.server.context";

    private AppProperties() {}
}
