package fakesetgame.seniordesign.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sami on 9/24/2014.
 */
public class Game {

    Board board;
    int timeElapsed;
    int score;
    Set<Set> trackSet = new HashSet<Set>();
    Set<Tile> tileSet = new HashSet<Tile>();

    public boolean attemptSet(Tile tile1, Tile tile2, Tile tile3) {
        if (TileSet.isValidSet(tile1, tile2, tile3)) {
            tileSet.add(tile1);
            tileSet.add(tile2);
            tileSet.add(tile3);
            trackSet.add(tileSet);
            score = score + 1;
            return true;
        }
        return false;
    }
    public int getScore() {
        return score;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }



    //the game has a board, timer, score (sets found), keep track of sets found


}
