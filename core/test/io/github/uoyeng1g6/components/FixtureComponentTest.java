package io.github.uoyeng1g6.components;

import com.badlogic.gdx.physics.box2d.Fixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class FixtureComponentTest {

    @Test
    void testFixture(){
        //create a new fixture component and check that the fixture component was assigned to it
        Fixture f = mock(Fixture.class);
        FixtureComponent fc = new FixtureComponent(f);
        assertEquals(f, fc.fixture);
    }

}