package io.github.uoyeng1g6.components;

import com.badlogic.ashley.core.Component;

/**
 * Component that enables an entity to be positioned on the game screen.
 */
public class PositionComponent implements Component {
    /**
     * The x coordinate.
     */
    public static float x;
    /**
     * The y coordinate.
     */
    public static float y;

    public PositionComponent(float x, float y) {
        PositionComponent.x = x;
        PositionComponent.y = y;
    }

    public static float getX() {
        return x;
    }

    public static float getY() {
        return y;
    }

    public void setX(float x) {
        PositionComponent.x = x;
    }

    public void setY(float y) {
        PositionComponent.y = y;
    }
}
