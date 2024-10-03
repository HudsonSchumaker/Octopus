package br.com.schumaker.force.framework.run;

import java.util.HashMap;
import java.util.Map;

/**
 * The CommandLineArgs class provides utility methods for parsing and retrieving command-line arguments.
 * It stores the arguments in a map for easy access and retrieval.
 * This class is a singleton and provides a global point of access to its instance.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public final class CommandLineArgs {
    public static final String ENV = "-env";
    private static final CommandLineArgs INSTANCE = new CommandLineArgs();
    private final Map<String, String> argsMap = new HashMap<>();

    private CommandLineArgs() {}

    public static CommandLineArgs getInstance() {
        return INSTANCE;
    }

    /**
     * Parses and stores the command-line arguments in a map.
     * Arguments should be in the format "-key=value".
     *
     * @param args the command-line arguments to parse.
     */
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

    /**
     * Retrieves the value of the specified command-line argument.
     *
     * @param key the key of the argument to retrieve.
     * @return the value of the specified argument, or null if not found.
     */
    public String getArg(String key) {
        return argsMap.get(key);
    }
}
