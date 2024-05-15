package io.github.uoyeng1g6.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PhysicsPolygonTest {

    PhysicsPolygon p_empty;
    PhysicsPolygon p;
    Vector2[] vertices = new Vector2[2];

    @BeforeEach
    void setUp(){
        vertices[0] = new Vector2(1,1);
        vertices[1] = new Vector2(2,2);
        p_empty = new PhysicsPolygon();
        p = new PhysicsPolygon("test", mock(BodyDef.BodyType.class), new Vector2(0,0), vertices);
    }

    @Test
    void getName() {
        assertEquals("test", p.getName());
    }

    @Test
    void getType() {
        assertNotNull(p.getType());
    }

    @Test
    void getPosition() {
        assertEquals(new Vector2(0,0), p.getPosition());
    }

    @Test
    void getVertices() {
        assertEquals(vertices, p.getVertices());
    }
}