package br.com.schumaker.force.framework.run;

import br.com.schumaker.force.framework.ioc.annotations.ForceApp;
import br.com.schumaker.force.framework.ioc.annotations.bean.Component;
import br.com.schumaker.force.framework.ioc.annotations.bean.Configuration;
import br.com.schumaker.force.framework.ioc.annotations.bean.Service;
import br.com.schumaker.force.framework.ioc.annotations.controller.Controller;
import br.com.schumaker.force.framework.ioc.annotations.db.Repository;
import br.com.schumaker.force.framework.ioc.annotations.exception.GlobalExceptionHandler;
import br.com.schumaker.force.framework.exception.ExceptionCodes;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.AppProperties;
import br.com.schumaker.force.framework.jdbc.SimpleConnectionPool;
import br.com.schumaker.force.framework.jdbc.SqlExecutor;
import br.com.schumaker.force.framework.web.WebServer;
import br.com.schumaker.force.framework.ioc.Environment;
import br.com.schumaker.force.framework.ioc.IoCContainer;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * The Force class is the main entry point for the Force framework.
 * It initializes the web server, handles command-line arguments, and registers various components, services, and controllers with the IoC container.
 * This class is responsible for bootstrapping the application and starting the web server.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class Force {
    private static final String HEALTH_PACKAGE = "br.com.schumaker.force.framework.web.health";
    private static final IoCContainer container = IoCContainer.getInstance();
    private static final Environment environment = Environment.getInstance();
    private static final CommandLineArgs commandLineArgs = CommandLineArgs.getInstance();
    private static WebServer webServer;

    /**
     * Runs the Force framework with the specified application class and command-line arguments.
     * It handles command-line arguments, prints the application banner, and registers various components, services, and controllers.
     *
     * @param clazz the application class to run.
     * @param args the command-line arguments.
     * @throws Exception if an error occurs during the application startup.
     */
    public static void run(Class<?> clazz, String[] args) throws Exception {
        handleCommandLineArgs(args);
        printBanner();
        executeSqlScripts();
        createManagedClasses(clazz);
        startWebServer();
    }

    /**
     * Starts the web server.
     */
    private static void startWebServer() {
        try {
            webServer = new WebServer(environment.getServerPort(), environment.getServerContext());
            webServer.start();
        } catch (Exception ex) {
            throw new ForceException(ex.getMessage(), ExceptionCodes.WEB_SERVER_INIT_ERROR.getCode());
        }
    }

    /**
     * Registers various components, services, and controllers with the IoC container.
     *
     * @param clazz the application class to run.
     * @throws Exception if an error occurs during the registration of components, services, and controllers.
     */
    private static void createManagedClasses(Class<?> clazz) throws Exception {
        ForceApp app = clazz.getAnnotation(ForceApp.class);
        var packageName = app.root();

        int totalTasks = 8;
        ProgressBar progressBar = new ProgressBar(totalTasks, 50);

        registerClassesWithAnnotation(packageName, GlobalExceptionHandler.class, container::registerGlobalExceptionHandler, progressBar, "GlobalExceptionHandler");
        registerClassesWithAnnotation(packageName, Configuration.class, container::registerConfiguration, progressBar, "Configurations");
        registerClassesWithAnnotation(packageName, Component.class, container::registerComponent, progressBar, "Components");
        registerClassesWithAnnotation(packageName, Repository.class, container::registerRepository, progressBar, "Repositories");
        registerClassesWithAnnotation(packageName, Service.class, container::registerService, progressBar, "Services");
        registerClassesWithAnnotation(packageName, Controller.class, container::registerController, progressBar, "Controllers");

        // health package
        registerClassesWithAnnotation(HEALTH_PACKAGE, Service.class, container::registerService, progressBar, "HealthService");
        registerClassesWithAnnotation(HEALTH_PACKAGE, Controller.class, container::registerController, progressBar, "HealthController");

        progressBar.complete();
    }

    /**
     * Registers classes with the specified annotation with the IoC container.
     *
     * @param packageName the package name to scan for classes.
     * @param annotation the annotation to search for.
     * @param registrar the class registrar to register the classes with.
     * @param progressBar the progress bar to update.
     * @param taskName the name of the task being executed.
     * @param <T> the type of the annotation.
     * @throws Exception if an error occurs during class registration.
     */
    private static <T> void registerClassesWithAnnotation(String packageName, Class<? extends Annotation> annotation, ClassRegistrar<T> registrar, ProgressBar progressBar, String taskName) throws Exception {
        List<Class<?>> classes = ClassScanner.getClassesWithAnnotation(packageName, annotation);
        registrar.register(classes);
        progressBar.update(1, taskName);
    }

    /**
     * Handles the command-line arguments and sets the environment accordingly.
     *
     * @param args the command-line arguments.
     */
    private static void handleCommandLineArgs(String[] args) {
        commandLineArgs.setArgs(args);
        String env = commandLineArgs.getArg(CommandLineArgs.ENV);
        if (env != null) {
            environment.setEnvironment(env);
        }
    }

    /**
     * Executes the DDL scripts to create the database schema.
     */
    private static void executeSqlScripts() {
        if (SimpleConnectionPool.getInstance().testConnection()) {
            System.out.println("SQL: Connection pool is ready.");
        }

        SqlExecutor.executeFromFile("/schema.sql");
        System.out.println("SQL: schema.sql executed.");
        SqlExecutor.executeFromFile("/data.sql");
        System.out.println("SQL: data.sql executed.");
    }

    /**
     * Prints the application banner to the console.
     */
    private static void printBanner() {
        System.out.println(
                """
                                      ,----..                                   \s
                            ,---,.   /   /   \\  ,-.----.     ,----..      ,---,.\s
                          ,'  .' |  /   .     : \\    /  \\   /   /   \\   ,'  .' |\s
                        ,---.'   | .   /   ;.  \\;   :    \\ |   :     :,---.'   |\s
                        |   |   .'.   ;   /  ` ;|   | .\\ : .   |  ;. /|   |   .'\s
                        :   :  :  ;   |  ; \\ ; |.   : |: | .   ; /--` :   :  |-,\s
                        :   |  |-,|   :  | ; | '|   |  \\ : ;   | ;    :   |  ;/|\s
                        |   :  ;/|.   |  ' ' ' :|   : .  / |   : |    |   :   .'\s
                        |   |   .''   ;  \\; /  |;   | |  \\ .   | '___ |   |  |-,\s
                        '   :  '   \\   \\  ',  / |   | ;\\  \\'   ; : .'|'   :  ;/|\s
                        |   |  |    ;   :    /  :   ' | \\.''   | '/  :|   |    \\\s
                        |   :  \\     \\   \\ .'   :   : :-'  |   :    / |   :   .'\s
                        |   | ,'      `---`     |   |.'     \\   \\ .'  |   | ,'  \s
                        `----'                  `---'        `---`    `----'    \s
                                                                              \s
                """
        );
        System.out.print(AppProperties.FMK_NAME + ", ");
        System.out.print("Version: " + AppProperties.FMK_VERSION + ", ");
        System.out.print("Server Port: " + environment.getServerPort() + ", ");
        System.out.println("Environment: " + (commandLineArgs.getArg("-env") == null ? "default" : commandLineArgs.getArg("-env")));
    }

    @FunctionalInterface
    private interface ClassRegistrar<T> {
        void register(List<Class<?>> classes) throws Exception;
    }
}
