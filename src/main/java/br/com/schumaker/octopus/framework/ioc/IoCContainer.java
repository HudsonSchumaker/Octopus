package br.com.schumaker.octopus.framework.ioc;

import java.util.ArrayList;
import java.util.List;

public class IoCContainer {
    private static final IoCContainer INSTANCE = new IoCContainer();
    private ManagedGlobalExceptionHandler globalExceptionHandler;
    private final List<ManagedService> managedService = new ArrayList<>();
    private final List<ManagedController> managedController = new ArrayList<>();

    private IoCContainer() {}

    public static IoCContainer getInstance() {
        return INSTANCE;
    }

    public ManagedController getController(String url) {
        var controller = managedController.stream()
                .filter(it -> it.getRoute().equals(url))
                .findFirst();

        return controller.orElse(null);
    }

    public ManagedService getService(String fqn) {
        var service = managedService.stream()
                .filter(it -> it.getFqn().equals(fqn))
                .findFirst();

        return service.orElse(null);
    }

    public ManagedGlobalExceptionHandler getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    public void registerGlobalExceptionHandler(List<Class<?>> clazz) {
        if (!clazz.isEmpty() && clazz.getFirst() != null) {
            globalExceptionHandler = ManagedGlobalExceptionHandler.builder(clazz.getFirst());
        }
    }

    public void registerService(List<Class<?>> services) {
        services.forEach(it -> {
            managedService.add(ManagedService.builder(it));
        });
    }

    public void registerController(List<Class<?>> controllers) {
        controllers.forEach(it -> {
           managedController.add(ManagedController.builder(it));
        });
    }
}
