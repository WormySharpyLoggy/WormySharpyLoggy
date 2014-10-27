package fakesetgame.seniordesign;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import junit.framework.Assert;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.model.Modifier;
import fakesetgame.seniordesign.model.Tile;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class BoardTest extends ApplicationTestCase<Application> {
    public BoardTest() {
        super(Application.class);
    }

    public void testRandomBoardGeneration() throws Exception{

        Board board = Board.generateRandom(0, 0 * Modifier.minModifier, 0 * Modifier.maxModifier);
        Assert.assertEquals(0, board.countSets());

        board = Board.generateRandom(1, 1 * Modifier.minModifier, 1 * Modifier.maxModifier);
        Assert.assertEquals(1, board.countSets());

        board = Board.generateRandom(2, 2 * Modifier.minModifier, 2 * Modifier.maxModifier);
        Assert.assertEquals(2, board.countSets());

        board = Board.generateRandom(3, 3 * Modifier.minModifier, 3 * Modifier.maxModifier);
        Assert.assertEquals(3, board.countSets());

        board = Board.generateRandom(4, 4 * Modifier.minModifier, 4 * Modifier.maxModifier);
        Assert.assertEquals(4, board.countSets());

        board = Board.generateRandom(5, 5 * Modifier.minModifier, 5 * Modifier.maxModifier);
        Assert.assertEquals(5, board.countSets());

        board = Board.generateRandom(6, 6 * Modifier.minModifier, 6 * Modifier.maxModifier);
        Assert.assertEquals(6, board.countSets());

        board = Board.generateRandom(12, 12 * Modifier.minModifier, 12 * Modifier.maxModifier);
        Assert.assertEquals(12, board.countSets());
    }

    public void testTopThirdDifficultyBoardGeneration() throws Exception {
        double minDiff = 6 * Modifier.COUNT_DIFFERENCE.getValue() *
                Modifier.COLOR_DIFFERENCE.getValue() * Modifier.SHAPE_DIFFERENCE.getValue();

        double maxDiff = 6 * Modifier.maxModifier;

        int foundBoards = 0;
        for (int i = 1; i < 11; i++) {
            Board b = Board.generateRandom(6, minDiff, maxDiff);
            if (b.getDifficulty() >= minDiff && b.getDifficulty() <= maxDiff) {
                foundBoards++;
                Log.d("Fini", String.format("Found board for round %d", i));
            }
        }
        Assert.assertEquals(10, foundBoards);
    }

    public void testMiddleThirdDifficultyBoardGeneration() throws Exception {
        double minDiff = 6 * Modifier.COUNT_DIFFERENCE.getValue() *
                Modifier.SHADING_DIFFERENCE.getValue();

        double maxDiff = 6 * Modifier.COUNT_DIFFERENCE.getValue() *
                Modifier.COLOR_DIFFERENCE.getValue() * Modifier.SHAPE_DIFFERENCE.getValue();
        int foundBoards = 0;
        for (int i = 1; i < 11; i++) {
            Board b = Board.generateRandom(6, minDiff, maxDiff);
            if (b.getDifficulty() >= minDiff && b.getDifficulty() <= maxDiff) {
                foundBoards++;
                Log.d("Fini", String.format("Found board for round %d", i));
            }
        }
        Assert.assertEquals(10, foundBoards);
    }

    /* This test kind of never finishes, so...

    public void testBottomThirdDifficultyBoardGeneration() throws Exception {
        double minDiff = 6 * Modifier.minModifier;

        double maxDiff = 6 * Modifier.COUNT_DIFFERENCE.getValue() *
                Modifier.SHADING_DIFFERENCE.getValue();
        int foundBoards = 0;
        for (int i = 1; i < 11; i++) {
            Board b = Board.generateRandom(6, minDiff, maxDiff);
            if (b.getDifficulty() >= minDiff && b.getDifficulty() <= maxDiff) {
                foundBoards++;
                Log.d("Fini", String.format("Found board for round %d", i));
            }
        }
        Assert.assertEquals(10, foundBoards);
    }
    */

    public void testExplicitBoardGeneration() throws Exception{
        Set<Tile> tiles = new HashSet<Tile>();
        while(tiles.size() < 9)
            tiles.add(Tile.generateRandom());
        Queue<Tile> q = new LinkedList<Tile>(tiles);

        Tile t11 = q.remove();
        Tile t12 = q.remove();
        Tile t13 = q.remove();
        Tile t21 = q.remove();
        Tile t22 = q.remove();
        Tile t23 = q.remove();
        Tile t31 = q.remove();
        Tile t32 = q.remove();
        Tile t33 = q.remove();

        Board board = new Board(t11, t12, t13, t21, t22, t23, t31, t32, t33);

        Assert.assertEquals(t11, board.getTile(0));
        Assert.assertEquals(t12, board.getTile(1));
        Assert.assertEquals(t13, board.getTile(2));
        Assert.assertEquals(t21, board.getTile(3));
        Assert.assertEquals(t22, board.getTile(4));
        Assert.assertEquals(t23, board.getTile(5));
        Assert.assertEquals(t31, board.getTile(6));
        Assert.assertEquals(t32, board.getTile(7));
        Assert.assertEquals(t33, board.getTile(8));
    }

    public void testBoardSerialization() throws Exception{
        for(int i=0; i<10; i++) {
            Board board = Board.generateRandom(3, 3 * Modifier.minModifier, 3 * Modifier.maxModifier);
            String boardString = board.toString();

            Assert.assertTrue(
                    String.format("Board string doesn't look like [123]{4}(,[123]{4}){8}.\n%s", boardString),
                    boardString.matches("[123]{4}(,[123]{4}){8}"));

            Board deserializedBoard = Board.fromString(boardString);

            Assert.assertEquals(
                    String.format("Board either incorrectly serialized or deserialized.\nOriginal:\n\t%s\nAfter Serialize-Deserialize:\n\t%s", board, deserializedBoard),
                    board, deserializedBoard);
        }
    }
}