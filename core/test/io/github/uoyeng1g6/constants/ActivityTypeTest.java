package io.github.uoyeng1g6.constants;

import io.github.uoyeng1g6.GdxTestRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
class ActivityTypeTest {

    @Test
    void values() {
        assertEquals("[SPOONS, PIAZZA, GLASSHOUSE, BREAKFAST, STUDY, LIBRARY, CSBUILDING, RECREATION, DUCKS, WALK]",
                Arrays.toString(ActivityType.values()));
    }

    @Test
    void valueOf() {
        assertTrue(ActivityType.EAT.contains(ActivityType.SPOONS));
        assertTrue(ActivityType.EAT.contains(ActivityType.PIAZZA));
        assertTrue(ActivityType.EAT.contains(ActivityType.GLASSHOUSE));

        assertTrue(ActivityType.PLAY.contains(ActivityType.RECREATION));
        assertTrue(ActivityType.PLAY.contains(ActivityType.DUCKS));
        assertTrue(ActivityType.PLAY.contains(ActivityType.WALK));

        assertTrue(ActivityType.WORK.contains(ActivityType.STUDY));
        assertTrue(ActivityType.WORK.contains(ActivityType.LIBRARY));
        assertTrue(ActivityType.WORK.contains(ActivityType.CSBUILDING));
    }
}