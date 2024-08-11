package br.com.schumaker.octopus.framework.ioc;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;

public class ManagedService implements ManagedClass {
    private final String fqn;
    private Object instance;

    private ManagedService(String fqn) {
        this.fqn = fqn;
    }

    public static ManagedService builder(Class<?> service) {
        var fqn = service.getName();
        var managedService = new ManagedService(fqn);
        managedService.instance = ClassReflection.getInstance().getInstance(service);
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
