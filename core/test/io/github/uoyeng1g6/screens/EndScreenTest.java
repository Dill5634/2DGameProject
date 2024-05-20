package io.github.uoyeng1g6.screens;

import com.badlogic.gdx.math.Vector2;
import io.github.uoyeng1g6.HeslingtonHustle;
import io.github.uoyeng1g6.constants.ActivityType;
import io.github.uoyeng1g6.models.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EndScreenTest {

    EndScreen endScreen;
    HeslingtonHustle heslingtonHustle;
    GameState endState;

    @BeforeEach
    void setUp() {
        heslingtonHustle = mock(HeslingtonHustle.class);
        endState = new GameState();
        endScreen = mock(EndScreen.class);
    }
    // max score with all streaks
    @Test
    void calculateExamScore() {


        // max score with all the streaks
        for (int x = 0; x < 7; x++){
            for (int i=0; i < 3; i++) {
                // eat 3 times
                endState.doActivity(0, 0, ActivityType.SPOONS, "test", new Vector2(0, 0));
            }
            for (int i=0; i < 1; i++){
                // study 5 times
                endState.doActivity(0,0, ActivityType.LIBRARY, "test", new Vector2(0,0));
            }

            for (int i=0; i < 2; i++) {
                // play 3 times
                endState.doActivity(0, 0, ActivityType.DUCKS, "test", new Vector2(0, 0));
            }
            for (int i=0; i < 2; i++) {
                // play 3 times
                endState.doActivity(0, 0, ActivityType.WALK, "test", new Vector2(0, 0));
            }
            endState.advanceDay(new Vector2(0,0));
        }

        endScreen = mock(EndScreen.class);
        //call real method for calculating exam score
        when(endScreen.calculateExamScore(endState)).thenCallRealMethod();
        when(endScreen.getDayScore(anyInt(),anyInt(),anyInt())).thenCallRealMethod();
        assertEquals(100, Math.round(endScreen.calculateExamScore(endState)));

    }

    @Test
    void calculateExamScoreMin(){
        endState = new GameState();
        // max score with all the streaks
        for (int x = 0; x < 7; x++){

            for (int i=0; i < 1; i++){
                // study once
                endState.doActivity(0,0, ActivityType.CSBUILDING, "test", new Vector2(0,0));
            }

            for (int i=0; i < 3; i++) {
                // play 3 times
                endState.doActivity(0, 0, ActivityType.PIAZZA, "test", new Vector2(0, 0));
            }
            for (int i=0; i < 3; i++) {
                // play 3 times
                endState.doActivity(0, 0, ActivityType.RECREATION, "test", new Vector2(0, 0));
            }
            endState.advanceDay(new Vector2(0,0));
        }

        endScreen = mock(EndScreen.class);
        //call real method for calculating exam score
        when(endScreen.calculateExamScore(endState)).thenCallRealMethod();
        when(endScreen.getDayScore(anyInt(),anyInt(),anyInt())).thenCallRealMethod();
        assertEquals(77, Math.round(endScreen.calculateExamScore(endState)));

    }




    @Test
    void getDayScoreTest(){

        endScreen = mock(EndScreen.class);
        int x = 0;
        int y = 0;
        int z = 0;
        //nothing generates no score
        when(endScreen.getDayScore(x,y,z)).thenCallRealMethod();
        assertEquals(0, endScreen.getDayScore(x,y,z));

        //check no score is given if no study occurs
        y = 1;
        z = 1;
        when(endScreen.getDayScore(x,y,z)).thenCallRealMethod();
        assertEquals(0, endScreen.getDayScore(x,y,z));

        x = 1;
        when(endScreen.getDayScore(x,y,z)).thenCallRealMethod();
        assertEquals(59, Math.round(endScreen.getDayScore(x,y,z)));

        x = 1;
        y = 3;
        z = 3;
        when(endScreen.getDayScore(x,y,z)).thenCallRealMethod();
        assertEquals(81, Math.round(endScreen.getDayScore(x,y,z)));
    }
}