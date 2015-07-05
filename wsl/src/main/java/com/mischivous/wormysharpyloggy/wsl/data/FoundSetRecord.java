/*
 * Copyright (C) 2014 Chris St. James. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.data;

import android.database.Cursor;
import android.provider.BaseColumns;
import com.mischivous.wormysharpyloggy.wsl.model.Tile;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A class to hold information retrieved from the database about sets found during a game.
 *
 * @author Chris St. James
 * @version 1.0
 * @since June 30, 2015
 */
public class FoundSetRecord {
	private final long _id;
	private final Set<Tile> tileSet;
	private final long totalElapsed;
	private final long deltaElapsed;
	private final boolean hintProvided;

	public Set<Tile> getTileSet() {
		return tileSet;
	}

	public long getId() {
		return _id;
	}

	public long getTotalElapsed() {
		return totalElapsed;
	}

	public long getDeltaElapsed() {
		return deltaElapsed;
	}

	public boolean wasHintProvided() {
		return hintProvided;
	}

	private FoundSetRecord(long _id, Collection<Tile> tileSet, long totalElapsed, long deltaElapsed, boolean hintProvided) {
		this._id = _id;
		this.tileSet = new HashSet<>(tileSet);
		this.totalElapsed = totalElapsed;
		this.deltaElapsed = deltaElapsed;
		this.hintProvided = hintProvided;
	}

	public static FoundSetRecord fromCursor(Cursor c) {

		String tilesString = c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_TILES));
		String[] tilesStrings = tilesString.split(",");
		Tile[] tiles = new Tile[tilesStrings.length];
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = Tile.fromString(tilesStrings[i]);


		return new FoundSetRecord(
				c.getLong(c.getColumnIndexOrThrow(TableDef._ID)),
				Arrays.asList(tiles),
				c.getLong(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_ELAPSED)),
				c.getLong(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_DELTA)),
				c.getInt(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_HINT)) == 1
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
		public static final String COLUMN_NAME_HINT = "hint";
		public static final String COLUMN_NAME_INSERTED = "inserted";

		public static final String[] ALL_COLUMNS = {
				TableDef._ID,
				TableDef.COLUMN_NAME_OUTCOME,
				TableDef.COLUMN_NAME_TILES,
				TableDef.COLUMN_NAME_ELAPSED,
				TableDef.COLUMN_NAME_DELTA,
				TableDef.COLUMN_NAME_HINT,
				TableDef.COLUMN_NAME_INSERTED,
		};
	}
}
