/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.mischivous.wormysharpyloggy.wsl.data.OptionsHelper;
import com.mischivous.wormysharpyloggy.wsl.data.PlayerDataDbHelper;
import com.mischivous.wormysharpyloggy.wsl.model.Game;
import com.mischivous.wormysharpyloggy.wsl.model.GameOverEvent;
import com.mischivous.wormysharpyloggy.wsl.model.GameType;
import com.mischivous.wormysharpyloggy.wsl.model.Tile;
import com.mischivous.wormysharpyloggy.wsl.util.GameOverListener;
import com.mischivous.wormysharpyloggy.wsl.view.ShadedImageView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * The Activity that displays the main game screen during play
 * and handles game logic.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 30, 2015
 */
public class GameScreen extends Activity implements View.OnClickListener, GameOverListener {

	// Variables used by all game types.
	private static final String TAG = "GameScreen";
	private static final int boardSize = 9;
	private static final Handler handler = new Handler();
	private final ShadedImageView[] tiles = new ShadedImageView[boardSize];
	private TextView timeView;
	private Tile[] selected;
	private Game game;

	private final Runnable updateClock = new Runnable() {
		@Override
		public void run() {
			// Setup the time display for normal/Powerset game types
			if (game.GetGameType() == GameType.Normal
					|| game.GetGameType() == GameType.PowerSet) {
				long elapsedSeconds = game.GetElapsedTime() / 1000;
				timeView.setText(String.format("%d:%02d", elapsedSeconds / 60, elapsedSeconds % 60));
				// Setup the time display for time attack game type
				} else {
				long timeRemaining = game.GetTimeRemaining() / 1000;
				timeView.setText(String.format("%d:%02d", timeRemaining / 60, timeRemaining % 60));
				}
			}
		};


	// Variables used by normal/time attack types
	private ImageView[][] found;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Choose the appropriate layout for the game type.
		GameType type = (GameType) getIntent().getExtras().get("type");
		if (type == GameType.PowerSet) {setContentView(R.layout.game_power_layout);}
		else {setContentView(R.layout.game_standard_layout);}

		// Choose the correct number of tiles possible for selection
		// at one time.
		if (type == GameType.PowerSet) { selected = new Tile[4]; }
		else { selected = new Tile[3]; }


		// Initialize the ImageView for each Tile on the Board
		for (int i = 0; i < boardSize; i++) {
			int id = getResources().getIdentifier(
					String.format("Tile%d", i + 1),
					"id",
					getPackageName());

			tiles[i] = (ShadedImageView) findViewById(id);
			if (tiles[i] == null) {
				Log.e(TAG, String.format("Failed to set tiles[%d]. Value is null.", i));
				} else {
				tiles[i].setTag(R.id.TILE_INDEX, i);
				tiles[i].setOnClickListener(this);
				}
			}

		// Set up the area to store found Sets in normal/time attack
		// modes and initialize their ImageViews.
		if (type == GameType.Normal || type == GameType.TimeAttack) {
			found = new ImageView[OptionsHelper.GetSetCount(this)][3];

			for (int set = 0; set < found.length; set++) {
				for (int tile = 0; tile < found[set].length; tile++) {
					int id = getResources().getIdentifier(
							String.format("FoundSet%d_%d", set + 1, tile + 1),
							"id",
							getPackageName());

					found[set][tile] = (ImageView) findViewById(id);
					if (found[set][tile] == null) {
						Log.e(TAG, String.format("Failed to set found[%d][%d]. Value is null.", set, tile));
						}
					}
				}
			}

		StartGame(type);

		// Initialize the timer
		timeView = (TextView) findViewById(R.id.TimeView);
		Timer clockUpdateTimer = new Timer();
		clockUpdateTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.post(updateClock);
				}
			}, 0, 200);
		}

	/**
	 * Initialize a new Game instance of the selected game type.
	 *
	 * @param type The type of Game to initialize
	 */
	private void StartGame(@NonNull GameType type) {
		if (type == null) { throw new IllegalArgumentException("Game type cannot be null."); }

			game = new Game(type, OptionsHelper.GetSetCount(this),
				OptionsHelper.GetMinDiff(this, type),
				OptionsHelper.GetMaxDiff(this, type));

		game.AddGameOverListener(this);
		ClearTileSelection();

		for (int i = 0; i < tiles.length; i++) {
			Drawable d = game.GetTileAt(i).GetDrawable(this);
			if (d == null) {
				Log.e(TAG, String.format("Failed to get drawable for tile %d. Value is null.",
						i));
			} else {
				tiles[i].setImageDrawable(d);
				Log.d(TAG, String.format("Set tile image for tiles[%d]", i));
				}
			}

		// Initialize the blanks at the top for normal/time attack modes
		if (type == GameType.Normal || type == GameType.TimeAttack) {
			if (found.length > 3) {
				for (int set = 3; set < found.length; set++)
					for (int tile = 0; tile < found[set].length; tile++)
						found[set][tile].setImageDrawable(ResourcesCompat.getDrawable(getResources(),
								R.mipmap.tile_small_blank,
								null));
				}
			// Place the first join in the correct location
			} else {
			int id = getResources().getIdentifier(
					"joinImage",
					"id",
					getPackageName());

			ImageView iv = (ImageView) findViewById(id);
			if (iv == null) {Log.e(TAG, "Failed to set joinImage. Value is null.");}
			else {iv.setImageDrawable(game.GetNextJoin().GetDrawable(this));}
			}
		}

	/**
	 * Gets the selected state of a Tile, by its index.
	 * @param tileIndex The index of the Tile to check for selection
	 * @return Whether or not the Tile is shaded
	 */
	private boolean IsTileSelected(@IntRange(from = 0, to = 8) int tileIndex) {
		if (tileIndex < 0 || tileIndex >= boardSize) {
			throw new IllegalArgumentException("tileIndex out of bounds.");
		} else {return tiles[tileIndex].getShaded();}
	}

	/**
	 * Selects a Tile. If the correct number of Tiles have been selected,
	 * check to see if the player has found either a set or a PowerSet,
	 * depending on game mode.
	 *
	 * @param tileIndex The index of the Tile to select or deselect
	 * @param selected The state to set the Tile in
	 */
	private void SetTileSelected(@IntRange(from = 0, to = 8) int tileIndex, boolean selected) {
		if (tileIndex < 0 || tileIndex > boardSize - 1) { return; }
		else if (IsTileSelected(tileIndex) == selected) { return; }

		if (selected) {
			int selectedCount = 1;
			Tile t = game.GetTileAt(tileIndex);
			for (int i = 0; i < this.selected.length; i++) {
				if (this.selected[i] == null) {
					this.selected[i] = t;
					break;
				} else {
					selectedCount += 1;
				}
			}

			// If we've selected the correct number of Tiles to check
			// for Sets, check with Game class to see if the given
			// Set is good.
			if (game.GetGameType() == GameType.Normal
					|| game.GetGameType() == GameType.TimeAttack) {

				tiles[tileIndex].setShaded(selected);
				Log.d(TAG, String.format("Setting tile %d shading = %s", tileIndex, Boolean.valueOf(selected).toString()));

				if (selectedCount == 3) {
					// Check if the selected set was found already
					if (game.WasFoundAlready(this.selected)) {
						messageUser(getString(R.string.foundSetAlready));

						}
					// If it wasn't, make sure it's a valid set
					else if (game.IsValidSet(this.selected)) {
						messageUser(getString(R.string.foundSet));

						// Place the found set up top
						int set = game.GetFoundSetCount() - 1;
						for (int i = 0; i < 3; i++) {
							found[set][i].setImageDrawable(this.selected[i].GetSmallDrawable(this));
							}

						// If it wasn't a valid set, inform the player
						} else {
							messageUser(getString(R.string.badSet));
							}
					ClearTileSelection();
					}
			// If we've selected the correct number of Tiles to check
			// for Powersets, check with Game class to see if the given
			// Sets are good.
			} else {
				if (selectedCount <= 2) { tiles[tileIndex].setShaded(2); }
				else { tiles[tileIndex].setShaded(3); }
				Log.d(TAG, String.format("Setting tile %d shading = %s",
						tileIndex,
						selectedCount <= 2 ? "Red" : "Blue"));

				if (selectedCount == 4) {
					// The Game class queues up the next join upon
					// finding a valid Powerset, so just set it and go
					if (game.IsValidSet(this.selected)) {
						messageUser(getString(R.string.foundSet));
						int id = getResources().getIdentifier(
								"joinImage",
								"id",
								getPackageName());
						if (!game.IsGameOver()) {
							ImageView iv = (ImageView) findViewById(id);
							if (iv == null) { Log.e(TAG, "Failed to set joinImage. Value is null."); }
							else { iv.setImageDrawable(game.GetNextJoin().GetDrawable(this)); }
						}
						// If it wasn't a valid set, inform the player
						} else {
							messageUser(getString(R.string.badSet));
							}
					ClearTileSelection();
					}
				}
				// Deselect the tile and remove it from the selected array
			} else {
//			if (game.GetGameType() == GameType.Normal || game.GetGameType() == GameType.TimeAttack) { tiles[tileIndex].setShaded(selected); }
//			else { tiles[tileIndex].setShaded(0); }
			tiles[tileIndex].setShaded(selected);
			Log.d(TAG, String.format("Setting tile %d shading = %s", tileIndex, Boolean.valueOf(selected).toString()));

			Tile t = game.GetTileAt(tileIndex);
				for (int i = 0; i < this.selected.length; i++) {
					if (this.selected[i] == t) {
						this.selected[i] = null;
						break;
					}
				}
			}
		}

	/**
	 * Deselects all Tiles.
	 */
	private void ClearTileSelection() {
		for (int i = 0; i < selected.length; i++) {selected[i] = null;}
		for (int i = 0; i < boardSize; i++)	{
			SetTileSelected(i, false);}
		}

	/**
	 * Displays a message to the user in a Toast.
	 *
	 * @param msg The message to display
	 */
	private void messageUser(@NonNull String msg) {
		if (msg == null) { throw new NullPointerException("Message to user cannot be null."); }
		Toast toast = Toast.makeText(this, msg,
				(msg.length() > 24 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT));
		toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
		}

	/**
	 * Event handler for the player selects a Tile.
	 *
	 * @param view The View representing the selected tile
	 */
	@Override
	public void onClick(@NonNull View view) {
		if (view == null) { throw new NullPointerException("Button clicked cannot be null."); }
		Object o = view.getTag(R.id.TILE_INDEX);
		if (view instanceof ImageView && o instanceof Integer) {
			if (game.IsGameOver())
				return;

			int index = (Integer) o;
			Log.d(TAG, String.format("Event: onClick for Tile %d", index));

			SetTileSelected(index, !IsTileSelected(index));
			}
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void GameOver(@Nullable GameOverEvent e) {
		long lastGame = PlayerDataDbHelper.saveOutcome(this, game);
		Intent i = new Intent(this, SummaryScreen.class);
		i.putExtra("lastGame", lastGame);
		startActivity(i);
		finish();
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onStop() {
		super.onStop();
		game.PauseTimer();
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		game.UnpauseTimer();
		}

}
