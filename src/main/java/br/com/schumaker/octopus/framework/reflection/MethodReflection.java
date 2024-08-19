package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.bean.Bean;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;
import br.com.schumaker.octopus.framework.ioc.managed.ManagedBean;

/**
 * The MethodReflection class provides utility methods for handling methods annotated with @Bean.
 * It uses reflection to instantiate beans and register them with the IoC container.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class MethodReflection {
    private static final MethodReflection INSTANCE = new MethodReflection();

    private MethodReflection() {}

    public static MethodReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Instantiates beans for methods annotated with @Bean in the specified instance and registers them with the IoC container.
     *
     * @param instance the instance whose methods are to be processed
     * @throws OctopusException if an error occurs during bean instantiation
     */
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
