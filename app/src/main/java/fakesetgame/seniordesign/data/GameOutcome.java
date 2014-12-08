package fakesetgame.seniordesign.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.model.Game;

/**
 * A class to hold information retrieved from the database about a game outcome.
 */
public class GameOutcome {
    private final long _id;
    private final Board board;
    private final long elapsed;
    private final Date inserted;
    private final boolean hintUsed;
    private final Game.GameType mode;
    private final Game.Outcome outcome;
    private final List<FoundSetRecord> foundSetRecordList;

    private GameOutcome(long _id, String board, long elapsed, long inserted, boolean hintUsed, String mode, String outcome, List<FoundSetRecord> foundSets) {
        this._id = _id;
        this.board = Board.fromString(board);
        this.elapsed = elapsed;
        this.inserted = new Date(inserted);
        this.hintUsed = hintUsed;
        this.mode = Game.GameType.valueOf(mode);
        this.outcome = Game.Outcome.valueOf(outcome);
        this.foundSetRecordList = new ArrayList<FoundSetRecord>(foundSets);
    }

    public long getId() {
        return _id;
    }

    public Board getBoard() {
        return board;
    }

    public long getElapsed() {
        return elapsed;
    }

    public Date getInserted() {
        return inserted;
    }

    public boolean wasHintUsed() {
        return hintUsed;
    }

    public Game.GameType getMode() {
        return mode;
    }

    public Game.Outcome getOutcome() {
        return outcome;
    }

    public List<FoundSetRecord> getFoundSetList() {
        return new ArrayList<FoundSetRecord>(foundSetRecordList);
    }

    public static GameOutcome fromCursor(Context context, Cursor c) {

        long outcomeId = c.getLong(c.getColumnIndexOrThrow(TableDef._ID));

        return new GameOutcome(
                outcomeId,
                c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_BOARD)),
                c.getLong(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_ELAPSED)),
                c.getLong(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_INSERTED)),
                c.getInt(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_HINT)) == 1,
                c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_MODE)),
                c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_OUTCOME)),
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
        public static final String COLUMN_NAME_HINT = "hint";
        public static final String COLUMN_NAME_MODE = "mode";
        public static final String COLUMN_NAME_OUTCOME = "outcome";

        public static final String[] ALL_COLUMNS = {
                _ID,
                COLUMN_NAME_BOARD,
                COLUMN_NAME_ELAPSED,
                COLUMN_NAME_INSERTED,
                COLUMN_NAME_HINT,
                COLUMN_NAME_MODE,
                COLUMN_NAME_OUTCOME
        };
    }
}
