package io.github.uoyeng1g6.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerComponentTest {

    @Test
    void testPlayerComponent(){
        PlayerComponent p = new PlayerComponent();
        assertNotNull(p);
        assertFalse(p.isInteracting);
    }
}