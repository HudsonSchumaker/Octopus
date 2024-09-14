package br.com.schumaker.octopus.framework.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void testPairCreation() {
        Pair<String, Integer> pair = new Pair<>("First", 2);

        assertEquals("First", pair.first());
        assertEquals(2, pair.second());
    }

    @Test
    void testPairEquality() {
        Pair<String, Integer> pair1 = new Pair<>("First", 2);
        Pair<String, Integer> pair2 = new Pair<>("First", 2);

        assertEquals(pair1, pair2);
    }

    @Test
    void testPairInequality() {
        Pair<String, Integer> pair1 = new Pair<>("First", 2);
        Pair<String, Integer> pair2 = new Pair<>("Second", 3);

        assertNotEquals(pair1, pair2);
    }

    @Test
    void testPairHashCode() {
        Pair<String, Integer> pair1 = new Pair<>("First", 2);
        Pair<String, Integer> pair2 = new Pair<>("First", 2);

        assertEquals(pair1.hashCode(), pair2.hashCode());
    }
}