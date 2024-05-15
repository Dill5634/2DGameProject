package io.github.uoyeng1g6.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.uoyeng1g6.constants.GameConstants;
import io.github.uoyeng1g6.models.GameState;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * System that handles drawing a semi-transparent overlay over the game screen
 * while an interaction is currently taking place. Also handles ending the interaction once
 * the overlay timeout has expired.
 */
public class InteractionOverlayRenderingSystem extends EntitySystem {
    /**
     * The colour to draw the interaction overlay.
     */
    private static final Color OVERLAY_COLOR = new Color(0, 0, 0, 0.35f);

    /**
     * The sprite batch to use to draw the overlay.
     */
    private final SpriteBatch batch;
    /**
     * The font to use to write on the overlay.
     */
    private final BitmapFont font;
    /**
     * The shapedrawer to use to draw the overlay.
     */
    private final ShapeDrawer shapeDrawer;
    /**
     * The game state;
     */
    private final GameState gameState;

    /**
     * The amount of time elapsed since the overlay was shown. If {@code -1} then no overlay is currently being
     * shown.
     */
    private float elapsed = -1;

    public InteractionOverlayRenderingSystem(
            SpriteBatch batch, BitmapFont font, ShapeDrawer shapeDrawer, GameState gameState) {
        this.batch = batch;
        this.font = font;
        this.shapeDrawer = shapeDrawer;
        this.gameState = gameState;
    }

    @Override
    public void update(float deltaTime) {
        if (gameState.interactionOverlay == null) {
            // We don't need to render anything if there is no current interaction
            return;
        }

        if (elapsed == -1) {
            elapsed = 0;
        } else {
            elapsed += deltaTime;
        }

        var pos = gameState.interactionOverlay.position;


        var halfWorldWidth = GameConstants.CAMERA_WIDTH / 2;
        var halfWorldHeight = GameConstants.CAMERA_HEIGHT / 2;
        var zeroX = pos.x-halfWorldWidth;
        var zeroY = pos.y-halfWorldHeight;
        shapeDrawer.filledRectangle(zeroX,zeroY , GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT, OVERLAY_COLOR);

        var layout = new GlyphLayout(font, gameState.interactionOverlay.text);
        font.draw(batch, layout,zeroX+ halfWorldWidth - (layout.width / 2), zeroY+ halfWorldHeight + (layout.height / 2));

        shapeDrawer.filledRectangle(
                zeroX +halfWorldWidth - (halfWorldWidth / 2) - 0.5f,
                zeroY+(halfWorldHeight / 2),
                halfWorldWidth + 1,
                3,
                Color.BLACK);

        var progressBarSize = (elapsed / gameState.interactionOverlay.displayFor) * halfWorldWidth;
        shapeDrawer.filledRectangle(
                zeroX+ halfWorldWidth - (halfWorldWidth / 2), zeroY+(halfWorldHeight / 2) + 0.5f, progressBarSize, 2, Color.WHITE);

        if (elapsed >= gameState.interactionOverlay.displayFor) {
            gameState.interactionOverlay = null;
            elapsed = -1;
        }
    }
}
