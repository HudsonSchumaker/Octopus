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
 * @version 1.1.0
 */
public final class IoCContainer {
    private static final IoCContainer INSTANCE = new IoCContainer();
    private final Map<String, ManagedClass<?>> managedClasses = new HashMap<>();
    private final List<ManagedController> managedController = new ArrayList<>();
    private ManagedGlobalExceptionHandler globalExceptionHandler;

    private IoCContainer() {}

    public static IoCContainer getInstance() {
        return INSTANCE;
    }

    public ManagedGlobalExceptionHandler getGlobalExceptionHandler() {
        return globalExceptionHandler;
    }

    /**
     * Get a managed controller by its url mapping.
     *
     * @param url the route of the managed component
     * @return the managed component
     */
    public ManagedController getController(String url) {
        var controller = managedController.stream()
                .filter(it -> it.getRoute().equals(url))
                .findFirst();

        return controller.orElse(null);
    }

    // TODO: check why is not being used
    /**
     * Get a ManagedConfiguration by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed component
     * @return the managed component
     */
    public ManagedConfiguration getConfiguration(String fqn) {
        return getManagedClass(fqn, ManagedConfiguration.class);
    }

    /**
     * Get a ManagedBean by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed component
     * @return the managed component
     */
    public ManagedBean getBean(String fqn) {
        return getManagedClass(fqn, ManagedBean.class);
    }

    /**
     * Get a ManagedRepository by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed component
     * @return the managed component
     */
    public ManagedRepository getRepository(String fqn) {
        return getManagedClass(fqn, ManagedRepository.class);
    }

    /**
     * Get a ManagedService by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed service
     * @return the managed service
     */
    public ManagedService getService(String fqn) {
        return getManagedClass(fqn, ManagedService.class);
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

    /**
     * Register a global exception handler.
     *
     * @param clazz the global exception handler class to register
     */
    public void registerGlobalExceptionHandler(List<Class<?>> clazz) {
        if (!clazz.isEmpty() && clazz.getFirst() != null) {
            this.globalExceptionHandler = ManagedGlobalExceptionHandler.builder(clazz.getFirst());
        }
    }

    /**
     * Register a list of configurations.
     *
     * @param configurations the list of configuration classes to register
     */
    public void registerConfiguration(List<Class<?>> configurations) {
        configurations.forEach(it -> this.managedClasses.put(it.getName(), ManagedConfiguration.builder(it)));
    }

    /**
     * Register a managed class.
     *
     * @param bean the managed class to register
     */
    public void registerBean(ManagedBean bean) {
        this.managedClasses.put(bean.getFqn(), bean);
    }

    /**
     * Register a list of components.
     *
     * @param components the list of component classes to register
     */
    public void registerComponent(List<Class<?>> components) {
        components.forEach(it -> this.managedClasses.put(it.getName(), ManagedComponent.builder(it)));
    }

    /**
     * Register a list of repositories.
     *
     * @param repositories the list of repository classes to register
     */
    public void registerRepository(List<Class<?>> repositories) {
        repositories.forEach(it -> this.managedClasses.put(it.getName(), ManagedRepository.builder(it)));
    }

    /**
     * Register a list of services.
     *
     * @param services the list of service classes to register
     */
    public void registerService(List<Class<?>> services) {
        services.forEach(it -> this.managedClasses.put(it.getName(), ManagedService.builder(it)));
    }

    /**
     * Register a list of controllers.
     *
     * @param controllers the list of controller classes to register
     */
    public void registerController(List<Class<?>> controllers) {
        controllers.forEach(it -> this.managedController.add(ManagedController.builder(it)));
    }
}
