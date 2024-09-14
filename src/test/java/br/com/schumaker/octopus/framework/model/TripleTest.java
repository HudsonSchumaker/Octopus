package br.com.schumaker.octopus.framework.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TripleTest {

    @Test
    void testTripleCreation() {
        Triple<String, Integer, Boolean> triple = new Triple<>("First", 2, true);

        assertEquals("First", triple.first());
        assertEquals(2, triple.second());
        assertEquals(true, triple.third());
    }

    @Test
    void testTripleEquality() {
        Triple<String, Integer, Boolean> triple1 = new Triple<>("First", 2, true);
        Triple<String, Integer, Boolean> triple2 = new Triple<>("First", 2, true);

        assertEquals(triple1, triple2);
    }

    @Test
    void testTripleInequality() {
        Triple<String, Integer, Boolean> triple1 = new Triple<>("First", 2, true);
        Triple<String, Integer, Boolean> triple2 = new Triple<>("Second", 3, false);

        assertNotEquals(triple1, triple2);
    }

    @Test
    void testTripleHashCode() {
        Triple<String, Integer, Boolean> triple1 = new Triple<>("First", 2, true);
        Triple<String, Integer, Boolean> triple2 = new Triple<>("First", 2, true);

        assertEquals(triple1.hashCode(), triple2.hashCode());
    }
}