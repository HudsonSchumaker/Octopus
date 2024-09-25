package br.com.schumaker.octopus.framework.reflection;

import br.com.schumaker.octopus.framework.annotations.bean.Value;
import br.com.schumaker.octopus.framework.ioc.Environment;
import br.com.schumaker.octopus.framework.exception.OctopusException;
import br.com.schumaker.octopus.framework.ioc.reflection.ValueReflection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.*;

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

        @Value("test.unsupported")
        private Object unsupportedField;

        private String noAnnotationField;

        public void testMethod(@Value("test.string") String param) {
            // Method for testing parameter injection
        }

        public void unsupportedTypeMethod(@Value("test.unsupported") Object param) {
            // Method for testing unsupported type
        }

        public void wrongType(@Value("test.invalid.type") int param) {
            // Method for testing wrong type
        }

        public void noAnnotationMethod(String param) {
            // Method for testing no annotation
        }
    }

    @Test
    public void testInjectFieldValueWithSupportedTypes() {
        // Arrange
        TestClass testInstance = new TestClass();

        // Act
        valueReflection.injectFieldValue(testInstance);

        // Assert
        assertEquals("testValue", testInstance.testString);
        assertEquals(123, testInstance.testInt);
    }

    @Test
    public void testInjectFieldValueWithUnsupportedType() {
        // Arrange
        TestClass testInstance = new TestClass();

        // Act
        valueReflection.injectFieldValue(testInstance);

        // Assert
        assertNull(testInstance.unsupportedField);
    }

    @Test
    public void testInjectFieldValueWithoutAnnotation() {
        // Arrange
        TestClass testInstance = new TestClass();

        // Act
        valueReflection.injectFieldValue(testInstance);

        // Assert
        assertNull(testInstance.noAnnotationField);
    }

    @Test
    public void testInjectParameterValueWithSupportedType() throws NoSuchMethodException {
        // Arrange
        Method method = TestClass.class.getMethod("testMethod", String.class);
        Parameter parameter = method.getParameters()[0];

        // Act
        Object injectedValue = valueReflection.injectParameterValue(parameter);

        // Assert
        assertEquals("testValue", injectedValue);
    }

    @Test
    public void testInjectParameterValueWithUnsupportedType() throws NoSuchMethodException {
        // Arrange
        Method method = TestClass.class.getMethod("unsupportedTypeMethod", Object.class);
        Parameter parameter = method.getParameters()[0];

        // Act
        Object injectedValue = valueReflection.injectParameterValue(parameter);

        // Assert
        assertNull(injectedValue);
    }

    @Test
    public void testInjectParameterValueWithoutAnnotation() throws NoSuchMethodException {
        // Arrange
        Method method = TestClass.class.getMethod("noAnnotationMethod", String.class);
        Parameter parameter = method.getParameters()[0];

        // Act
        Object injectedValue = valueReflection.injectParameterValue(parameter);

        // Assert
        assertNull(injectedValue);
    }

    @Test
    public void testInjectParameterValueThrowsException() throws NoSuchMethodException {
        // Arrange
        Method method = TestClass.class.getMethod("wrongType", int.class);
        Parameter parameter = method.getParameters()[0];
        environment.setKey("test.invalid.type", "invalid-Int");

        // Act & Assert
        assertThrows(OctopusException.class, () -> valueReflection.injectParameterValue(parameter));
    }
}
