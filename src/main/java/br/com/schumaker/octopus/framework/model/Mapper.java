package br.com.schumaker.octopus.framework.model;

import br.com.schumaker.octopus.framework.exception.OctopusException;

import java.lang.reflect.Field;

/**
 * The Mapper class is a utility for mapping fields from a source object to a target object.
 * This class uses reflection to copy fields with matching names and types from the source
 * object to the target object.
 *
 * @param <S> the type of the source object
 * @param <T> the type of the target object
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class Source {
 *     private String name;
 *     private int age;
 *
 *     // Getters and setters
 * }
 *
 * public class Target {
 *     private String name;
 *     private int age;
 *
 *     // Getters and setters
 * }
 *
 * public class Example {
 *     public static void main(String[] args) {
 *         Source source = new Source();
 *         source.setName("John");
 *         source.setAge(30);
 *
 *         Mapper<Source, Target> mapper = new Mapper<>();
 *         Target target = mapper.map(source, Target.class);
 *
 *         System.out.println(target.getName()); // Output: John
 *         System.out.println(target.getAge());  // Output: 30
 *     }
 * }
 * }
 * </pre>
 *
 * @see OctopusException
 */
public class Mapper <S, T> {

    /**
     * Maps fields from the source object to the target object.
     *
     * @param source the source object
     * @param targetClass the class of the target object
     * @return the target object with fields mapped from the source object
     * @throws OctopusException if an error occurs during mapping
     */
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
