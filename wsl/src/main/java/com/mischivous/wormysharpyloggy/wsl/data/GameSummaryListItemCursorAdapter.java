/*
 * Copyright (C) 2014 Chris St. James. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.mischivous.wormysharpyloggy.wsl.R;

import java.text.DateFormat;

/**
 * A class to shove game outcome information into summary view rows.
 *
 * @author Chris St. James
 * @version 1.0
 * @since June 30, 2015
 */
public class GameSummaryListItemCursorAdapter extends CursorAdapter {
	private LayoutInflater mInflater;

	public GameSummaryListItemCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {

		if (cursor.getPosition() % 2 == 1) {
			view.setBackgroundColor(context.getResources().getColor(R.color.background_odd));
		} else {
			view.setBackgroundColor(context.getResources().getColor(R.color.background_even));
		}

		TextView rankView = (TextView) view.findViewById(R.id.rank);
		TextView firstLineView = (TextView) view.findViewById(R.id.firstLine);
		TextView secondLineView = (TextView) view.findViewById(R.id.secondLine);

		int rank = cursor.getPosition() + 1;
		GameOutcome outcome = GameOutcome.fromCursor(context, cursor);
		rankView.setText(Integer.valueOf(rank).toString());
		long seconds = outcome.getElapsed() / 1000;
		int setsFound = outcome.getSets();
		double difficulty = outcome.getBoard().GetDifficulty();
		firstLineView.setText(String.format("Time: %d:%02d | Sets: %d | Dif.: %.2f%s",
				seconds / 60,
				seconds % 60,
				setsFound,
				difficulty,
				outcome.wasHintUsed()? " | CHEATER" : ""));
		secondLineView.setText(DateFormat.getDateTimeInstance().format(outcome.getInserted()));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.game_summary_list_item_view, parent, false);
	}
}
