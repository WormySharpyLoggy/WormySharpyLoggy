/*
 * Copyright (C) 2014 Chris St. James. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mischivous.wormysharpyloggy.wsl.data.GameOutcome;
import com.mischivous.wormysharpyloggy.wsl.data.GameSummaryListItemCursorAdapter;
import com.mischivous.wormysharpyloggy.wsl.data.PlayerDataDbHelper;
import com.mischivous.wormysharpyloggy.wsl.model.GameResult;
import com.mischivous.wormysharpyloggy.wsl.model.GameType;

/**
 * This is the Activity class for the overlay screen
 * that is shown to the user at the conclusion of a game.
 *
 * @author Chris St. James
 * @version 1.0
 * @since June 30, 2015
 */
public class SummaryScreen extends Activity {

	private GameType mode;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summary_layout);
		long lastGameID = getIntent().getLongExtra("lastGame", 0);

		GameOutcome lastGame = PlayerDataDbHelper.getOutcome(this, lastGameID);
		mode = lastGame.getMode();
		long lastGameTime = lastGame.getElapsed() / 1000;

		TextView elapsed = (TextView) findViewById(R.id.elapsedTime);
		if(lastGame.getOutcome() == GameResult.Win)
			elapsed.setText(String.format("%d:%02d", lastGameTime / 60, lastGameTime % 60));
		else elapsed.setText(":(");

		GameSummaryListItemCursorAdapter adapter = new GameSummaryListItemCursorAdapter(this, PlayerDataDbHelper.getBestOutcomes(this, lastGame.getMode(), 5, lastGame.wasHintUsed()), 0);
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(adapter);

		if(lastGame.getOutcome() == GameResult.Win)
			messageUser(getResources().getString(lastGame.wasHintUsed() ? R.string.hintUsed : R.string.hintNotUsed));
		else messageUser(getResources().getString(R.string.lostGame));
	}

	/**
	 * Displays a message to the user in a Toast.
	 *
	 * @param msg The message to display
	 */
	private void messageUser(String msg) {
		Toast toast = Toast.makeText(getApplicationContext(), msg,
				(msg.length() > 24 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT));
		toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
		}

	/**
	 * Starts a new Game of the same game type.
	 *
	 * @param v The button that was clicked
	 */
	public void StartNewGame(View v) {
		Intent i = new Intent(this, GameScreen.class);
		i.putExtra("type", mode);
		startActivity(i);
		}

	/**
	 * Returns the user to the main menu after completing a Game.
	 *
	 * @param v The button that was clicked
	 */
	public void MainMenu(View v) {
		finish();
	}
}
