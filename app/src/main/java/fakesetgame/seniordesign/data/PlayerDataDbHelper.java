package fakesetgame.seniordesign.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fakesetgame.seniordesign.model.Game;

/**
 * Created by Chris on 10/17/2014.
 */
public class PlayerDataDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PlayerData.db";
    public static final String TAG = "PlayerDataDbHelper";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GameOutcome.Columns.TABLE_NAME + " (" +
                    GameOutcome.Columns._ID + " INTEGER PRIMARY KEY," +
                    GameOutcome.Columns.COLUMN_NAME_BOARD + " TEXT," +
                    GameOutcome.Columns.COLUMN_NAME_ELAPSED + " INTEGER," +
                    GameOutcome.Columns.COLUMN_NAME_INSERTED + " INTEGER" +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GameOutcome.Columns.TABLE_NAME;

    public PlayerDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate():\n" + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            default:
                Log.d(TAG, "onUpgrade():\n" + SQL_DELETE_ENTRIES);
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
                break;
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onDowngrade():\n" + SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public static PlayerDataDbHelper helper = null;

    public static void InstantiateHelper(Context context) {
        if (helper == null)
            helper = new PlayerDataDbHelper(context);
    }

    public static long saveOutcome(Context context, Game game) {
        InstantiateHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(GameOutcome.Columns.COLUMN_NAME_BOARD, game.board.toString());
        values.put(GameOutcome.Columns.COLUMN_NAME_ELAPSED, game.getElapsedTime());
        values.put(GameOutcome.Columns.COLUMN_NAME_INSERTED, new Date().getTime());

        // Insert the new row, returning the primary key value of the new row
        return db.insert(
                GameOutcome.Columns.TABLE_NAME,
                null,
                values);
    }

    public static List<GameOutcome> getLastOutcomes(Context context, int count) {
        InstantiateHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = GameOutcome.Columns.ALL_COLUMNS;

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                GameOutcome.Columns.COLUMN_NAME_INSERTED + " DESC";

        Cursor c = db.query(
                GameOutcome.Columns.TABLE_NAME,    // The table to query
                projection, // The columns to return
                null,       // The columns for the WHERE clause
                null,       // The values for the WHERE clause
                null,       // don't group the rows
                null,       // don't filter by row groups
                sortOrder   // The sort order
        );

        List<GameOutcome> outcomes = new ArrayList<GameOutcome>();
        while (c.moveToNext() && count-- > 0) {
            outcomes.add(
                    GameOutcome.fromCursor(c)
            );
        }

        return outcomes;
    }

    public static GameOutcome getOutcomeByID(Context context, long id) {
        InstantiateHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = GameOutcome.Columns.ALL_COLUMNS;

        Cursor c = db.query(
                GameOutcome.Columns.TABLE_NAME,        // The table to query
                projection,                         // The columns to return
                GameOutcome.Columns._ID + "=?",        // The columns for the WHERE clause
                new String[]{String.valueOf(id)},   // The values for the WHERE clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                null                                // The sort order
        );

        List<GameOutcome> outcomes = new ArrayList<GameOutcome>();
        if (c.moveToFirst()) {
            return GameOutcome.fromCursor(c);
        }

        return null;
    }
}
