package io.github.uoyeng1g6.constants;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerConstantsTest {
    @Test
    void startLocation(){
        assertEquals(PlayerConstants.START_POSITION, new Vector2(9, 82));
    }

}