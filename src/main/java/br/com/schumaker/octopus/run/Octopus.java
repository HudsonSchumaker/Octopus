package br.com.schumaker.octopus.run;

import br.com.schumaker.octopus.annotations.Controller;
import br.com.schumaker.octopus.annotations.OctopusApp;
import br.com.schumaker.octopus.annotations.Service;
import br.com.schumaker.octopus.ioc.Environment;
import br.com.schumaker.octopus.ioc.IoCContainer;
import br.com.schumaker.octopus.web.WebServer;

import java.util.List;

public class Octopus {
    private static final WebServer ws;
    private static final IoCContainer container = IoCContainer.getInstance();
    private static final Environment environment = Environment.getInstance();

    static {
        try {
            ws = new WebServer(environment.getServerPort(), environment.getServerContext());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(Class<?> clazz, String[] args) throws Exception {
        OctopusApp app = clazz.getAnnotation(OctopusApp.class);
        var packageName = app.root();

        List<Class<?>> serviceClasses = ClassScanner.getClassesWithAnnotation(packageName, Service.class);
        container.registerService(serviceClasses);

        List<Class<?>> controllerClasses = ClassScanner.getClassesWithAnnotation(packageName, Controller.class);
        container.registerController(controllerClasses);


        for (Class<?> clazzz : controllerClasses) {
            System.out.println(clazzz.getName());
            var ann =  clazzz.getAnnotation(Controller.class);
            System.out.println(ann.value());

        }


        ws.start();

    }
}
