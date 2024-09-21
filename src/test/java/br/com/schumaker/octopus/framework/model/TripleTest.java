package br.com.schumaker.octopus.framework.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The TripleTest class.
 * This class is responsible for testing the Triple class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
public class TripleTest {

    @Test
    void testTripleCreation() {
        // Arrange
        Triple<String, Integer, Boolean> triple = new Triple<>("First", 2, true);

        // Assert
        assertEquals("First", triple.first());
        assertEquals(2, triple.second());
        assertEquals(true, triple.third());
    }

    @Test
    void testTripleEquality() {
        // Arrange
        Triple<String, Integer, Boolean> triple1 = new Triple<>("First", 2, true);
        Triple<String, Integer, Boolean> triple2 = new Triple<>("First", 2, true);

        // Assert
        assertEquals(triple1, triple2);
    }

    @Test
    void testTripleInequality() {
        // Arrange
        Triple<String, Integer, Boolean> triple1 = new Triple<>("First", 2, true);
        Triple<String, Integer, Boolean> triple2 = new Triple<>("Second", 3, false);

        // Assert
        assertNotEquals(triple1, triple2);
    }

    @Test
    void testTripleHashCode() {
        // Arrange
        Triple<String, Integer, Boolean> triple1 = new Triple<>("First", 2, true);
        Triple<String, Integer, Boolean> triple2 = new Triple<>("First", 2, true);

        // Assert
        assertEquals(triple1.hashCode(), triple2.hashCode());
    }
}