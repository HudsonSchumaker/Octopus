package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;

/**
 * The ManagedComponent class represents a managed component within the IoC container.
 * It implements the ManagedClass interface and provides methods to retrieve the fully qualified name (FQN)
 * and the instance of the managed component.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ManagedComponent implements ManagedClass<ManagedComponent> {
    private final String fqn;
    private Object instance;

    private ManagedComponent(String fqn) {
        this.fqn = fqn;
    }

    /**
     * Creates a new ManagedComponent instance using the specified component class.
     * The instance is created using reflection.
     *
     * @param component the component class
     * @return a new @ManagedComponent instance
     */
    public static ManagedComponent builder(Class<?> component) {
        var fqn = component.getName();
        var managedComponent = new ManagedComponent(fqn);
        managedComponent.instance = ClassReflection.getInstance().instantiate(component);

        return managedComponent;
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
