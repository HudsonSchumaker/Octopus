package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.reflection.MethodReflection;

/**
 * The ManagedConfiguration class represents a managed configuration within the IoC container.
 * It implements the ManagedClass interface and provides methods to retrieve the fully qualified name (FQN)
 * and the instance of the managed configuration.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ManagedConfiguration implements ManagedClass<ManagedConfiguration> {
    private final String fqn;
    private Object instance;

    private ManagedConfiguration(String fqn) {
        this.fqn = fqn;
    }

    /**
     * Creates a new ManagedConfiguration instance using the specified configuration class.
     * The instance is created using reflection.
     *
     * @param configuration the configuration class
     * @return a new ManagedConfiguration instance
     */
    public static ManagedConfiguration builder(Class<?> configuration) {
        var fqn = configuration.getName();
        var managedConfiguration = new ManagedConfiguration(fqn);
        managedConfiguration.instance = ClassReflection.getInstance().instantiate(configuration);
        handleBeanMethods(managedConfiguration.instance);

        return managedConfiguration;
    }

    private static void handleBeanMethods(Object instance) {
        MethodReflection.getInstance().instantiateBean(instance);
    }

    @Override
    public String getFqn() {
        return fqn;
    }

    @Override
    public Object getInstance() {
        return instance;
    }
}
