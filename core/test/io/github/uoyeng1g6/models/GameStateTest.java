package io.github.uoyeng1g6.models;

import com.badlogic.gdx.math.Vector2;
import io.github.uoyeng1g6.constants.ActivityType;
import io.github.uoyeng1g6.constants.GameConstants;
import io.github.uoyeng1g6.systems.CounterUpdateSystem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    private GameState state = new GameState();
    //private CounterUpdateSystem updateSystem;
    //public final ArrayList<GameState.Day> days = new ArrayList<>(7);

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
        state.doActivity(1,1, ActivityType.WALK,"test", new Vector2(0,0));
        //System.out.println(state.currentDay.activityStats);
        //check counter updated
        assertEquals(state.currentDay.activityStats.get(ActivityType.WALK), 1);
        //check hours updated
        assertEquals(state.hoursRemaining, 7);
        //check energy updated
        assertEquals(state.energyRemaining, 9);

        //attempt to perform activity without sufficient energy
        state.doActivity(50,0, ActivityType.BREAKFAST, "test", new Vector2(0,0));
        assertNull(state.currentDay.activityStats.get(ActivityType.BREAKFAST));
        //check hours remain the same
        assertEquals(state.hoursRemaining, 7);
        //check energy remains the same
        assertEquals(state.energyRemaining, 9);

        //attempt to perform activity without sufficient time
        state.doActivity(0,50, ActivityType.BREAKFAST, "test", new Vector2(0,0));
        assertNull(state.currentDay.activityStats.get(ActivityType.BREAKFAST));
        //check hours remain the same
        assertEquals(state.hoursRemaining, 7);
        //check energy remains the same
        assertEquals(state.energyRemaining, 9);
    }

    @Test
    void getTotalActivityCount() {
    }

    @Test
    void testGetTotalActivityCount() {
    }
}