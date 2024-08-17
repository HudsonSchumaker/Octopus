package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.Value;
import br.com.schumaker.octopus.framework.ioc.Environment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValueReflectionTest {
    private ValueReflection valueReflection;
    private Environment environment;

    @BeforeEach
    public void setUp() {
        valueReflection = ValueReflection.getInstance();
        environment = Environment.getInstance();
        environment.setKey("test.string", "testValue");
        environment.setKey("test.int", "123");
    }

    static class TestClass {
        @Value("test.string")
        private String testString;

        @Value("test.int")
        private int testInt;
    }

    @Test
    public void testInjectFieldValue() {
        // Arrange
        TestClass testInstance = new TestClass();
        valueReflection.injectFieldValue(testInstance);

        // Act & Assert
        assertEquals("testValue", testInstance.testString);
        assertEquals(123, testInstance.testInt);
    }

    @Test
    public void testInjectParameterValue() {
        // Arrange
        TestClass testInstance = new TestClass();
        environment.setKey("test.string", "testValue");

        // Act
        Object instance = valueReflection.injectFieldValue(testInstance);

        // Assert
        assertEquals("testValue", ((TestClass)instance).testString);
    }
}
