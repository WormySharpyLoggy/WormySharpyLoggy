package fakesetgame.seniordesign.model;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sami on 9/17/2014.
 */
public class Board {
    private Tile[] tiles;
    private Set<Set<Tile>> sets;

    public static final int TILES = 9;
    private static final String TAG = "Board";

    public Board(Tile... tiles) {

        if (tiles.length != TILES)
            throw new IllegalArgumentException(String.format("Board has %d tiles. Please provide the correct number of tiles.", TILES));

        Set<Tile> tileSet = new HashSet<Tile>();
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == null)
                throw new IllegalArgumentException("No tiles can be null.");
            else if (!tileSet.add(tiles[i]))
                throw new IllegalArgumentException("All tiles must be distinct. No repeated tiles on one board are allowed.");
        }

        this.tiles = tiles;
        sets = TileSet.getSets(tiles);
    }

    public static Board fromString(String board){
        String[] tileStrings = board.split(",");
        if(tileStrings.length != 9)
            throw new IllegalArgumentException(String.format("Board should contain exactly 9 tiles. Found %d.", tileStrings.length));

        Tile[] tiles = new Tile[tileStrings.length];
        for(int i=0; i<tiles.length; i++)
            tiles[i] = Tile.fromString(tileStrings[i]);

        return new Board(tiles);
    }

    public static Board generateRandom(int targetSetCount) {
        if (targetSetCount < 0)
            throw new IllegalArgumentException("Cannot produce a board with negative set count.");

        if (TILES == 9 && (targetSetCount > 12 || targetSetCount == 7 || targetSetCount == 9 || targetSetCount == 10 || targetSetCount == 11))
            throw new IllegalArgumentException("A 9-tile board can have 0, 1, 2, 3, 4, 5, 6, 8, or 12 sets. No other number of sets is possible.");

        if (TILES != 9)
            throw new IllegalArgumentException("Statistics for boards of a size other than 9 have not been determined.");

        Set<Tile> tileSet = TileSet.getRandom(TILES);
        int tries = 0;
        while (true) {
            tries++;
            Tile[] tiles = tileSet.toArray(new Tile[TILES]);
            int sets = TileSet.countSets(tiles);
            if (sets == targetSetCount) {
                Log.d(TAG, String.format("Generated a random board with %d sets in %d tries.", sets, tries));
                return new Board(tileSet.toArray(new Tile[TILES]));
            } else {
                Log.d(TAG, String.format("Board has %d sets of target %d sets. Adjusting...", sets, targetSetCount));

                Tile minTile = null, maxTile = null;
                int minCount = Integer.MAX_VALUE, maxCount = Integer.MIN_VALUE;
                for (Tile tile : tiles) {
                    int count = TileSet.countSetsContainingTile(tile, tiles);
                    if (count < minCount) {
                        minCount = count;
                        minTile = tile;
                    }
                    if (count > maxCount) {
                        maxCount = count;
                        maxTile = tile;
                    }
                }

                Tile newTile = Tile.generateRandom();
                while (tileSet.contains(newTile))
                    newTile = Tile.generateRandom();

                if (sets < targetSetCount)
                    tileSet.remove(minTile);
                else
                    tileSet.remove(maxTile);

                tileSet.add(newTile);
            }
        }
    }

    public int size() {
        return tiles.length;
    }

    public Set<Set<Tile>> getSets(){
        return new HashSet<Set<Tile>>(sets);
    }

    public int countSets() {
        return TileSet.countSets(tiles);
    }

    public Tile getTile(int index) {
        if (index < 0 || index >= TILES) {
            throw new IllegalArgumentException("Index out of bounds.");
        }

        return tiles[index];
    }

    @Override
    public String toString() {
        String[] tileStrings = new String[TILES];
        for (int i = 0; i < TILES; i++)
            tileStrings[i] = tiles[i].toString();

        Arrays.sort(tileStrings);

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : tileStrings) {
            if (stringBuilder.length() > 0)
                stringBuilder.append(",");
            stringBuilder.append(s);
        }

        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof Board))
            return false;

        Board other = (Board)o;

        Set<Tile> theseTiles = new HashSet<Tile>(Arrays.asList(tiles));
        Set<Tile> otherTiles = new HashSet<Tile>(Arrays.asList(other.tiles));

        return theseTiles.equals(otherTiles);
    }
}
