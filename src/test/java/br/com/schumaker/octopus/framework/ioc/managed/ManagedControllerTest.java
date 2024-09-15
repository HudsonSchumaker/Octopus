package br.com.schumaker.octopus.framework.ioc.managed;

import br.com.schumaker.octopus.framework.annotations.controller.Controller;
import br.com.schumaker.octopus.framework.annotations.controller.Get;
import br.com.schumaker.octopus.framework.model.Triple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ManagedControllerTest {
    private ManagedController managedController;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        managedController = ManagedController.builder(MyController.class);
    }

    @Test
    void testGetMethod_Found() {
        Triple<String, Method, List<Parameter>> result = managedController.getMethod("/users/123", "GET");
        assertNotNull(result);
        assertEquals("/users/{id}", result.first());
    }

    @Test
    void testGetMethod_NotFound() {
        assertThrows(RuntimeException.class, () -> {
            managedController.getMethod("/invalidRoute", "GET");
        });
    }

    @Test
    void testPathMatches() {
        assertTrue(managedController.pathMatches("/users/{id}", "/users/123"));
        assertFalse(managedController.pathMatches("/users/{id}", "/users"));
    }

    @Test
    void testExtractPathVariables() {
        List<Object> pathVariables = managedController.extractPathVariables("/users/{id}", "/users/123");
        assertEquals(1, pathVariables.size());
        assertEquals("123", pathVariables.getFirst());
    }

    // Test controller class
    @Controller
    public static class MyController {
        @Get("/users/{id}")
        public String getUserById(String id) {
            return "User: " + id;
        }
    }
}
