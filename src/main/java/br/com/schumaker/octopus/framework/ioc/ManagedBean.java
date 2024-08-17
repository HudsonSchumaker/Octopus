package br.com.schumaker.octopus.framework.ioc;

public class ManagedBean implements ManagedClass<ManagedBean> {
    private final String fqn;
    private final Object instance;

    private ManagedBean(String fqn, Object instance) {
        this.fqn = fqn;
        this.instance = instance;
    }

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
