package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.Bean;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedBean;

public class MethodReflection {
    private static final MethodReflection INSTANCE = new MethodReflection();

    private MethodReflection() {}

    public static MethodReflection getInstance() {
        return INSTANCE;
    }

    public void instantiateBean(Object instance) {
        try {
            var methods = instance.getClass().getDeclaredMethods();
            for (var method : methods) {
                if (method.isAnnotationPresent(Bean.class)) {
                    Object bean = method.invoke(instance);
                    var managedBean = ManagedBean.builder(bean.getClass(), bean);
                    IoCContainer.getInstance().registerBean(managedBean);
                }
            }
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }
}
