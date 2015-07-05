/*
 * Copyright (C) 2014 Chris St. James. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.data;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import com.mischivous.wormysharpyloggy.wsl.model.Board;
import com.mischivous.wormysharpyloggy.wsl.model.GameResult;
import com.mischivous.wormysharpyloggy.wsl.model.GameType;

import java.util.Date;
import java.util.List;

/**
 * A class to hold information retrieved from the database about a game outcome.
 *
 * @author Chris St. James
 * @version 1.0
 * @since June 30, 2015
 */
public class GameOutcome {
	private final long _id;
	private final Board board;
	private final int sets;
	private final int gens;
	private final long elapsed;
	private final Date inserted;
	private final boolean hintUsed;
	private final GameType mode;
	private final GameResult outcome;

	private GameOutcome(long _id, String board, int sets, int gens, long elapsed, long inserted, boolean hintUsed, String mode, String outcome, List<FoundSetRecord> foundSets) {
		this._id = _id;
		this.board = Board.fromString(board);
		this.gens = gens;
		this.sets = sets;
		this.elapsed = elapsed;
		this.inserted = new Date(inserted);
		this.hintUsed = hintUsed;
		this.mode = GameType.valueOf(mode);
		this.outcome = GameResult.valueOf(outcome);
	}

	public long getId() { return _id; }

	public Board getBoard() {
		return board;
	}

	public int getSets() { return sets; }

	public long getElapsed() {
		return elapsed;
	}

	public Date getInserted() {
		return inserted;
	}

	public boolean wasHintUsed() {
		return hintUsed;
	}

	public GameType getMode() {
		return mode;
	}

	public GameResult getOutcome() { return outcome; }

	public static GameOutcome fromCursor(Context context, Cursor c) {

		long outcomeId = c.getLong(c.getColumnIndexOrThrow(TableDef._ID));

		return new GameOutcome(
				outcomeId,
				c.getString(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_BOARD)),
				c.getInt(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_SETS)),
				c.getInt(c.getColumnIndexOrThrow(TableDef.COLUMN_NAME_BOARD_GENS)),
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
		public static final String COLUMN_NAME_BOARD_GENS = "generations";
		public static final String COLUMN_NAME_SETS = "sets";
		public static final String COLUMN_NAME_ELAPSED = "elapsed";
		public static final String COLUMN_NAME_INSERTED = "inserted";
		public static final String COLUMN_NAME_HINT = "hint";
		public static final String COLUMN_NAME_MODE = "mode";
		public static final String COLUMN_NAME_OUTCOME = "outcome";

		public static final String[] ALL_COLUMNS = {
				_ID,
				COLUMN_NAME_BOARD,
				COLUMN_NAME_BOARD_GENS,
				COLUMN_NAME_SETS,
				COLUMN_NAME_ELAPSED,
				COLUMN_NAME_INSERTED,
				COLUMN_NAME_HINT,
				COLUMN_NAME_MODE,
				COLUMN_NAME_OUTCOME
		};
	}
}
