package br.com.schumaker.force.framework.ioc.annotations;

import br.com.schumaker.force.framework.ioc.annotations.bean.Component;
import br.com.schumaker.force.framework.ioc.annotations.bean.Configuration;
import br.com.schumaker.force.framework.ioc.annotations.bean.Service;
import br.com.schumaker.force.framework.ioc.annotations.controller.Controller;
import br.com.schumaker.force.framework.ioc.annotations.db.Repository;
import br.com.schumaker.force.framework.ioc.annotations.db.Table;

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
 * @see Controller
 * @see Service
 * @see Repository
 * @see Configuration
 * @see Component
 * @see Table
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForceApp {
    String root() default "com";
}
