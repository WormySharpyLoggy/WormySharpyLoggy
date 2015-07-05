/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.mischivous.wormysharpyloggy.wsl.model.Tile;

/**
 * This is the Activity class for the splash screen,
 * which displays a brief graphic on app startup.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 29, 2015
 */
public class SplashScreen extends Activity {

	private static boolean active = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final int TIMEOUT = 1500;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_layout);

		// Randomize the splash image; take this out if I ever decide
		// to create a logo.
		Tile splash = Tile.GetRandomTiles(1)[0];
		ImageView iv = (ImageView) findViewById(R.id.imageSplash);
		iv.setImageDrawable(splash.GetDrawable(getApplicationContext()));

		// Create a timer to redirect to main screen.
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// Start main activity.
				if(active) {
					Intent i = new Intent(SplashScreen.this, HomeScreen.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					}
				// Close this activity.
				finish();
				}
			}, TIMEOUT);
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStart() {
		super.onStart();
		active = true;
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStop() {
		super.onStop();
		active = false;
		}
}
