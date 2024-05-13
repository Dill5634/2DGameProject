package io.github.uoyeng1g6.constants;

/**
 * Constants used throughout the game
 */
public class GameConstants {
    /**
     * The width of the game world in tiles.
     */
    public static final int WORLD_WIDTH = 130;
    /**
     * The height of the game world in tiles.
     */
    public static final int WORLD_HEIGHT = 108;
    /**
     * The width of the camera in tiles
     */
    public static final float CAMERA_WIDTH = 130/3.5f;
    /**
     * The height of the camera in tiles
     */
    public static final float CAMERA_HEIGHT = 108/3.5f;
    /**
     * The pixels per tile
     */
    public static final float PIXELS_PER_TILE = 32f;
    /**
     * Frames Per Second
     */
    public static final float FPS = 60;
    /**
     *  Basic Font size for playing the game
     */
    public static final float FONT_SIZE = 0.1f;
    /**
     * The maximum amount of energy available to the player on a single day.
     */
    public static final int MAX_ENERGY = 100;
    /**
     * The maximum amount of hours available to the player on a single day.
     */
    public static final int MAX_HOURS = 16;
    /**
     * The number of seconds to display the interaction overlay for per hour used by the interaction.
     */
    public static final int OVERLAY_SECONDS_PER_HOUR = 2;
}
