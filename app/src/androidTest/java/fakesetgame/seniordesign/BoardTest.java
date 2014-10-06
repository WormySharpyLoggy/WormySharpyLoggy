package fakesetgame.seniordesign;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.model.Tile;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class BoardTest extends ApplicationTestCase<Application> {
    public BoardTest() {
        super(Application.class);
    }

    public void testRandomBoardGeneration() throws Exception{

        Board board = Board.generateRandom(0);
        Assert.assertEquals(0, board.countSets());

        board = Board.generateRandom(1);
        Assert.assertEquals(1, board.countSets());

        board = Board.generateRandom(2);
        Assert.assertEquals(2, board.countSets());

        board = Board.generateRandom(3);
        Assert.assertEquals(3, board.countSets());

        board = Board.generateRandom(4);
        Assert.assertEquals(4, board.countSets());

        board = Board.generateRandom(5);
        Assert.assertEquals(5, board.countSets());

        board = Board.generateRandom(6);
        Assert.assertEquals(6, board.countSets());

        board = Board.generateRandom(12);
        Assert.assertEquals(12, board.countSets());
    }

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
}