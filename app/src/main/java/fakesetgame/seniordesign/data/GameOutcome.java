package fakesetgame.seniordesign.data;

import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.Date;

import fakesetgame.seniordesign.model.Board;

/**
 * Created by Chris on 10/17/2014.
 */
public class GameOutcome {
    public final long _id;
    public final Board board;
    public final long elapsed;
    public final Date inserted;

    public GameOutcome(long _id, String board, long elapsed, long inserted){
        this._id = _id;
        this.board = Board.fromString(board);
        this.elapsed = elapsed;
        this.inserted = new Date(inserted);
    }

    public static GameOutcome fromCursor(Cursor c){
        return new GameOutcome(
                c.getLong(c.getColumnIndexOrThrow(Columns._ID)),
                c.getString(c.getColumnIndexOrThrow(Columns.COLUMN_NAME_BOARD)),
                c.getLong(c.getColumnIndexOrThrow(Columns.COLUMN_NAME_ELAPSED)),
                c.getLong(c.getColumnIndexOrThrow(Columns.COLUMN_NAME_INSERTED))
        );
    }

    /**
     * Defines the game outcomes table
     */
    public static abstract class Columns implements BaseColumns {

        public static final String TABLE_NAME = "outcome";

        public static final String COLUMN_NAME_BOARD = "board";
        public static final String COLUMN_NAME_ELAPSED = "elapsed";
        public static final String COLUMN_NAME_INSERTED = "inserted";

        public static final String[] ALL_COLUMNS = {
                Columns._ID,
                Columns.COLUMN_NAME_BOARD,
                Columns.COLUMN_NAME_ELAPSED,
                Columns.COLUMN_NAME_INSERTED,
        };
    }
}
