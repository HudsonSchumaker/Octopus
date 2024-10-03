package br.com.schumaker.force;

import br.com.schumaker.force.framework.annotations.ForceApp;
import br.com.schumaker.force.framework.run.Force;

/**
 * The Main class is the entry point of the application.
 * It is responsible for starting the Force framework.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class Main {
 *     public static void main(String[] args) throws Exception {
 *         Force.run(Main.class, args);
 *     }
 * }
 * }
 * </pre>
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@ForceApp(root = "br.com.schumaker.force.app")
public class Main {
    public static void main(String[] args) throws Exception {
        Force.run(Main.class, args);
    }
}
