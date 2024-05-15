package io.github.uoyeng1g6.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.uoyeng1g6.HeslingtonHustle;
import io.github.uoyeng1g6.constants.ActivityType;
import io.github.uoyeng1g6.constants.GameConstants;
import io.github.uoyeng1g6.models.GameState;
import io.github.uoyeng1g6.models.LeaderBoard;
import io.github.uoyeng1g6.utils.ChangeListener;
import java.util.List;

/**
 * The end screen of the game. Displays the player's score and the total number done of each activity.
 */
public class EndScreen implements Screen {
    /**
     * Theoretical maximum day score. Allows normalising to range 0-100.
     */
    private static final float MAX_DAY_SCORE = 105.125f;
    /**
     * Theoretical minimum day score. Allows normalising to range 0-100.
     */
    private static final float MIN_DAY_SCORE = 0f;

    Camera camera;
    /**
     * The {@code scene2d.ui} stage used to render this screen.
     */
    Stage stage;

    LeaderBoard leaderboard;


    public EndScreen(HeslingtonHustle game, GameState endGameState) {
        camera = new OrthographicCamera();
        var viewport = new FitViewport(GameConstants.WORLD_WIDTH * 10, GameConstants.WORLD_HEIGHT * 10, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        leaderboard = new LeaderBoard();
        float finalScore = calculateExamScore(endGameState);
        leaderboard.insert(MainMenu.playerName, finalScore);


        var root = new Table(game.skin);
        root.setFillParent(true);
        root.pad(0.25f);

        root.setDebug(game.debug);
        stage.addActor(root);

        root.add("                                                                          Game Over").getActor().setFontScale(2);
        root.row();


        var leader= new Table(game.skin);
        leader.add("                        LEADERBOARD");
        leader.row();
        leader.add(System.lineSeparator());
        leader.row();
        for(int x = 0;x<10;x++){

            leader.add(leaderboard.show(x)[0]);
            leader.add(leaderboard.show(x)[1]);
            leader.row();

        }

        String streakMessage= "";
        if(endGameState.streaks[0])streakMessage+="   FEEDER   ";
        if(endGameState.streaks[1])streakMessage+="   WALKER   ";
        if(endGameState.streaks[2])streakMessage+="   BOOKWORM   ";
        if(endGameState.streaks[3])streakMessage+="   ALCOHOLIC   ";
        if(endGameState.streaks[4])streakMessage+="   FAST BREAKER   ";



        var inner = new Table(game.skin);


        inner.add(String.format("Exam Score: %.2f / 100", finalScore))
                .padBottom(50);
        inner.row();
        inner.add("Times Studied: " + endGameState.getTotalActivityCount(ActivityType.WORK));
        inner.row();
        inner.add("Meals Eaten: " + endGameState.getTotalActivityCount(ActivityType.EAT));
        inner.row();
        inner.add("Recreational Activities Done: " + endGameState.getTotalActivityCount(ActivityType.PLAY));
        inner.row();
        inner.add("Streak Achievements Gained:" + streakMessage);
        inner.row();

        var mainMenuButton = new TextButton("Main Menu", game.skin);
        mainMenuButton.addListener(ChangeListener.of((e, a) -> game.setState(HeslingtonHustle.State.MAIN_MENU)));
        inner.add(mainMenuButton)
                .padTop(50)
                .width(Value.percentWidth(0.4f, inner))
                .height(Value.percentHeight(0.1f, inner));
        root.add(leader);
        root.add(inner).grow();
    }

    /**
     * Calculate the score for a given day based on the number of activities performed. The optimal score
     * is given by studying 5 times, eating 3 times, and doing a recreational activity 3 times.
     *
     * @param studyCount the number of times the player studied during the day.
     * @param mealCount the number of times the player ate during the day.
     * @param recreationCount the number of recreational activities done by the player during the day.
     * @return the computed score given the activity counts.
     */
    float getDayScore(int studyCount, int mealCount, int recreationCount) {

        var studyPoints = 0;
        for (int i = 1; i <= studyCount; i++) {
            studyPoints += i <= 5 ? 10 : -5;
        }
        studyPoints = Math.max(0, studyPoints);

        // Calculate meal multiplier
        float mealMultiplier = 1;
        for (var i = 1; i <= mealCount; i++) {
            mealMultiplier += i <= 3 ? 0.09f : -0.025f;
        }
        mealMultiplier = Math.max(1, mealMultiplier);

        // Calculate recreation multiplier
        float recreationMultiplier = 1;
        for (var i = 1; i <= recreationCount; i++) {
            recreationMultiplier += i <= 3 ? 0.09f : -0.025f;
        }
        recreationMultiplier = Math.max(1, recreationMultiplier);

        // Calculate day score
        return studyPoints * mealMultiplier * recreationMultiplier;
    }

    /**
     * Calculate the aggregate score of all the days.
     *
     * @param state the current game state
     * @return the computed game score.
     */
    float calculateExamScore(GameState state) {

        List<GameState.Day>days  = state.days;
        float totalScore = 0;

        for (var day : days) {
            int studyCount = day.statFor(ActivityType.WORK);
            int mealCount = day.statFor(ActivityType.EAT);
            int recreationCount = day.statFor(ActivityType.PLAY);

            var dayScore = getDayScore(studyCount, mealCount, recreationCount);
            // Normalise day score between 0 and 100, round up to nearest whole number
            var normalisedDayScore = Math.ceil(((dayScore - MIN_DAY_SCORE) * 100) / (MAX_DAY_SCORE - MIN_DAY_SCORE));

            // Increase total score
            totalScore += (float) (normalisedDayScore * (1 / 7f));
        }

        for(int x =0;x<5;x++){
             totalScore += state.streaks[x] ? 5 : 0;
      }


        // Clamp total score from 0-100
        return Math.min(100, Math.max(0, totalScore));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);


        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }
}
