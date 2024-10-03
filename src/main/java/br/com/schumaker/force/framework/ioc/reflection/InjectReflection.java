package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.ioc.annotations.bean.Inject;
import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;

/**
 * The InjectReflection class provides utility methods for injecting dependencies into fields annotated with @Inject.
 * It uses reflection to set the field values of an instance with the corresponding beans from the IoC container.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class InjectReflection {
    private static final InjectReflection INSTANCE = new InjectReflection();

    private InjectReflection() {}

    public static InjectReflection getInstance() {
        return INSTANCE;
    }

    /**
     * Injects the field values of the specified instance with the corresponding beans from the IoC container.
     * Fields annotated with @Inject are set with the appropriate bean instances.
     *
     * @param instance the instance whose fields are to be injected.
     * @throws ForceException if an error occurs during field injection.
     */
    public void injectFieldBean(Object instance) {
        try {
            var fields = instance.getClass().getDeclaredFields();
            for (var field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    field.setAccessible(true);
                    var type = field.getType();
                    var value = IoCContainer.getInstance().getBean(type.getName());
                    if (value != null && value.getInstance() != null) {
                        field.set(instance, value.getInstance());
                    }
                }
            }
        } catch (Exception e) {
            throw new ForceException(e.getMessage());
        }
    }
}
