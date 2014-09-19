package fakesetgame.seniordesign;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import java.util.Arrays;
import java.util.Random;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.model.Color;
import fakesetgame.seniordesign.model.TileSet;
import fakesetgame.seniordesign.model.Shading;
import fakesetgame.seniordesign.model.Shape;
import fakesetgame.seniordesign.model.Tile;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class BoardTest extends ApplicationTestCase<Application> {
    public BoardTest() {
        super(Application.class);
    }

    Random random = new Random();

    public void testRandomBoardGeneration() throws Exception{

        Board board = Board.generateRandom();
        Assert.assertEquals(3, board.countSets());
    }
}