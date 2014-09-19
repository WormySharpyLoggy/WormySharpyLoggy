package fakesetgame.seniordesign.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by Sami on 9/17/2014.
 */
public class Board {
    private Tile[] tiles;

    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final int TILES = ROWS * COLS;
    private static final String TAG = "Board";

    public Board(Tile... tiles) {

        if(tiles.length!= TILES)
            throw new IllegalArgumentException(String.format("Board is %dx%d, having %d tiles. Please provide the correct number of tiles.", ROWS, COLS, TILES));

        Set<Tile> tileSet = new HashSet<Tile>();
        for(int i=0; i<tiles.length; i++) {
            if (tiles[i] == null)
                throw new IllegalArgumentException("No tiles can be null.");
            else if (!tileSet.add(tiles[i]))
                throw new IllegalArgumentException("All tiles must be distinct. No repeated tiles on one board are allowed.");
        }

        this.tiles = tiles;
    }

    public static Board generateRandom(){
        int targetSetNum = 3; // in testing, more than 3 sets in 9 cards is rare.
        int tries = 0;
        while(true) {
            tries++;
            Set<Tile> tileSet = new HashSet<Tile>();

            while (tileSet.size() < TILES)
                tileSet.add(Tile.generateRandom());

            Board board = new Board(tileSet.toArray(new Tile[TILES]));

            int sets = board.countSets();
            if (sets == targetSetNum) {
                Log.d(TAG, String.format("Generated a random board with %d sets in %d tries.", sets, tries));
                return board;
            }
            else
                Log.d(TAG, String.format("Generated a random board with %d sets. Trying again.", sets));
        }
    }

    public int countSets(){

        int setCount = 0;

        for(int i=0; i<tiles.length; i++)
            for(int j=i+1; j<tiles.length; j++)
                for(int k=j+1; k<tiles.length; k++)
                    if(TileSet.isValidSet(tiles[i], tiles[j], tiles[k]))
                        setCount++;

        return setCount;
    }

    public Tile getTile(int row, int column) {
        if (row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("Row out of bounds.");
        }
        if (column < 0 || column >= COLS) {
            throw new IllegalArgumentException("Column out of bounds.");
        }

        return tiles[row * COLS + column];
    }
}
