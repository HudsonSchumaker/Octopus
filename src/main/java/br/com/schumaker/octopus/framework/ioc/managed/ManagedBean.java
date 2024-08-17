package br.com.schumaker.octopus.framework.ioc.managed;

/**
 * The ManagedBean class represents a managed bean within the IoC container.
 * It implements the ManagedClass interface and provides methods to retrieve the fully qualified name (FQN)
 * and the instance of the managed bean.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ManagedBean implements ManagedClass<ManagedBean> {
    private final String fqn;
    private final Object instance;

    private ManagedBean(String fqn, Object instance) {
        this.fqn = fqn;
        this.instance = instance;
    }

    /**
     * Creates a new ManagedBean instance using the specified configuration class and instance.
     *
     * @param configuration the configuration class
     * @param instance the instance of the configuration class
     * @return a new ManagedBean instance
     */
    public static ManagedBean builder(Class<?> configuration, Object instance) {
        var fqn = configuration.getName();
        return new ManagedBean(fqn, instance);
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
