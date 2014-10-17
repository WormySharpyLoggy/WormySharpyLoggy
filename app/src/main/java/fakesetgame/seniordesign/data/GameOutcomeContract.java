package fakesetgame.seniordesign.data;

import android.provider.BaseColumns;

/**
 * Created by Chris on 10/17/2014.
 */
public final class GameOutcomeContract {

    public static abstract class GameOutcomeEntry implements BaseColumns {
        public static final String TABLE_NAME = "outcome";
        public static final String COLUMN_NAME_BOARD = "board";
        public static final String COLUMN_NAME_ELAPSED = "elapsed";
        public static final String COLUMN_NAME_INSERTED = "inserted";
    }
}
