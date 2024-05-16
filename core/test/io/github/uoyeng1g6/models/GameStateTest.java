package io.github.uoyeng1g6.models;

import com.badlogic.gdx.math.Vector2;
import io.github.uoyeng1g6.GdxTestRunner;
import io.github.uoyeng1g6.constants.ActivityType;
import io.github.uoyeng1g6.constants.GameConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(GdxTestRunner.class)
class GameStateTest {

    GameState state;
    @BeforeEach
    void setUp(){
        state = new GameState();
    }

    @Test
    void advanceDay() {
        //manually decrease energy for this test day
        state.energyRemaining = 5;
        //day count needs to be updated from 7 to 6
        state.advanceDay(new Vector2());
        assertEquals(state.daysRemaining, 6);

        //check new days are added
        assertFalse(state.days.isEmpty());

        //check resets energy to maximum
        assertEquals(state.energyRemaining, GameConstants.MAX_ENERGY);

        //check resets time to 16 hours left
        assertEquals(state.hoursRemaining, 16);
    }

    @Test
    void doActivity() {
        state.hoursRemaining = 8;
        state.energyRemaining = 10;

        //perform activity with sufficient energy and time
        assertTrue(state.doActivity(1,1, ActivityType.WALK,"test", new Vector2(0,0)));
        //System.out.println(state.currentDay.activityStats);
        //check counter updated
        assertEquals(state.currentDay.activityStats.get(ActivityType.WALK), 1);
        //check hours updated
        assertEquals(state.hoursRemaining, 7);
        //check energy updated
        assertEquals(state.energyRemaining, 9);

        //attempt to perform activity without sufficient energy
        assertFalse(state.doActivity(50,0, ActivityType.GLASSHOUSE, "test", new Vector2(0,0)));
        assertNull(state.currentDay.activityStats.get(ActivityType.GLASSHOUSE));
        //check hours remain the same
        assertEquals(state.hoursRemaining, 7);
        //check energy remains the same
        assertEquals(state.energyRemaining, 9);

        //attempt to perform activity without sufficient time
        assertFalse(state.doActivity(0,50, ActivityType.STUDY, "test", new Vector2(0,0)));
        assertNull(state.currentDay.activityStats.get(ActivityType.STUDY));
        //check hours remain the same
        assertEquals(state.hoursRemaining, 7);
        //check energy remains the same
        assertEquals(state.energyRemaining, 9);
    }

    @Test
    void testGetTotalActivityCount() {
        //check counter works correctly
        state.doActivity(1,1, ActivityType.SPOONS,"test", new Vector2(0,0));
        state.doActivity(1,1, ActivityType.LIBRARY,"test", new Vector2(0,0));
        assertEquals(state.getTotalActivityCount(ActivityType.SPOONS),1);
        assertEquals(state.getTotalActivityCount(ActivityType.LIBRARY),1);

        assertEquals(state.getTotalActivityCount(ActivityType.EAT), 1);
    }

    @Test
    void testStreaks() {
        for (int i = 0; i < 7; i++){
            state.doActivity(0,50, ActivityType.DUCKS, "test", new Vector2(0,0));
            state.advanceDay(new Vector2(0,0));
        }
        assertEquals(true, state.streaks[0]);
        assertEquals(false, state.streaks[1]);
    }
}