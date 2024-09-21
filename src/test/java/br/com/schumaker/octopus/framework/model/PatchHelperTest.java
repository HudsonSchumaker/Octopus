package br.com.schumaker.octopus.framework.model;

import br.com.schumaker.octopus.framework.exception.OctopusException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The PatchHelperTest class.
 * This class is responsible for testing the PatchHelper class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
class PatchHelperTest {

    @Test
    void testApplyPatch() {
        // Arrange
        TestEntity entity = new TestEntity(1L, "Old Name");
        Map<String, Object> patchMessage = new HashMap<>();
        patchMessage.put("name", "New Name");

        // Act
        PatchHelper.applyPatch(entity, patchMessage);

        // Assert
        assertEquals("New Name", entity.getName());
    }

    @Test
    void testApplyPatchWithInvalidField() {
        // Arrange
        TestEntity entity = new TestEntity(1L, "Old Name");
        Map<String, Object> patchMessage = new HashMap<>();
        patchMessage.put("invalidField", "New Value");

        // Act & Assert
        assertThrows(OctopusException.class, () -> PatchHelper.applyPatch(entity, patchMessage));
    }

    // Test entity class
    static class TestEntity {
        private Long id;
        private String name;

        public TestEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}