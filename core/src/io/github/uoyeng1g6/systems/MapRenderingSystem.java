package io.github.uoyeng1g6.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.uoyeng1g6.constants.GameConstants;

/**
 * System to handle rendering the tilemap onto the game background.
 */
public class MapRenderingSystem extends EntitySystem {
    private final OrthographicCamera camera;

    private final OrthogonalTiledMapRenderer renderer;

    public MapRenderingSystem(TiledMap tiledMap, OrthographicCamera camera) {
        this.camera = camera;

        this.renderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / GameConstants.PIXELS_PER_TILE);
    }

    @Override
    public void update(float deltaTime) {
        renderer.setView(camera);

        renderer.render();
    }

    public TiledMap getTiledMap() {return renderer.getMap();}
}
