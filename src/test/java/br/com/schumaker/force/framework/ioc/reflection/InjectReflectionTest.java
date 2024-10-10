package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.ioc.annotations.bean.Inject;
import br.com.schumaker.force.framework.ioc.IoCContainer;
import br.com.schumaker.force.framework.ioc.managed.ManagedBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InjectReflectionTest {
    private InjectReflection injectReflection;

    @BeforeEach
    void setUp() {
        injectReflection = InjectReflection.getInstance();
    }

    @Test
    void testInjectFieldBean() {
        // Arrange
        TestClass testInstance = new TestClass();
        IoCContainer.getInstance().registerBean(ManagedBean.builder(TestDependency.class, new TestDependency()));

        // Act
        injectReflection.injectFieldBean(testInstance);

        // Assert
        assertNotNull(testInstance.getDependency());
    }

    static class TestClass {
        @Inject
        private TestDependency dependency;

        public TestDependency getDependency() {
            return dependency;
        }
    }

    static class TestDependency {}
}