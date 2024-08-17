package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;

/**
 * The ManagedService class represents a managed service within the IoC container.
 * It implements the ManagedClass interface and provides methods to retrieve the fully qualified name (FQN)
 * and the instance of the managed service.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class ManagedService implements ManagedClass<ManagedService> {
    private final String fqn;
    private Object instance;

    private ManagedService(String fqn) {
        this.fqn = fqn;
    }

    /**
     * Creates a new ManagedService instance using the specified service class.
     * The instance is created using reflection.
     *
     * @param service the service class
     * @return a new ManagedService instance
     */
    public static ManagedService builder(Class<?> service) {
        var fqn = service.getName();
        var managedService = new ManagedService(fqn);
        managedService.instance = ClassReflection.getInstance().instantiate(service);
        return managedService;
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
