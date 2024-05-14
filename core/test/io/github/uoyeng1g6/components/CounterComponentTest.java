package io.github.uoyeng1g6.components;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import io.github.uoyeng1g6.GdxTestRunner;
import io.github.uoyeng1g6.models.GameState;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@RunWith(GdxTestRunner.class)
class CounterComponentTest {

    @Test
    void testCounterComponent() {
        CounterComponent c = new CounterComponent(mock(Label.class), new CounterComponent.CounterValueResolver() {
            // spotless:off
            private final Map<Integer, String> dayNameMap = Map.of(
                    7, "Monday", 6, "Tuesday", 5, "Wednesday",
                    4, "Thursday", 3, "Friday", 2, "Saturday",
                    1, "Sunday - Exam Tomorrow"
            );
            @Override
            public String resolveValue(GameState gameState) {
                return dayNameMap.get(gameState.daysRemaining);
            }
        });

        assertNotNull(c.label);
        assertNotNull(c.valueResolver);
    }

}