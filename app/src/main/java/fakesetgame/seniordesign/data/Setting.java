package fakesetgame.seniordesign.data;

import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Created by Chris on 10/27/2014.
 */
public class Setting {

    private final long _id;
    private final String name;
    private final String value;

    private Setting(long _id, String name, String value) {
        this._id = _id;
        this.name = name;
        this.value = value;
    }

    public long getId() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static Setting fromCursor(Cursor c) {

        return new Setting(
                c.getLong(c.getColumnIndexOrThrow(TableDef._ID)),
                c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_NAME)),
                c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_VALUE))
        );
    }

    /**
     * Defines the found set table
     */
    public static abstract class TableDef implements BaseColumns {

        public static final String TABLE_NAME = "settings";

        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_VALUE = "value";

        public static final String[] ALL_COLUMNS = {
                TableDef._ID,
                TableDef.COLUMN_NAME_NAME,
                TableDef.COLUMN_NAME_VALUE,
        };
    }
}
