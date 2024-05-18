package io.github.uoyeng1g6.constants;

import com.badlogic.gdx.math.Vector2;

/**
 * Constants for the player character.
 */
public class PlayerConstants {
    /**
     * The radius of the player's circular hitbox from within interactions can be triggered.
     */
    public static final float PLAYER_SCALER = 15;
    /**
     * Used for both SPRITE_SCALE and HITBOX_RADIUS to keep these consistent
     */
    public static final float SPRITE_SCALE = 0.03f;
    /**
     * The size of the player sprite
     */
    public static final float HITBOX_RADIUS = SPRITE_SCALE * PLAYER_SCALER;
    /**
     * The movement speed of the player.
     */
    public static final float PLAYER_SPEED = 20f;
    /**
     * The starting position of the player.
     */
    public static final Vector2 START_POSITION = new Vector2(9, 82);
}
