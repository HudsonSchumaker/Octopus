package br.com.schumaker.force.framework.annotations.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static br.com.schumaker.force.framework.ioc.AppProperties.DEFAULT_VALUE_NAME;

/**
 * The @Value annotation is used to inject a value into a field or parameter.
 * This annotation can be applied to fields and parameters to indicate that the
 * annotated element should have a specific value injected by the container.
 *
 * <p>
 * The value to be injected is specified as a string in the annotation's value attribute, that is an attribute on
 * the application.properties file.
 * </p>
 *
 * <p>
 * Example usage on a field:
 * </p>
 *
 * <pre>
 * {@code
 * public class MyClass {
 *     @Value("some.value.from.file")
 *     private String myField;
 * }
 * }
 * </pre>
 *
 * <p>
 * Example usage on a parameter:
 * </p>
 *
 * <pre>
 * {@code
 * public class MyClass {
 *     private final String myField;
 *
 *     public MyClass(@Value("some.value.from.file") String myField) {
 *         this.myField = myField;
 *     }
 * }
 * }
 * </pre>
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String value() default DEFAULT_VALUE_NAME;
}
