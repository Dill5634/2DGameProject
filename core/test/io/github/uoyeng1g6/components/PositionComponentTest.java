package io.github.uoyeng1g6.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionComponentTest {


    @Test
    void testCoordinates(){
        //check creation
        PositionComponent p = new PositionComponent(5f, 10f);
        assertNotNull(p);
        assertEquals(5f, p.x);
        assertEquals(10f, p.y);

        //update values
        p.x = 15f;
        p.y = 20f;

        assertEquals(15f, p.x);
        assertEquals(20f, p.y);
    }
}