package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
class AppTest {
    @Test
    @DisplayName("First Test")
    void theAnswer() {
        assertEquals(42, 22 + 20, "ciao");
    }
}
