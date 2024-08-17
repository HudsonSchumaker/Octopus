package br.com.schumaker.octopus.framework.run;

import br.com.schumaker.octopus.framework.annotations.*;
import br.com.schumaker.octopus.framework.exception.ExceptionCodes;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.web.WebServer;
import br.com.schumaker.octopus.framework.ioc.Environment;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;

import java.util.List;

public class Octopus {
    private static final WebServer ws;
    private static final IoCContainer container = IoCContainer.getInstance();
    private static final Environment environment = Environment.getInstance();

    static {
        try {
            ws = new WebServer(environment.getServerPort(), environment.getServerContext());
        } catch (Exception e) {
            throw new OctopusException(e.getMessage(), ExceptionCodes.WEB_SERVER_INIT_ERROR.getCode());
        }
    }

    public static void run(Class<?> clazz, String[] args) throws Exception {
        printBanner();
        OctopusApp app = clazz.getAnnotation(OctopusApp.class);
        var packageName = app.root();

        List<Class<?>> globalExceptionHandler = ClassScanner.getClassesWithAnnotation(packageName, GlobalExceptionHandler.class);
        container.registerGlobalExceptionHandler(globalExceptionHandler);

        List<Class<?>> configurationClasses = ClassScanner.getClassesWithAnnotation(packageName, Configuration.class);
        container.registerConfiguration(configurationClasses);

        List<Class<?>> componentClasses = ClassScanner.getClassesWithAnnotation(packageName, Component.class);
        container.registerComponent(componentClasses);

        List<Class<?>> repositoryClasses = ClassScanner.getClassesWithAnnotation(packageName, Repository.class);
       // container.registerRepository(repositoryClasses);

        List<Class<?>> serviceClasses = ClassScanner.getClassesWithAnnotation(packageName, Service.class);
        container.registerService(serviceClasses);

        List<Class<?>> controllerClasses = ClassScanner.getClassesWithAnnotation(packageName, Controller.class);
        container.registerController(controllerClasses);

        ws.start();
    }

    private static void printBanner() {
        System.out.println(Environment.AppProperties.APP_NAME);
        System.out.println("Version: " + Environment.AppProperties.APP_VERSION);
        System.out.println("""
                      ___           ___           ___           ___           ___           ___           ___    \s
                     /\\  \\         /\\  \\         /\\  \\         /\\  \\         /\\  \\         /\\__\\         /\\  \\   \s
                    /::\\  \\       /::\\  \\        \\:\\  \\       /::\\  \\       /::\\  \\       /:/  /        /::\\  \\  \s
                   /:/\\:\\  \\     /:/\\:\\  \\        \\:\\  \\     /:/\\:\\  \\     /:/\\:\\  \\     /:/  /        /:/\\ \\  \\ \s
                  /:/  \\:\\  \\   /:/  \\:\\  \\       /::\\  \\   /:/  \\:\\  \\   /::\\~\\:\\  \\   /:/  /  ___   _\\:\\~\\ \\  \\\s
                 /:/__/ \\:\\__\\ /:/__/ \\:\\__\\     /:/\\:\\__\\ /:/__/ \\:\\__\\ /:/\\:\\ \\:\\__\\ /:/__/  /\\__\\ /\\ \\:\\ \\ \\__\\
                 \\:\\  \\ /:/  / \\:\\  \\  \\/__/    /:/  \\/__/ \\:\\  \\ /:/  / \\/__\\:\\/:/  / \\:\\  \\ /:/  / \\:\\ \\:\\ \\/__/
                  \\:\\  /:/  /   \\:\\  \\         /:/  /       \\:\\  /:/  /       \\::/  /   \\:\\  /:/  /   \\:\\ \\:\\__\\ \s
                   \\:\\/:/  /     \\:\\  \\        \\/__/         \\:\\/:/  /         \\/__/     \\:\\/:/  /     \\:\\/:/  / \s
                    \\::/  /       \\:\\__\\                      \\::/  /                     \\::/  /       \\::/  /  \s
                     \\/__/         \\/__/                       \\/__/                       \\/__/         \\/__/   \s
                
                """);
    }
}
