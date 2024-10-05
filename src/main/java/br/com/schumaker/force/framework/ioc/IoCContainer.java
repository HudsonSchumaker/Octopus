package br.com.schumaker.force.framework.ioc;

import br.com.schumaker.force.framework.ioc.managed.ManagedBean;
import br.com.schumaker.force.framework.ioc.managed.ManagedClass;
import br.com.schumaker.force.framework.ioc.managed.ManagedComponent;
import br.com.schumaker.force.framework.ioc.managed.ManagedConfiguration;
import br.com.schumaker.force.framework.ioc.managed.ManagedController;
import br.com.schumaker.force.framework.ioc.managed.ManagedGlobalExceptionHandler;
import br.com.schumaker.force.framework.ioc.managed.ManagedRepository;
import br.com.schumaker.force.framework.ioc.managed.ManagedService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The IoCContainer class represents the Inversion of Control (IoC) container.
 * It manages the lifecycle and dependencies of various managed classes, including controllers, services, repositories, and more.
 * The container provides methods to retrieve and register these managed classes.
 *
 * @see ManagedBean
 * @see ManagedClass
 * @see ManagedService
 * @see ManagedComponent
 * @see ManagedController
 * @see ManagedRepository
 * @see ManagedConfiguration
 * @see ManagedGlobalExceptionHandler
 *
 * @author Hudson Schumaker
 * @version 1.2.0
 */
public final class IoCContainer implements IoC {
    private static final IoCContainer INSTANCE = new IoCContainer();
    private final Map<String, ManagedClass<?>> managedClasses = new HashMap<>();
    private final List<ManagedController> managedController = new ArrayList<>();
    private ManagedGlobalExceptionHandler globalExceptionHandler;

    private IoCContainer() {}

    public static IoCContainer getInstance() {
        return INSTANCE;
    }

    @Override
    public ManagedGlobalExceptionHandler getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    @Override
    public ManagedController getController(String url) {
        var controller = managedController.stream()
                .filter(it -> it.getRoute().equals(url))
                .findFirst();

        return controller.orElse(null);
    }

    @Override
    public ManagedConfiguration getConfiguration(String fqn) {
        return getManagedClass(fqn, ManagedConfiguration.class);
    }

    @Override
    public ManagedBean getBean(String fqn) {
        return getManagedClass(fqn, ManagedBean.class);
    }

    @Override
    public ManagedRepository getRepository(String fqn) {
        return getManagedClass(fqn, ManagedRepository.class);
    }

    @Override
    public ManagedService getService(String fqn) {
        return getManagedClass(fqn, ManagedService.class);
    }

    @Override
    public ManagedComponent getComponent(String fqn) {
        return getManagedClass(fqn, ManagedComponent.class);
    }

    @Override
    public void registerGlobalExceptionHandler(List<Class<?>> clazz) {
        if (!clazz.isEmpty() && clazz.getFirst() != null) {
            this.globalExceptionHandler = ManagedGlobalExceptionHandler.builder(clazz.getFirst());
        }
    }

    @Override
    public void registerConfiguration(List<Class<?>> configurations) {
        configurations.forEach(it -> this.managedClasses.put(it.getName(), ManagedConfiguration.builder(it)));
    }

    @Override
    public void registerBean(ManagedBean bean) {
        this.managedClasses.put(bean.getFqn(), bean);
    }

    @Override
    public void registerComponent(List<Class<?>> components) {
        components.forEach(it -> this.managedClasses.put(it.getName(), ManagedComponent.builder(it)));
    }

    @Override
    public void registerRepository(List<Class<?>> repositories) {
        repositories.forEach(it -> this.managedClasses.put(it.getName(), ManagedRepository.builder(it)));
    }

    @Override
    public void registerService(List<Class<?>> services) {
        services.forEach(it -> this.managedClasses.put(it.getName(), ManagedService.builder(it)));
    }

    @Override
    public void registerController(List<Class<?>> controllers) {
        controllers.forEach(it -> this.managedController.add(ManagedController.builder(it)));
    }

    /**
     * Get a managed class by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed class
     * @return the managed class
     */
    private <T> T getManagedClass(String fqn, Class<T> expectedType) {
        ManagedClass<?> managedClass = managedClasses.get(fqn);
        if (!expectedType.isInstance(managedClass)) {
            return null;
        }

        return expectedType.cast(managedClass);
    }
}
