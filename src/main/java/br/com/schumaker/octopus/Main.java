package br.com.schumaker.octopus;

import br.com.schumaker.octopus.annotations.OctopusApp;
import br.com.schumaker.octopus.run.Octopus;

@OctopusApp(root = "br.com.schumaker.octopus.app")
public class Main {
    public static void main(String[] args) throws Exception {
        Octopus.run(Main.class, args);
    }
}
