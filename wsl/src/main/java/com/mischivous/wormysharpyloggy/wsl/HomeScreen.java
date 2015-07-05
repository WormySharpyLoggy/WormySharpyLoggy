/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import com.mischivous.wormysharpyloggy.wsl.model.GameType;

/**
 * The home screen of the game, which allows players to start a new
 * game and change game settings for a more enjoyable experience.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 28, 2015
 */
public class HomeScreen extends Activity {
	/**
	 * {@inheritDoc}
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
        }

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onNewIntent(Intent i) { setContentView(R.layout.home_layout); }

	/**
	 * Sends the user to the main game screen to start a new game,
	 * with the type chosen by the button the user pressed.
	 *
	 * @param v The button the user pressed
	 */
	public void StartGame(@NonNull View v) {
		if (v == null) {throw new NullPointerException("Button clicked cannot be null."); }

		Intent i = new Intent(HomeScreen.this, GameScreen.class);

		switch (v.getId()) {
			case R.id.normalButton:
				i.putExtra("type", GameType.Normal);
				break;
			case R.id.taButton:
				i.putExtra("type", GameType.TimeAttack);
				break;
			case R.id.powerButton:
				i.putExtra("type", GameType.PowerSet);
				break;
			default: break;
			}
		startActivity(i);
		}

	/**
	 * Sends user to the Settings screen to change game options.
	 *
	 * @param v The View calling the function
	 */
    public void GotoSettings(@Nullable View v) {
	    Intent i = new Intent(this, SettingsScreen.class);
	    startActivity(i);
        }

	/**
	 * Sends user to the instruction screen to learn how the different
	 * game modes function.
	 *
	 * @param v The View calling the function
	 */
	public void GotoInstructions(@Nullable View v) {
		Intent i = new Intent(this, InstructionsScreen.class);
		startActivity(i);
		}
}
