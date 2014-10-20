package fakesetgame.seniordesign.data;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import fakesetgame.seniordesign.model.Tile;

/**
 * Created by Chris on 10/20/2014.
 */
public class FoundSetRecord{
    private final Set<Tile> tileSet;
    private final long totalElapsed;
    private final long deltaElapsed;

    private FoundSetRecord(Collection<Tile> tileSet, long totalElapsed, long deltaElapsed){
        this.tileSet = new HashSet<Tile>(tileSet);
        this.totalElapsed = totalElapsed;
        this.deltaElapsed = deltaElapsed;
    }

    public static FoundSetRecord fromCursor(Cursor c){

        String tilesString = c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_TILES));
        String[] tilesStrings = tilesString.split(",");
        Tile[] tiles = new Tile[tilesStrings.length];
        for(int i=0; i<tiles.length; i++)
            tiles[i] = Tile.fromString(tilesStrings[i]);


        return new FoundSetRecord(
                Arrays.asList(tiles),
                c.getLong(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_ELAPSED)),
                c.getLong(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_DELTA))
        );
    }

    /**
     * Defines the found set table
     */
    public static abstract class TableDef implements BaseColumns {

        public static final String TABLE_NAME = "found_sets";

        public static final String COLUMN_NAME_OUTCOME = "outcome_id";
        public static final String COLUMN_NAME_TILES = "tiles";
        public static final String COLUMN_NAME_ELAPSED = "elapsed";
        public static final String COLUMN_NAME_DELTA = "delta";
        public static final String COLUMN_NAME_INSERTED = "inserted";

        public static final String[] ALL_COLUMNS = {
                TableDef._ID,
                TableDef.COLUMN_NAME_OUTCOME,
                TableDef.COLUMN_NAME_TILES,
                TableDef.COLUMN_NAME_ELAPSED,
                TableDef.COLUMN_NAME_DELTA,
                TableDef.COLUMN_NAME_INSERTED,
        };
    }
}
