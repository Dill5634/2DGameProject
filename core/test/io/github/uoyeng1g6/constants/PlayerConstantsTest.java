package io.github.uoyeng1g6.constants;

import com.badlogic.gdx.math.Vector2;
import io.github.uoyeng1g6.GdxTestRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
class PlayerConstantsTest {
    @Test
    void startLocation(){
        assertEquals(PlayerConstants.START_POSITION, new Vector2(9, 82));
    }

}