package br.com.schumaker.octopus.framework.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The PairTest class.
 * This class is responsible for testing the Pair class.
 *
 * @author Hudson Schumaker
 * @version 1.0.0
 */
class PairTest {

    @Test
    void testPairCreation() {
        // Arrange
        Pair<String, Integer> pair = new Pair<>("First", 2);

        // Assert
        assertEquals("First", pair.first());
        assertEquals(2, pair.second());
    }

    @Test
    void testPairEquality() {
        // Arrange
        Pair<String, Integer> pair1 = new Pair<>("First", 2);
        Pair<String, Integer> pair2 = new Pair<>("First", 2);

        // Assert
        assertEquals(pair1, pair2);
    }

    @Test
    void testPairInequality() {
        // Arrange
        Pair<String, Integer> pair1 = new Pair<>("First", 2);
        Pair<String, Integer> pair2 = new Pair<>("Second", 3);

        // Assert
        assertNotEquals(pair1, pair2);
    }

    @Test
    void testPairHashCode() {
        // Arrange
        Pair<String, Integer> pair1 = new Pair<>("First", 2);
        Pair<String, Integer> pair2 = new Pair<>("First", 2);

        // Assert
        assertEquals(pair1.hashCode(), pair2.hashCode());
    }
}