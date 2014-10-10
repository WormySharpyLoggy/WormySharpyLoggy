package fakesetgame.seniordesign;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import fakesetgame.seniordesign.model.Game;
import fakesetgame.seniordesign.model.Color;
import fakesetgame.seniordesign.model.TileSet;
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
            Game gameState2 = new Game();
            int initialScore2;
            boolean isValidTrue;

            Tile validTile1 = new Tile(Shape.Oval, Shading.Filled, Color.Red, 1);
            Tile validTile2 = new Tile(Shape.Oval, Shading.Filled, Color.Red, 2);
            Tile validTile3 = new Tile(Shape.Oval, Shading.Filled, Color.Red, 3);

            initialScore2 = gameState2.getFoundSetCount();
            isValidTrue = gameState2.attemptSet(validTile1, validTile2, validTile3);

            Assert.assertEquals(initialScore2 + 1, gameState2.getFoundSetCount());
            Assert.assertTrue(isValidTrue);
        }


      }

