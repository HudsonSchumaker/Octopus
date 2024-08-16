package br.com.schumaker.octopus.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Component annotation is used to mark a class as a component.
 * This annotation can be applied to classes to indicate that the class
 * is a candidate for auto-detection and dependency injection by the container.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Component
 * public class MyComponent {
 *
 *     @Value("some.value.from.file")
 *     private String myField;
 *
 *     @Inject
 *     private MyDependency myDependency;
 *
 *     public void doSomething() {
 *         // Component logic
 *     }
 * }
 * }
 * </pre>
 *
 * @see Value
 * @see Inject
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {}
