package br.com.schumaker.octopus.ioc;

import java.util.ArrayList;
import java.util.List;

public class IoCContainer {
    private static final IoCContainer INSTANCE = new IoCContainer();
    private final List<ManagedController> managedController = new ArrayList<>();
    private final List<ManagedService> managedService = new ArrayList<>();

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

    public void registerController(List<Class<?>> controllers) {
        controllers.forEach(it -> {
           managedController.add(ManagedController.builder(it));
        });
    }

    public void registerService(List<Class<?>> services) {
        services.forEach(it -> {
            managedService.add(ManagedService.builder(it));
        });
    }
}
