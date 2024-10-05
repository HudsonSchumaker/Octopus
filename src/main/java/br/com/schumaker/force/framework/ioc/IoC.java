package br.com.schumaker.force.framework.ioc;

import br.com.schumaker.force.framework.ioc.managed.*;

import java.util.List;

/**
 * The IoC interface represents the Inversion of Control (IoC) container.
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
 * @version 1.0.0
 */
public interface IoC {

    /**
     * Get the global exception handler.
     *
     * @return the global exception handler
     */
    ManagedGlobalExceptionHandler getGlobalExceptionHandler();

    /**
     * Get a managed controller by its url mapping.
     *
     * @param url the route of the managed component
     * @return the managed component
     */
    ManagedController getController(String url);

    /**
     * Get a ManagedConfiguration by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed component
     * @return the managed component
     */
    ManagedConfiguration getConfiguration(String fqn);

    /**
     * Get a ManagedBean by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed component
     * @return the managed component
     */
    ManagedBean getBean(String fqn);

    /**
     * Get a ManagedRepository by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed component
     * @return the managed component
     */
    ManagedRepository getRepository(String fqn);

    /**
     * Get a ManagedService by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed service
     * @return the managed service
     */
    ManagedService getService(String fqn);

    /**
     * Get a ManagedComponent by its fully qualified name.
     *
     * @param fqn the fully qualified name of the managed component
     * @return the managed component
     */
    ManagedComponent getComponent(String fqn);

    /**
     * Register a global exception handler.
     *
     * @param clazz the global exception handler class to register
     */
    void registerGlobalExceptionHandler(List<Class<?>> clazz);

    /**
     * Register a list of configurations.
     *
     * @param configurations the list of configuration classes to register
     */
    void registerConfiguration(List<Class<?>> configurations);

    /**
     * Register a managed class.
     *
     * @param bean the managed class to register
     */
    void registerBean(ManagedBean bean);

    /**
     * Register a list of components.
     *
     * @param components the list of component classes to register
     */
    void registerComponent(List<Class<?>> components);

    /**
     * Register a list of repositories.
     *
     * @param repositories the list of repository classes to register
     */
    void registerRepository(List<Class<?>> repositories);

    /**
     * Register a list of services.
     *
     * @param services the list of service classes to register
     */
    void registerService(List<Class<?>> services);

    /**
     * Register a list of controllers.
     *
     * @param controllers the list of controller classes to register
     */
    void registerController(List<Class<?>> controllers);
}
