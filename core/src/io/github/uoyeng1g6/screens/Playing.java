package io.github.uoyeng1g6.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.uoyeng1g6.HeslingtonHustle;
import io.github.uoyeng1g6.components.AnimationComponent;
import io.github.uoyeng1g6.components.CounterComponent;
import io.github.uoyeng1g6.components.FixtureComponent;
import io.github.uoyeng1g6.components.HitboxComponent;
import io.github.uoyeng1g6.components.InteractionComponent;
import io.github.uoyeng1g6.components.PlayerComponent;
import io.github.uoyeng1g6.components.PositionComponent;
import io.github.uoyeng1g6.components.TextureComponent;
import io.github.uoyeng1g6.components.TooltipComponent;
import io.github.uoyeng1g6.constants.ActivityType;
import io.github.uoyeng1g6.constants.GameConstants;
import io.github.uoyeng1g6.constants.MoveDirection;
import io.github.uoyeng1g6.constants.PlayerConstants;
import io.github.uoyeng1g6.models.GameState;
import io.github.uoyeng1g6.models.PhysicsPolygon;
import io.github.uoyeng1g6.systems.*;

import java.util.Map;

/**
 * The gameplay screen for the game. Causes a transition to the end screen once all the in-game
 * days have been completed.
 */
public class Playing implements Screen {
    final HeslingtonHustle game;

    final OrthographicCamera camera;
    final Viewport viewport;

    /**
     * The {@code scene2d.ui} stage used to render the ui overlay for this screen.
     */
    Stage stage;

    /**
     * The engine used to handle system updating.
     */
    Engine engine;
    /**
     * The current game state;
     */
    GameState gameState;
    /**
     * The box2d world used for the physics system.
     */
    World world;

    /**
     * The box2d debug renderer used when the game is running in physics debug mode.
     */
    Box2DDebugRenderer debugRenderer = null;

    CameraFollowSystem cameraFollowSystem;


    public Playing(HeslingtonHustle game) {
        this.game = game;

        this.engine = new PooledEngine();
        this.gameState = new GameState();
        this.world = new World(new Vector2(), true);

        camera = new OrthographicCamera();
        this.camera.setToOrtho(false, GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT);
        viewport = new FitViewport(GameConstants.CAMERA_WIDTH, GameConstants.CAMERA_HEIGHT, camera);
        viewport.apply();
        camera.position.set(PlayerConstants.START_POSITION.x, PlayerConstants.START_POSITION.y, 0);
        camera.update();

        cameraFollowSystem = new CameraFollowSystem(gameState, camera, viewport);
        engine.addSystem(cameraFollowSystem);
        Entity playerEntity = initPlayerEntity(engine);
        engine.addEntity(playerEntity);
        cameraFollowSystem.addPlayer(playerEntity);

        stage = new Stage(viewport);

        var labelStyle = new Label.LabelStyle(game.tooltipFont, Color.BLACK);

        var uiTop = new Table();
        uiTop.setFillParent(true);
        uiTop.setDebug(game.debug);
        stage.addActor(uiTop);
        uiTop.center().top();

        var daysLabel = new Label("Monday", labelStyle);
        daysLabel.setFontScale(GameConstants.FONT_SIZE);
        uiTop.add(daysLabel);
        uiTop.row();
        var timeLabel = new Label("07:00", labelStyle);
        timeLabel.setFontScale(GameConstants.FONT_SIZE);
        uiTop.add(timeLabel);

        var counters = new Table(game.skin);
        counters.setFillParent(true);
        counters.pad(1);
        counters.setDebug(game.debug);
        stage.addActor(counters);

        var studyIcon = game.interactionIconsTextureAtlas.findRegion("book_icon");
        var eatIcon = game.interactionIconsTextureAtlas.findRegion("food_icon");
        var recreationIcon = game.interactionIconsTextureAtlas.findRegion("popcorn_icon");
        var studyImage = new Image(studyIcon);
        var eatImage = new Image(eatIcon);
        var recreationImage = new Image(recreationIcon);

        var todayLabel = new Label("Today:", labelStyle);
        todayLabel.setFontScale(GameConstants.FONT_SIZE/2);
        var totalLabel = new Label("Total:", labelStyle);
        totalLabel.setFontScale(GameConstants.FONT_SIZE/2);

        var dayStudyLabel = new Label("0", labelStyle);
        dayStudyLabel.setFontScale(GameConstants.FONT_SIZE);
        var totalStudyLabel = new Label("0", labelStyle);
        totalStudyLabel.setFontScale(GameConstants.FONT_SIZE);

        var dayEatLabel = new Label("0", labelStyle);
        dayEatLabel.setFontScale(GameConstants.FONT_SIZE);
        var totalEatLabel = new Label("0", labelStyle);
        totalEatLabel.setFontScale(GameConstants.FONT_SIZE);

        var dayRecreationLabel = new Label("0", labelStyle);
        dayRecreationLabel.setFontScale(GameConstants.FONT_SIZE);
        var totalRecreationLabel = new Label("0", labelStyle);
        totalRecreationLabel.setFontScale(GameConstants.FONT_SIZE);

        counters.top().right();
        counters.add();
        counters.add(todayLabel).padRight(0.5f);
        counters.add(totalLabel);
        counters.row();
        counters.add(studyImage).width(3).height(3).padRight(0.25f);
        counters.add(dayStudyLabel);
        counters.add(totalStudyLabel);
        counters.row();
        counters.add(eatImage).width(3).height(3).padRight(0.25f);
        counters.add(dayEatLabel);
        counters.add(totalEatLabel);
        counters.row();
        counters.add(recreationImage).width(3).height(3).padRight(0.25f);
        counters.add(dayRecreationLabel);
        counters.add(totalRecreationLabel);

        var energy = new Table(game.skin);
        energy.setFillParent(true);
        energy.pad(1);
        energy.setDebug(game.debug);
        stage.addActor(energy);

        var energyLabel = new Label("Energy Remaining:", labelStyle);
        energyLabel.setFontScale(GameConstants.FONT_SIZE);
        var energyAmount = new Label(String.valueOf(GameConstants.MAX_ENERGY), labelStyle);
        energyAmount.setFontScale(GameConstants.FONT_SIZE);

        energy.top().left();
        energy.add(energyLabel);
        energy.row();
        energy.add(energyAmount);

        Table[] tables = new Table[3];
        tables[0] = uiTop;
        tables[1] = counters;
        tables[2] = energy;
        cameraFollowSystem.addTables(tables);

        initTerrain(game.tiledMap, world);

        for (var entity : initInteractionLocations(engine)) {
            engine.addEntity(entity);
        }

        engine.addEntity(
                engine.createEntity().add(new CounterComponent(daysLabel, new CounterComponent.CounterValueResolver() {
                    // spotless:off
                    private final Map<Integer, String> dayNameMap = Map.of(
                            7, "Monday", 6, "Tuesday", 5, "Wednesday",
                            4, "Thursday", 3, "Friday", 2, "Saturday",
                            1, "Sunday - Exam Tomorrow"
                    );
                    // spotless:on

                    @Override
                    public String resolveValue(GameState gameState) {
                        return dayNameMap.get(gameState.daysRemaining);
                    }
                })));
        engine.addEntity(engine.createEntity().add(new CounterComponent(timeLabel, state -> {
            var newHour = 7 + (GameConstants.MAX_HOURS - state.hoursRemaining);
            return String.format("%s%d:00", newHour < 10 ? "0" : "", newHour);
        })));

        engine.addEntity(engine.createEntity()
                .add(new CounterComponent(
                        dayStudyLabel, state -> String.valueOf(state.currentDay.statFor(ActivityType.STUDY)))));
        engine.addEntity(engine.createEntity()
                .add(new CounterComponent(
                        dayEatLabel, state -> String.valueOf(state.currentDay.statFor(ActivityType.MEAL)))));
        engine.addEntity(engine.createEntity()
                .add(new CounterComponent(
                        dayRecreationLabel,
                        state -> String.valueOf(state.currentDay.statFor(ActivityType.RECREATION)))));

        engine.addEntity(engine.createEntity()
                .add(new CounterComponent(
                        totalStudyLabel, state -> String.valueOf(state.getTotalActivityCount(ActivityType.STUDY)))));
        engine.addEntity(engine.createEntity()
                .add(new CounterComponent(
                        totalEatLabel, state -> String.valueOf(state.getTotalActivityCount(ActivityType.MEAL)))));
        engine.addEntity(engine.createEntity()
                .add(new CounterComponent(
                        totalRecreationLabel,
                        state -> String.valueOf(state.getTotalActivityCount(ActivityType.RECREATION)))));

        engine.addEntity(engine.createEntity()
                .add(new CounterComponent(energyAmount, state -> String.valueOf(state.energyRemaining))));

        engine.addSystem(new PlayerInputSystem(gameState));
        engine.addSystem(new PlayerInteractionSystem(gameState));
        engine.addSystem(new MapRenderingSystem(game.tiledMap, camera));
        engine.addSystem(new StaticRenderingSystem(game.spriteBatch));
        engine.addSystem(new AnimationSystem(game.spriteBatch, gameState));
        engine.addSystem(new TooltipRenderingSystem(game.tooltipFont, game.shapeDrawer, game.spriteBatch, gameState));
        engine.addSystem(new CounterUpdateSystem(gameState));
        engine.addSystem(new CollisionRenderingSystem(gameState));
        if (game.debug) {
            engine.addSystem(new DebugSystem(game.shapeDrawer));
        }
        engine.addSystem(
                new InteractionOverlayRenderingSystem(game.spriteBatch, game.overlayFont, game.shapeDrawer, gameState));

        if (game.physicsDebug) {
            debugRenderer = new Box2DDebugRenderer();
        }
    }

    /**
     * Get the current game state object.
     *
     * @return the game state for this playthrough.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Load collsion objects from map and create them in the box2d world.
     */
    void initTerrain(TiledMap map, World world) {
        Array<Body> bodies = CollisionRenderingSystem.RenderCollisionBodies(map, world);
    }

    /**
     * Initialise the entities for the interaction locations on the map
     *
     * @param engine the engine to create the entities for.
     * @return the created entities.
     */
    Entity[] initInteractionLocations(Engine engine) {
        final var iconSize = 2 / 64f;
        var ref = engine.getSystem(cameraFollowSystem.getClass());

        var studyIcon = game.interactionIconsTextureAtlas.findRegion("book_icon");
        var study = engine.createEntity()
                .add(new TextureComponent(studyIcon, iconSize).show())
                .add(new PositionComponent(30, 82))
                .add(new HitboxComponent(new Rectangle(
                        30, 82, studyIcon.getRegionWidth() * iconSize, studyIcon.getRegionHeight() * iconSize)))
                .add(new InteractionComponent(state -> {
                    if (!state.doActivity(1, 10, ActivityType.STUDY, "Studying...",ref.getCameraPosition())) {
                        // Notify insufficient time/energy
                    }
                }))
                .add(new TooltipComponent(game.tooltipFont, "[E] Study for exams\nTime: -1h\nEnergy: -10"));

        var foodIcon = game.interactionIconsTextureAtlas.findRegion("food_icon");
        var food = engine.createEntity()
                .add(new TextureComponent(foodIcon, iconSize).show())
                .add(new PositionComponent(54, 2.5f))
                .add(new HitboxComponent(new Rectangle(
                        54, 2.5f, foodIcon.getRegionWidth() * iconSize, foodIcon.getRegionHeight() * iconSize)))
                .add(new InteractionComponent(state -> {
                    if (!state.doActivity(1, 5, ActivityType.MEAL, "Eating...",ref.getCameraPosition())) {
                        // Notify insufficient time/energy
                    }
                }))
                .add(new TooltipComponent(game.tooltipFont, "[E] Eat at Piazza\nTime: -1h\nEnergy: -5"));

        var popcornIcon = game.interactionIconsTextureAtlas.findRegion("popcorn_icon");
        var recreation = engine.createEntity()
                .add(new TextureComponent(popcornIcon, iconSize).show())
                .add(new PositionComponent(53.5f, 26.5f))
                .add(new HitboxComponent(new Rectangle(
                        53.5f,
                        26.5f,
                        popcornIcon.getRegionWidth() * iconSize,
                        popcornIcon.getRegionHeight() * iconSize)))
                .add(new InteractionComponent(state -> {
                    if (!state.doActivity(1, 10, ActivityType.RECREATION, "Watching films...",ref.getCameraPosition())) {
                        // Notify insufficient time/energy
                    }
                }))
                .add(new TooltipComponent(game.tooltipFont, "[E] Watch films with mates\nTime: -1h\nEnergy: -10"));

        var sleepIcon = game.interactionIconsTextureAtlas.findRegion("bed_icon");
        var sleep = engine.createEntity()
                .add(new TextureComponent(sleepIcon, iconSize).show())
                .add(new PositionComponent(9, 82f))
                .add(new HitboxComponent(new Rectangle(
                        9, 82, sleepIcon.getRegionWidth() * iconSize, sleepIcon.getRegionHeight() * iconSize)))
                .add(new InteractionComponent(state -> this.gameState.advanceDay(ref.getCameraPosition())))
                //.add(new InteractionComponent(GameState::advanceDay))
                .add(new TooltipComponent(game.tooltipFont, "[E] Go to sleep\nEnds the current day"));

        return new Entity[] {study, food, recreation, sleep};
    }

    /**
     * Initialise the player's physics object in the box2d world.
     *
     * @return the fixture for the player's physics object.
     */
    Fixture initPlayerBody() {
        var player = new BodyDef();
        player.type = BodyDef.BodyType.DynamicBody;
        player.position.set(PlayerConstants.START_POSITION);
        var playerBody = world.createBody(player);
        playerBody.setUserData(PlayerConstants.HITBOX_RADIUS);
        var playerCircle = new CircleShape();
        playerCircle.setRadius(PlayerConstants.HITBOX_RADIUS);
        var playerFixture = playerBody.createFixture(playerCircle, 1f);
        playerCircle.dispose();

        return playerFixture;
    }

    /**
     * Initialise the entity that represents the player character.
     *
     * @param engine the engine to create the entity for.
     * @return the created entity.
     */
    Entity initPlayerEntity(Engine engine) {
        var playerAnimations = new AnimationComponent(0.05f);
        playerAnimations.animations.put(
                MoveDirection.STATIONARY,
                new Animation<>(1f, game.playerTextureAtlas.createSprites("stationary"), Animation.PlayMode.LOOP));
        playerAnimations.animations.put(
                MoveDirection.UP,
                new Animation<>(0.12f, game.playerTextureAtlas.createSprites("walk_up"), Animation.PlayMode.LOOP));
        playerAnimations.animations.put(
                MoveDirection.DOWN,
                new Animation<>(0.12f, game.playerTextureAtlas.createSprites("walk_down"), Animation.PlayMode.LOOP));
        playerAnimations.animations.put(
                MoveDirection.LEFT,
                new Animation<>(0.12f, game.playerTextureAtlas.createSprites("walk_left"), Animation.PlayMode.LOOP));
        playerAnimations.animations.put(
                MoveDirection.RIGHT,
                new Animation<>(0.12f, game.playerTextureAtlas.createSprites("walk_right"), Animation.PlayMode.LOOP));

        return engine.createEntity()
                .add(new PlayerComponent())
                .add(playerAnimations)
                .add(new FixtureComponent(initPlayerBody()));
    }

    @Override
    public void render(float delta) {
        // Allow the final interaction (day transition) to complete before showing the end screen
        if (gameState.daysRemaining == 0 && gameState.interactionOverlay == null) {
            game.setState(HeslingtonHustle.State.END_SCREEN);
            return;
        }

        ScreenUtils.clear(0, 0, 0, 1);

        // This makes transpacency work properly
        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();

        engine.update(delta);
        if (game.physicsDebug) {
            debugRenderer.render(world, camera.combined);
        }
        game.spriteBatch.end();

        stage.act();
        stage.draw();

        world.step(delta, 8, 3);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
