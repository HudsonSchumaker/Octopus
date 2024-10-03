package br.com.schumaker.force.framework.annotations;

import br.com.schumaker.force.framework.annotations.bean.Component;
import br.com.schumaker.force.framework.annotations.bean.Configuration;
import br.com.schumaker.force.framework.annotations.bean.Service;
import br.com.schumaker.force.framework.annotations.controller.Controller;
import br.com.schumaker.force.framework.annotations.db.Repository;
import br.com.schumaker.force.framework.annotations.db.Table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The @ForceApp annotation is used to mark a class as the entry point of a Force application.
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
 * @ForceApp(root = "com.example.myapp")
 * public class MyApp {
 *
 *     public static void main(String[] args) {
 *         Force.run(Main.class, args);
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
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForceApp {
    String root() default "com";
}
