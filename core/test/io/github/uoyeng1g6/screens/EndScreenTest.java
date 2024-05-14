package io.github.uoyeng1g6.screens;

import com.badlogic.gdx.math.Vector2;
import io.github.uoyeng1g6.GdxTestRunner;
import io.github.uoyeng1g6.HeslingtonHustle;
import io.github.uoyeng1g6.constants.ActivityType;
import io.github.uoyeng1g6.models.GameState;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(GdxTestRunner.class)
class EndScreenTest {

//    EndScreen endScreen;
//    HeslingtonHustle heslingtonHustle;
//    GameState endState;
//
//    @BeforeEach
//    void setUp() {
//        heslingtonHustle = mock(HeslingtonHustle.class);
//        endState = new GameState();
//    }
//
//    @Test
//    void getDayScore() {
//        //get day score for a day with no activities
//        assertEquals(0, endScreen.getDayScore(0,0,0));
//    }
//
//    @Test
//    void calculateExamScore() {
//
//        for (int x = 0; x < 7; x++){
//            for (int i=0; i < 5; i++){
//                // study 5 times
//                endState.doActivity(0,0, ActivityType.LIBRARY, "test", new Vector2(0,0));
//            }
//            for (int i=0; i < 3; i++) {
//                // eat 3 times
//                endState.doActivity(0, 0, ActivityType.SPOONS, "test", new Vector2(0, 0));
//            }
//            for (int i=0; i < 3; i++) {
//                // play 3 times
//                endState.doActivity(0, 0, ActivityType.DUCKS, "test", new Vector2(0, 0));
//            }
//            endState.advanceDay(new Vector2(0,0));
//        }
//        endScreen = new EndScreen(heslingtonHustle, endState);
//        //crashes here when creating endScreen
//        //endScreen = mock();
//        assertEquals(endScreen.calculateExamScore(endState), 77);
//    }
}