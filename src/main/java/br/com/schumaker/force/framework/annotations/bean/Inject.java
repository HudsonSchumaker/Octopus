package br.com.schumaker.force.framework.annotations.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Inject annotation is used to mark a field or constructor for dependency injection.
 * This annotation ask the container to inject a compatible bean if found.
 * <p>
 * The @Inject annotation injects a bean into the annotated field or constructor.
 * All the parameters of the constructor have to be a Bean to be proper injected by the container.
 * </p>
 *
 * <p>
 * Example usage on a field:
 * </p>
 *
 * <pre>
 * {@code
 * public class MyClass {
 *     @Inject
 *     private MyDependency myDependency;
 * }
 * }
 * </pre>
 *
 * <p>
 * Example usage on a constructor:
 * </p>
 *
 * <pre>
 * {@code
 * public class MyClass {
 *     private final MyDependency myDependency;
 *
 *     @Inject
 *     public MyClass(MyDependency myDependency) {
 *         this.myDependency = myDependency;
 *     }
 * }
 * }
 * </pre>
 *
 * @see Bean
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {}
