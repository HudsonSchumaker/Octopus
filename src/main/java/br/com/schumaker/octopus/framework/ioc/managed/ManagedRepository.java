package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;

/**
 * The ManagedRepository class represents a managed repository within the IoC container.
 * It implements the ManagedClass interface and provides methods to retrieve the fully qualified name (FQN)
 * and the instance of the managed repository.
 *
 * @see ManagedClass
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class ManagedRepository implements ManagedClass<ManagedRepository> {
    private final String fqn;
    private Object instance;

    private ManagedRepository(String fqn) {
        this.fqn = fqn;
    }

    /**
     * Creates a new ManagedRepository instance using the specified repository class.
     * The instance is created using reflection.
     *
     * @param repository the repository class
     * @return a new ManagedRepository instance
     */
    public static ManagedRepository builder(Class<?> repository) {
        var fqn = repository.getName();
        var managedRepository = new ManagedRepository(fqn);
        managedRepository.instance = ClassReflection.getInstance().instantiate(repository);
        return managedRepository;
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
