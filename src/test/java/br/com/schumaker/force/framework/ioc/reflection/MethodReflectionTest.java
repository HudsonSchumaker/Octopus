package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.exception.ForceException;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.annotations.bean.Bean;
import br.com.schumaker.force.framework.ioc.managed.ManagedBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MethodReflectionTest {

    private MethodReflection methodReflection;

    @BeforeEach
    public void setUp() {
        methodReflection = MethodReflection.getInstance();
    }

    @Test
    public void testInstantiateBean() {
        // Arrange
        TestClass testClass = new TestClass();
        methodReflection.instantiateBean(testClass);
        IoCContainer.getInstance().registerBean(ManagedBean.builder(TestBean.class, new TestBean()));

        // Act
        ManagedBean managedBean = IoCContainer.getInstance().getBean(String.valueOf(TestBean.class).replace("class ", ""));

        // Assert
        assertNotNull(managedBean);
        assertInstanceOf(TestBean.class, managedBean.getInstance());
    }

    @Test
    public void testInstantiateBeanWithException() {
        // Arrange
        TestClassWithException testClass = new TestClassWithException();

        // Act
        ForceException exception = assertThrows(ForceException.class, () -> methodReflection.instantiateBean(testClass));

        // Assert
        assertEquals(500, exception.getStatusCode());
    }

    static class TestClass {
        @Bean
        public TestBean testBean() {
            return new TestBean();
        }
    }

    static class TestClassWithException {
        @Bean
        public TestBean testBean() {
            throw new RuntimeException("Error instantiating bean");
        }
    }

    static class TestBean {}
}