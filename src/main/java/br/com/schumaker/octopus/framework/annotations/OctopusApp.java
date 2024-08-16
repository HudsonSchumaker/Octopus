package br.com.schumaker.octopus.framework.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @OctopusApp annotation is used to mark a class as the entry point of an Octopus application.
 * This annotation can be applied to classes to indicate that the class serves as the main
 * configuration and bootstrap class for the application.
 *
 * <p>
 * The root attribute specifies the root package or directory for the application. If no value is provided,
 * the default is com package.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * @OctopusApp(root = "com.example.myapp")
 * public class MyApp {
 *
 *     public static void main(String[] args) {
 *         Octopus.run(Main.class, args);
 *     }
 * }
 * }
 * </pre>
 *
 * @see Configuration
 * @see Component
 * @see Repository
 * @see Table
 * @see Service
 * @see Controller
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OctopusApp {
    String root() default "com";
}
