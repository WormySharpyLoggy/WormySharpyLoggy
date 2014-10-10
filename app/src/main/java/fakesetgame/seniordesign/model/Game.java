package fakesetgame.seniordesign.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sami on 9/24/2014.
 */
public class Game {

    private Board board;
    private int timeElapsed;
    private Set<Set> trackSet = new HashSet<Set>();


    public boolean attemptSet(Tile tile1, Tile tile2, Tile tile3) {
        if (TileSet.isValidSet(tile1, tile2, tile3)) {
            Set<Tile> tileSet = new HashSet<Tile>();
            tileSet.add(tile1);
            tileSet.add(tile2);
            tileSet.add(tile3);
            if (trackSet.add(tileSet)) {
                return true;
            }
        }
        return false;
    }
    public int getScore() {
        return trackSet.size();
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }



    //the game has a board, timer, score (sets found), keep track of sets found


}
