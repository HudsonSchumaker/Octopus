package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;

public class ManagedComponent implements ManagedClass<ManagedComponent> {
    private final String fqn;
    private Object instance;

    private ManagedComponent(String fqn) {
        this.fqn = fqn;
    }

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
