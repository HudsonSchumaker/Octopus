package br.com.schumaker.octopus.framework.ioc;

public class AppProperties {

    protected static final String APP_NAME = "Octopus";
    protected static final String APP_VERSION = "0.0.0";
    protected static final String SERVER_PORT = "server.port";
    protected static final String SERVER_CONTEXT = "server.context";
    protected static final String DB_DRIVER = "db.driver";
    protected static final String DB_URL = "db.url";
    protected static final String DB_USER = "db.user";
    protected static final String DB_PASSWORD = "db.password";

    public static final String DEFAULT_VALUE_NAME = "oc.annotation.default.value";

    private AppProperties() {}
}
