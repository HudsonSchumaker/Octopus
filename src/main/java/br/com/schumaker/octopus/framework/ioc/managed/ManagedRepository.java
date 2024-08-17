package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;

public class ManagedRepository implements ManagedClass<ManagedRepository> {
    private final String fqn;
    private Object instance;

    private ManagedRepository(String fqn) {
        this.fqn = fqn;
    }

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
