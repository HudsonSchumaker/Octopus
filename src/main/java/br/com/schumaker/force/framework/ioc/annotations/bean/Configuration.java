package br.com.schumaker.force.framework.ioc.annotations.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @Configuration annotation is used to mark a class as a source of bean definitions.
 * This annotation can be applied to classes to indicate that the class contains
 * methods annotated with @Bean that produce beans to be managed by the container.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @Configuration
 * public class AppConfig {
 *
 *     @Bean
 *     public MyBean myBean() {
 *         return new MyBean();
 *     }
 * }
 * }
 * </pre>
 *
 * @see Bean
 * @see Inject
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {}
