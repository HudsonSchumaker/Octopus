package br.com.schumaker.octopus.framework.run;

import java.util.HashMap;
import java.util.Map;

public class CommandLineArgs {
    private static final CommandLineArgs INSTANCE = new CommandLineArgs();
    private final Map<String, String> argsMap = new HashMap<>();

    private CommandLineArgs() {}

    public static CommandLineArgs getInstance() {
        return INSTANCE;
    }

    public void setArgs(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("-")) {
                String[] parts = arg.split("=", 2);
                if (parts.length == 2) {
                    argsMap.put(parts[0], parts[1]);
                }
            }
        }
    }

    public String getArg(String key) {
        return argsMap.get(key);
    }
}
