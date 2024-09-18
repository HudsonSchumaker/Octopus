package br.com.schumaker.octopus.framework.model;

import br.com.schumaker.octopus.framework.exception.OctopusException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * The PatchHelper class.
 * This class provides a helper method to apply a patch to an entity.
 * It uses reflection to update the fields of the entity based on the patch message.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class PatchHelper {

    /**
     * Applies the patch to the given entity.
     *
     * @param entity the entity to be patched.
     * @param patchMessage the patch message containing the fields to be updated.
     * @param <T> the type of the entity.
     */
    public static <T> void applyPatch(T entity, Map<String, Object> patchMessage) {
        Class<?> entityClass = entity.getClass();

        patchMessage.forEach((key, value) -> {
            try {
                Field field = entityClass.getDeclaredField(key);
                field.setAccessible(true);
                field.set(entity, value);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new OctopusException("Error applying patch to entity.", ex);
            }
        });
    }
}
