/*
 * Copyright (C) 2014 Chris St. James. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.mischivous.wormysharpyloggy.wsl.model.Game;
import com.mischivous.wormysharpyloggy.wsl.model.GameResult;
import com.mischivous.wormysharpyloggy.wsl.model.GameType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Handles CRUD operations on database, database version upgrades.
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
					GameOutcome.TableDef.COLUMN_NAME_SETS + " INTEGER, " +
					GameOutcome.TableDef.COLUMN_NAME_BOARD_GENS + " INTEGER, " +
					GameOutcome.TableDef.COLUMN_NAME_ELAPSED + " INTEGER, " +
					GameOutcome.TableDef.COLUMN_NAME_INSERTED + " INTEGER, " +
					GameOutcome.TableDef.COLUMN_NAME_HINT + " INTEGER, " +
					GameOutcome.TableDef.COLUMN_NAME_MODE + " TEXT, " +
					GameOutcome.TableDef.COLUMN_NAME_OUTCOME + " TEXT" +
					" );\n" +
					"CREATE INDEX " + GameOutcome.TableDef.TABLE_NAME + "_" +
					GameOutcome.TableDef.COLUMN_NAME_MODE + "_" +
					GameOutcome.TableDef.COLUMN_NAME_ELAPSED + "_idx ON" +
					GameOutcome.TableDef.TABLE_NAME + " (" +
					GameOutcome.TableDef.COLUMN_NAME_MODE + " ASC, " +
					GameOutcome.TableDef.COLUMN_NAME_ELAPSED + " ASC);",
			"CREATE TABLE " + FoundSetRecord.TableDef.TABLE_NAME + " (" +
					FoundSetRecord.TableDef._ID + " INTEGER PRIMARY KEY, " +
					FoundSetRecord.TableDef.COLUMN_NAME_OUTCOME + " INTEGER, " +
					FoundSetRecord.TableDef.COLUMN_NAME_TILES + " TEXT, " +
					FoundSetRecord.TableDef.COLUMN_NAME_ELAPSED + " INTEGER, " +
					FoundSetRecord.TableDef.COLUMN_NAME_DELTA + " INTEGER, " +
					FoundSetRecord.TableDef.COLUMN_NAME_HINT + " INTEGER," +
					FoundSetRecord.TableDef.COLUMN_NAME_INSERTED + " INTEGER, " +
					"FOREIGN KEY (" + FoundSetRecord.TableDef.COLUMN_NAME_OUTCOME + ") " +
					"REFERENCES " + GameOutcome.TableDef.TABLE_NAME + " (" + GameOutcome.TableDef._ID + "));" +
					"CREATE INDEX " + FoundSetRecord.TableDef.TABLE_NAME + "_" +
					FoundSetRecord.TableDef.COLUMN_NAME_OUTCOME + "_idx ON " +
					FoundSetRecord.TableDef.TABLE_NAME + " (" + FoundSetRecord.TableDef.COLUMN_NAME_OUTCOME + " ASC);",
	};

	private static final String[] SQL_DELETE_ENTRIES = new String[]{
			"DROP TABLE IF EXISTS " + GameOutcome.TableDef.TABLE_NAME,
			"DROP TABLE IF EXISTS " + FoundSetRecord.TableDef.TABLE_NAME,
	};

	public PlayerDataDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		for (String createScript : SQL_CREATE_ENTRIES) {
			Log.d(TAG, "onCreate():\n" + createScript);
			db.execSQL(createScript);
		}
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (newVersion) {
			default:
				for (String deleteScript : SQL_DELETE_ENTRIES) {
					Log.d(TAG, "onUpgrade():\n" + deleteScript);
					db.execSQL(deleteScript);
				}
				onCreate(db);
				break;
		}
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (String deleteScript : SQL_DELETE_ENTRIES) {
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

	/**
	 * Saves one game outcome to the database.
	 * @param context
	 * @param game
	 * @return
	 */
	public static long saveOutcome(Context context, Game game) {
		InstantiateHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(GameOutcome.TableDef.COLUMN_NAME_BOARD, game.GetBoardString());
		values.put(GameOutcome.TableDef.COLUMN_NAME_SETS, game.GetFullSetCount());
		values.put(GameOutcome.TableDef.COLUMN_NAME_ELAPSED, game.GetElapsedTime());
		values.put(GameOutcome.TableDef.COLUMN_NAME_INSERTED, new Date().getTime());
		values.put(GameOutcome.TableDef.COLUMN_NAME_HINT, game.WereHintsUsed());
		values.put(GameOutcome.TableDef.COLUMN_NAME_MODE, game.GetGameType().toString());
		values.put(GameOutcome.TableDef.COLUMN_NAME_OUTCOME, game.GetGameResult().toString());

		// Insert the new row, returning the primary key value of the new row
		long outcomeId = db.insert(
				GameOutcome.TableDef.TABLE_NAME,
				null,
				values);

		/* This class was written for an earlier version of the game,
		   which had a different underlying design and did not have
		   the PowerSet variant, thus this code is not accurate for some
		   games; when I get time, I'll try to rejigger it to work
		   properly again.

		   // TODO: Allow for storage of PowerSets in the database
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
			values.put(FoundSetRecord.TableDef.COLUMN_NAME_HINT, found.wasHintProvided());
			values.put(FoundSetRecord.TableDef.COLUMN_NAME_INSERTED, new Date().getTime());

			db.insert(
					FoundSetRecord.TableDef.TABLE_NAME,
					null,
					values);
		}*/

		return outcomeId;
	}

	/**
	 * Gets a Cursor over outcomes given a sort order and filters.
	 * @param context
	 * @param rows
	 * @param sortOrder
	 * @param where
	 * @param whereArgs
	 * @return
	 */
	private static Cursor getTopOutcomes(Context context, int rows, String sortOrder, String where, String[] whereArgs) {
		InstantiateHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = GameOutcome.TableDef.ALL_COLUMNS;

		return db.query(
				GameOutcome.TableDef.TABLE_NAME,    // The table to query
				projection, // The columns to return
				where,       // The columns for the WHERE clause
				whereArgs,       // The values for the WHERE clause
				null,       // don't group the rows
				null,       // don't filter by row groups
				sortOrder,   // The sort order
				Integer.valueOf(rows).toString()
		);
	}

	/**
	 * Gets a Cursor over the most recently recorded game outcomes.
	 * @param context
	 * @param mode
	 * @param rows
	 * @return
	 */
	public static Cursor getLastOutcomes(Context context, GameType mode, int rows) {
		return getTopOutcomes(
				context,
				rows,
				GameOutcome.TableDef.COLUMN_NAME_INSERTED + " DESC",
				GameOutcome.TableDef.COLUMN_NAME_MODE + "=?",
				new String[]{mode.toString()}
		);
	}

	/**
	 * Gets a Cursor over the top games (quickest solve time)
	 * @param context
	 * @param mode
	 * @param rows
	 * @param showGamesWithHints
	 * @return
	 */
	public static Cursor getBestOutcomes(Context context, GameType mode, int rows, boolean showGamesWithHints) {

		List<String> where = new ArrayList<String>();
		List<String> whereArgs = new ArrayList<String>();

		where.add(GameOutcome.TableDef.COLUMN_NAME_MODE + "=?");
		whereArgs.add(mode.toString());

		where.add(GameOutcome.TableDef.COLUMN_NAME_OUTCOME + "=?");
		whereArgs.add(GameResult.Win.toString());

		if (!showGamesWithHints) {
			where.add(GameOutcome.TableDef.COLUMN_NAME_HINT + "=?");
			whereArgs.add("0");
		}

		StringBuilder whereSB = new StringBuilder();
		for (int i = 0; i < where.size(); ++i) {
			if (i > 0)
				whereSB.append(" and ");
			whereSB.append(where.get(i));
		}

		return getTopOutcomes(context, rows, GameOutcome.TableDef.COLUMN_NAME_ELAPSED + " ASC", whereSB.toString(), whereArgs.toArray(new String[whereArgs.size()]));
	}

	/**
	 * Gets the details of a particular game outcome by id.
	 * @param context
	 * @param id
	 * @return
	 */
	public static GameOutcome getOutcome(Context context, long id) {
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

	/**
	 * Gets a list of sets found during a game, from the saved outcome.
	 * @param context
	 * @param outcome
	 * @return
	 */
	public static List<FoundSetRecord> getFoundSetsByGameOutcome(Context context, long outcome) {
		InstantiateHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = FoundSetRecord.TableDef.ALL_COLUMNS;

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
				FoundSetRecord.TableDef.COLUMN_NAME_ELAPSED + " ASC";

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
