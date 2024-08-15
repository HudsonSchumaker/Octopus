package br.com.schumaker.octopus.framework.model;

import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.lang.reflect.Field;

public class Mapper <S, T> {

    public T map(S source, Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] targetFields = targetClass.getDeclaredFields();

            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                for (Field targetField : targetFields) {
                    if (sourceField.getName().equals(targetField.getName()) && sourceField.getType().equals(targetField.getType())) {
                        targetField.setAccessible(true);
                        targetField.set(target, sourceField.get(source));
                        targetField.setAccessible(false);
                    }
                }
                sourceField.setAccessible(false);
            }
            return target;
        } catch (Exception e) {
            throw new OctopusException(e.getMessage());
        }
    }
}
