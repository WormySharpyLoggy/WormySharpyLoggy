package fakesetgame.seniordesign.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fakesetgame.seniordesign.model.Board;

/**
 * Created by Chris on 10/17/2014.
 */
public class GameOutcome {
    public final long _id;
    public final Board board;
    public final long elapsed;
    public final Date inserted;
    private final List<FoundSetRecord> foundSetRecordList;

    private GameOutcome(long _id, String board, long elapsed, long inserted, List<FoundSetRecord> foundSets){
        this._id = _id;
        this.board = Board.fromString(board);
        this.elapsed = elapsed;
        this.inserted = new Date(inserted);
        this.foundSetRecordList = new ArrayList<FoundSetRecord>(foundSets);
    }

    public List<FoundSetRecord> getFoundSetList(){
        return new ArrayList<FoundSetRecord>(foundSetRecordList);
    }

    public static GameOutcome fromCursor(Context context, Cursor c){

        long outcomeId = c.getLong(c.getColumnIndexOrThrow(TableDef._ID));

        return new GameOutcome(
                outcomeId,
                c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_BOARD)),
                c.getLong(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_ELAPSED)),
                c.getLong(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_INSERTED)),
                PlayerDataDbHelper.getFoundSetsByGameOutcome(context, outcomeId)
        );
    }

    /**
     * Defines the game outcomes table
     */
    public static abstract class TableDef implements BaseColumns {

        public static final String TABLE_NAME = "outcome";

        public static final String COLUMN_NAME_BOARD = "board";
        public static final String COLUMN_NAME_ELAPSED = "elapsed";
        public static final String COLUMN_NAME_INSERTED = "inserted";

        public static final String[] ALL_COLUMNS = {
                TableDef._ID,
                TableDef.COLUMN_NAME_BOARD,
                TableDef.COLUMN_NAME_ELAPSED,
                TableDef.COLUMN_NAME_INSERTED,
        };
    }
}
