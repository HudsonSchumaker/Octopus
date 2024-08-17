package br.com.schumaker.octopus.framework.run;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommandLineArgsTest {
    private CommandLineArgs commandLineArgs;

    @BeforeEach
    public void setUp() {
        commandLineArgs = CommandLineArgs.getInstance();
        commandLineArgs.setArgs(new String[]{}); // Clear previous args
    }

    @Test
    public void testSetAndGetSingleArg() {
        // Arrange
        String[] args = {"-env=local"};
        commandLineArgs.setArgs(args);

        // Act & Assert
        assertEquals("local", commandLineArgs.getArg("-env"));
    }

    @Test
    public void testSetAndGetMultipleArgs() {
        // Arrange
        String[] args = {"-env=local", "-port=8080"};
        commandLineArgs.setArgs(args);

        // Act & Assert
        assertEquals("local", commandLineArgs.getArg("-env"));
        assertEquals("8080", commandLineArgs.getArg("-port"));
    }

    @Test
    public void testGetArgNotExist() {
        // Arrange
        String[] args = {"-env=local"};
        commandLineArgs.setArgs(args);

        // Act & Assert
        assertNull(commandLineArgs.getArg("-port"));
    }
}