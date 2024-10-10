package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.managed.ManagedBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class ConstructorReflectionTest {
    private ConstructorReflection constructorReflection;

    @BeforeEach
    void setUp() {
        constructorReflection = ConstructorReflection.getInstance();
    }

    @Test
    void testInstantiateWithInjectedBeans() throws Exception {
        // Arrange
        IoCContainer.getInstance().registerBean(ManagedBean.builder(Dependency.class, new Dependency()));
        Constructor<TestClass> constructor = TestClass.class.getDeclaredConstructor(Dependency.class);

        // Act
        TestClass instance = (TestClass) constructorReflection.instantiateWithInjectedBeans(constructor);

        // Assert
        assertNotNull(instance);
        assertNotNull(instance.dependency());
    }

    @Test
    void testInstantiateWithInjectedBeansWrongConstructor() throws Exception {
        // Arrange
        Constructor<TestClass> constructor = null;

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> {
            constructorReflection.instantiateWithInjectedBeans(constructor);
        });

        // Assert
        assertEquals(500, exception.getStatusCode());
    }

    @Test
    void testInstantiateWithInjectedBeansMissingBean() throws Exception {
        // Arrange
        Constructor<TestClass> constructor = TestClass.class.getDeclaredConstructor(Dependency.class);

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> {
            constructorReflection.instantiateWithInjectedBeans(constructor);
        });

        // Assert
        assertTrue(exception.getMessage().contains("Bean(s) missing for injection in constructor parameters"));
    }

    record TestClass(Dependency dependency) {}
    static class Dependency {}
}
