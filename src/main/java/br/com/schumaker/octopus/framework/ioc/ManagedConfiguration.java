package br.com.schumaker.octopus.framework.ioc;

import br.com.schumaker.octopus.framework.reflection.ClassReflection;
import br.com.schumaker.octopus.framework.reflection.MethodReflection;

public class ManagedConfiguration implements ManagedClass<ManagedConfiguration> {
    private final String fqn;
    private Object instance;

    private ManagedConfiguration(String fqn) {
        this.fqn = fqn;
    }

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
