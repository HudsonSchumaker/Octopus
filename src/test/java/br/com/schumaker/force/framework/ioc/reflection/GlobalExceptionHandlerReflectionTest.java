package br.com.schumaker.force.framework.ioc.reflection;

import br.com.schumaker.force.framework.ioc.annotations.exception.ExceptionHandler;
import br.com.schumaker.force.framework.model.Pair;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerReflectionTest {

    @Test
    void testGetMethods() throws NoSuchMethodException {
        // Act
        Map<Class<?>, Pair<Method, List<Parameter>>> methods = GlobalExceptionHandlerReflection.getMethods(TestClass.class);
        Pair<Method, List<Parameter>> pair = methods.get(RuntimeException.class);

        // Assert
        assertNotNull(methods);
        assertTrue(methods.containsKey(RuntimeException.class));
        assertNotNull(pair);
        assertEquals("handleRuntimeException", pair.first().getName());
        assertEquals(1, pair.second().size());
        assertEquals(String.class, pair.second().getFirst().getType());
    }

    static class TestClass {
        @ExceptionHandler(RuntimeException.class)
        public void handleRuntimeException(String message) {
            // Handle exception
        }
    }
}