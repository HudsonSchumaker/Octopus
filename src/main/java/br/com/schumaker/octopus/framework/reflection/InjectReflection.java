package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.Inject;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.IoCContainer;

public class InjectReflection {
    private static final InjectReflection INSTANCE = new InjectReflection();

    private InjectReflection() {}

    public static InjectReflection getInstance() {
        return INSTANCE;
    }

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
            throw new OctopusException(e.getMessage());
        }
    }
}
