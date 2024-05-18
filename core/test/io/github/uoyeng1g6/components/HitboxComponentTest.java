package io.github.uoyeng1g6.components;

import com.badlogic.gdx.math.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HitboxComponentTest {

    @Test
    void testHitbox(){
        HitboxComponent h = new HitboxComponent(new Rectangle(5,5,5,5));
        assertNotNull(h);
        assertEquals(new Rectangle(5,5,5,5), h.rects[0]);
        assertEquals(new Rectangle(5,5,5,5), h.region);
    }


}