package br.com.schumaker.octopus.framework.ioc;

import br.com.schumaker.octopus.framework.ioc.managed.ManagedBean;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedClass;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedComponent;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedConfiguration;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedController;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedGlobalExceptionHandler;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedRepository;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedService;

import java.util.ArrayList;
import java.util.List;

public class IoCContainer {
    private static final IoCContainer INSTANCE = new IoCContainer();
    private ManagedGlobalExceptionHandler globalExceptionHandler;

    private final List<ManagedClass<?>> managedClasses = new ArrayList<>();
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

    public ManagedConfiguration getConfiguration(String fqn) {
        var configuration = managedClasses.stream()
                .filter(it -> it.getFqn().equals(fqn))
                .findFirst();

        return (ManagedConfiguration) configuration.orElse(null);
    }

    public ManagedGlobalExceptionHandler getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    public ManagedBean getBean(String fqn) {
        var bean = managedClasses.stream()
                .filter(it -> it.getFqn().equals(fqn))
                .findFirst();

        return (ManagedBean) bean.orElse(null);
    }

    public ManagedService getService(String fqn) {
        var service = managedClasses.stream()
                .filter(it -> it.getFqn().equals(fqn))
                .findFirst();

        return (ManagedService) service.orElse(null);
    }

    public void registerGlobalExceptionHandler(List<Class<?>> clazz) {
        if (!clazz.isEmpty() && clazz.getFirst() != null) {
            this.globalExceptionHandler = ManagedGlobalExceptionHandler.builder(clazz.getFirst());
        }
    }

    public void registerConfiguration(List<Class<?>> configurations) {
        configurations.forEach(it -> this.managedClasses.add(ManagedConfiguration.builder(it)));
    }

    public void registerBean(ManagedBean bean) {
        this.managedClasses.add(bean);
    }

    public void registerComponent(List<Class<?>> components) {
        components.forEach(it -> this.managedClasses.add(ManagedComponent.builder(it)));
    }

    public void registerRepository(List<Class<?>> repositories) {
        repositories.forEach(it -> this.managedClasses.add(ManagedRepository.builder(it)));
    }

    public void registerService(List<Class<?>> services) {
        services.forEach(it -> this.managedClasses.add(ManagedService.builder(it)));
    }

    public void registerController(List<Class<?>> controllers) {
        controllers.forEach(it -> this.managedController.add(ManagedController.builder(it)));
    }
}
