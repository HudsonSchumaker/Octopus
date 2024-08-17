package br.com.schumaker.octopus;

import br.com.schumaker.octopus.framework.annotations.OctopusApp;
import br.com.schumaker.octopus.framework.run.Octopus;

/**
 * The Main class is the entry point of the application.
 * It is responsible for starting the Octopus framework.
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 * {@code
 * public class Main {
 *     public static void main(String[] args) throws Exception {
 *         Octopus.run(Main.class, args);
 *     }
 * }
 * }
 * </pre>
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
@OctopusApp(root = "br.com.schumaker.octopus.app")
public class Main {
    public static void main(String[] args) throws Exception {
        Octopus.run(Main.class, args);
    }
}
