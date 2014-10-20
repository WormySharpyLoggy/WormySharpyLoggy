package fakesetgame.seniordesign.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import fakesetgame.seniordesign.model.Game;
import fakesetgame.seniordesign.model.Tile;

/**
 * Created by Chris on 10/17/2014.
 */
public class PlayerDataDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "PlayerData.db";
    public static final String TAG = "PlayerDataDbHelper";

    private static final String[] SQL_CREATE_ENTRIES = new String[]{
            "CREATE TABLE " + GameOutcome.TableDef.TABLE_NAME + " (" +
                    GameOutcome.TableDef._ID + " INTEGER PRIMARY KEY, " +
                    GameOutcome.TableDef.COLUMN_NAME_BOARD + " TEXT, " +
                    GameOutcome.TableDef.COLUMN_NAME_ELAPSED + " INTEGER, " +
                    GameOutcome.TableDef.COLUMN_NAME_INSERTED + " INTEGER" +
                    " )",
            "CREATE TABLE " + FoundSetRecord.TableDef.TABLE_NAME + " (" +
                    FoundSetRecord.TableDef._ID + " INTEGER PRIMARY KEY, " +
                    FoundSetRecord.TableDef.COLUMN_NAME_OUTCOME + " INTEGER, " +
                    FoundSetRecord.TableDef.COLUMN_NAME_TILES + " TEXT, " +
                    FoundSetRecord.TableDef.COLUMN_NAME_ELAPSED + " INTEGER, " +
                    FoundSetRecord.TableDef.COLUMN_NAME_DELTA + " INTEGER, " +
                    FoundSetRecord.TableDef.COLUMN_NAME_INSERTED + " INTEGER, " +
                    "FOREIGN KEY (" + FoundSetRecord.TableDef.COLUMN_NAME_OUTCOME + ") " +
                    "REFERENCES " + GameOutcome.TableDef.TABLE_NAME + " (" + GameOutcome.TableDef._ID + "))"
    };

    private static final String[] SQL_DELETE_ENTRIES = new String[]{
            "DROP TABLE IF EXISTS " + GameOutcome.TableDef.TABLE_NAME,
            "DROP TABLE IF EXISTS " + FoundSetRecord.TableDef.TABLE_NAME
    };

    public PlayerDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        for(String createScript: SQL_CREATE_ENTRIES) {
            Log.d(TAG, "onCreate():\n" + createScript);
            db.execSQL(createScript);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            default:
                for(String deleteScript: SQL_DELETE_ENTRIES){
                    Log.d(TAG, "onUpgrade():\n" + deleteScript);
                    db.execSQL(deleteScript);
                }
                onCreate(db);
                break;
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(String deleteScript: SQL_DELETE_ENTRIES){
            Log.d(TAG, "onDowngrade():\n" + deleteScript);
            db.execSQL(deleteScript);
        }
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
        values.put(GameOutcome.TableDef.COLUMN_NAME_BOARD, game.board.toString());
        values.put(GameOutcome.TableDef.COLUMN_NAME_ELAPSED, game.getElapsedTime());
        values.put(GameOutcome.TableDef.COLUMN_NAME_INSERTED, new Date().getTime());

        // Insert the new row, returning the primary key value of the new row
        long outcomeId = db.insert(
                GameOutcome.TableDef.TABLE_NAME,
                null,
                values);

        // Insert the FoundSetRecord values
        for (Game.FoundSet found : game.getFoundSetList()) {

            Tile[] tiles = found.getTileSet().toArray(new Tile[Game.TILES_IN_A_SET]);
            String[] tileStrings = new String[tiles.length];
            for (int i = 0; i < tileStrings.length; i++)
                tileStrings[i] = tiles[i].toString();
            Arrays.sort(tileStrings);
            StringBuilder sb = new StringBuilder();
            for (String tile : tileStrings) {
                if (sb.length() != 0)
                    sb.append(",");
                sb.append(tile);
            }

            values = new ContentValues();
            values.put(FoundSetRecord.TableDef.COLUMN_NAME_OUTCOME, outcomeId);
            values.put(FoundSetRecord.TableDef.COLUMN_NAME_TILES, sb.toString());
            values.put(FoundSetRecord.TableDef.COLUMN_NAME_ELAPSED, found.getTotalElapsed());
            values.put(FoundSetRecord.TableDef.COLUMN_NAME_DELTA, found.getDeltaElapsed());
            values.put(FoundSetRecord.TableDef.COLUMN_NAME_INSERTED, new Date().getTime());

            db.insert(
                    FoundSetRecord.TableDef.TABLE_NAME,
                    null,
                    values);
        }

        return outcomeId;
    }

    public static List<GameOutcome> getLastOutcomes(Context context, int count) {
        InstantiateHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = GameOutcome.TableDef.ALL_COLUMNS;

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                GameOutcome.TableDef.COLUMN_NAME_INSERTED + " DESC";

        Cursor c = db.query(
                GameOutcome.TableDef.TABLE_NAME,    // The table to query
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
                    GameOutcome.fromCursor(context, c)
            );
        }

        return outcomes;
    }

    public static GameOutcome getOutcomeByID(Context context, long id) {
        InstantiateHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = GameOutcome.TableDef.ALL_COLUMNS;

        Cursor c = db.query(
                GameOutcome.TableDef.TABLE_NAME,        // The table to query
                projection,                         // The columns to return
                GameOutcome.TableDef._ID + "=?",        // The columns for the WHERE clause
                new String[]{String.valueOf(id)},   // The values for the WHERE clause
                null,                               // don't group the rows
                null,                               // don't filter by row groups
                null                                // The sort order
        );

        if (c.moveToFirst()) {
            return GameOutcome.fromCursor(context, c);
        }

        return null;
    }

    public static List<FoundSetRecord> getFoundSetsByGameOutcome(Context context, long outcome) {
        InstantiateHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = FoundSetRecord.TableDef.ALL_COLUMNS;

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                FoundSetRecord.TableDef._ID + " ASC";

        Cursor c = db.query(
                FoundSetRecord.TableDef.TABLE_NAME,    // The table to query
                projection, // The columns to return
                FoundSetRecord.TableDef.COLUMN_NAME_OUTCOME + "=?",       // The columns for the WHERE clause
                new String[]{String.valueOf(outcome)},       // The values for the WHERE clause
                null,       // don't group the rows
                null,       // don't filter by row groups
                sortOrder   // The sort order
        );

        List<FoundSetRecord> foundSets = new ArrayList<FoundSetRecord>();
        while (c.moveToNext()) {
            foundSets.add(
                    FoundSetRecord.fromCursor(c)
            );
        }

        return foundSets;
    }
}
