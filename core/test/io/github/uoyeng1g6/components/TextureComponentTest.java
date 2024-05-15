package io.github.uoyeng1g6.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TextureComponentTest {

    TextureComponent t;
    TextureRegion x;

    @BeforeEach
    void setUp(){
        x = mock(TextureRegion.class);
        t = new TextureComponent(x, 1f);
    }

    @Test
    void testConstructor(){
        assertEquals(x, t.region);
        assertEquals(1f, t.scale);
    }

    @Test
    void show() {
        assertEquals(t, t.show());
        assertTrue(t.visible);
    }

    @Test
    void hide() {
        assertEquals(t, t.hide());
        assertFalse(t.visible);
    }
}