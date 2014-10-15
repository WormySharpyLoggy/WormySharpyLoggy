package fakesetgame.seniordesign;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import fakesetgame.seniordesign.model.Game;
import fakesetgame.seniordesign.model.Color;
import fakesetgame.seniordesign.model.Shading;
import fakesetgame.seniordesign.model.Shape;
import fakesetgame.seniordesign.model.Tile;


/**
 * Created by Sami on 10/3/2014.
 */
public class GameTest extends ApplicationTestCase<Application> {
    public GameTest() {
        super(Application.class);
    }

    public void testInvalidAttemptingSet() throws Exception {
        Game gameState = new Game();
        int initialScore;
        boolean isValidFalse;

        Tile tile1 = new Tile(Shape.Oval, Shading.Filled, Color.Red, 1);
        Tile tile2 = new Tile(Shape.Oval, Shading.Filled, Color.Red, 2);
        Tile tile3 = new Tile(Shape.Oval, Shading.Filled, Color.Green, 1);

        initialScore = gameState.getFoundSetCount();
        isValidFalse = gameState.attemptSet(tile1, tile2, tile3);

        Assert.assertEquals(initialScore, gameState.getFoundSetCount());
        Assert.assertFalse(isValidFalse);
    }

    public void testValidAttemptingSet() throws Exception {
        Game gameState = new Game();
        int initialScore;
        boolean isValidTrue;

        Tile validTile1 = new Tile(Shape.Oval, Shading.Filled, Color.Red, 1);
        Tile validTile2 = new Tile(Shape.Oval, Shading.Filled, Color.Red, 2);
        Tile validTile3 = new Tile(Shape.Oval, Shading.Filled, Color.Red, 3);

        initialScore = gameState.getFoundSetCount();
        isValidTrue = gameState.attemptSet(validTile1, validTile2, validTile3);

        Assert.assertEquals(initialScore + 1, gameState.getFoundSetCount());
        Assert.assertTrue(isValidTrue);

        Assert.assertFalse("Sending the same elements again should not be a valid attempt.",
                gameState.attemptSet(validTile1, validTile2, validTile3)
        );
    }

    public void testGameTimer() throws Exception {
        Game game = new Game();

        // sleep 200 ms
        Thread.sleep(200);

        long elapsed = game.getElapsedTime();
        Assert.assertTrue(
                String.format("Timer should have elapsed about 200ms, but elapsed %dms instead.", game.getElapsedTime()),
                Math.abs(game.getElapsedTime() - 200) < 10
        );

        game.unpauseTimer();
        Assert.assertTrue(
                "Starting a timer that is already running should not cause a change in elapsed time, but it has.",
                Math.abs(elapsed - game.getElapsedTime()) < 10
        );

        game.pauseTimer();
        elapsed = game.getElapsedTime();

        Thread.sleep(200);
        Assert.assertEquals(
                "Timer should not have elapsed while paused, but it has.",
                elapsed, game.getElapsedTime());

        game.pauseTimer();
        Assert.assertEquals(
                "Pausing twice should not cause elapsed time to change, but it has.",
                elapsed, game.getElapsedTime());

        game.unpauseTimer();

        Thread.sleep(200);
        long newElapsed = game.getElapsedTime();
        Assert.assertTrue(
                String.format("After resuming, timer should have elapsed about 200ms, but old elapsed time is %dms and new elapsed time is %dms.", elapsed, newElapsed),
                newElapsed >= elapsed + 200 && newElapsed < elapsed + 400);

        game.pauseTimer();
        elapsed = game.getElapsedTime();

        Assert.assertTrue(
                String.format("Timer should have 400 ms elapsed time, but it shows %dms.", elapsed),
                Math.abs(game.getElapsedTime() - 400) < 10);

        Thread.sleep(200);
        Assert.assertEquals(
                "Timer should not have elapsed while paused (2nd pause), but it has.",
                elapsed, game.getElapsedTime());

        game.unpauseTimer();

        Thread.sleep(200);
        newElapsed = game.getElapsedTime();
        Assert.assertTrue(
                String.format("After resuming (2nd resume), timer should have elapsed about 200ms, but old elapsed time is %dms and new elapsed time is %dms.", elapsed, newElapsed),
                newElapsed >= elapsed + 200 && newElapsed < elapsed + 400);

        Assert.assertTrue(
                String.format("Timer should have 600 ms elapsed time (2nd pause), but it shows %dms.", game.getElapsedTime()),
                Math.abs(game.getElapsedTime() - 600) < 10);

        game.restartTimer();
        Thread.sleep(100);
        Assert.assertTrue(
                String.format("Timer should have 100 ms elapsed time, but it shows %dms.", game.getElapsedTime()),
                Math.abs(game.getElapsedTime() - 100) < 10);
    }
}

