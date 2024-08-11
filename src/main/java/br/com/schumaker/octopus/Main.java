package br.com.schumaker.octopus;

import br.com.schumaker.octopus.framework.annotations.OctopusApp;
import br.com.schumaker.octopus.framework.run.Octopus;

@OctopusApp(root = "br.com.schumaker.octopus.app")
public class Main {
    public static void main(String[] args) throws Exception {
        Octopus.run(Main.class, args);
    }
}
