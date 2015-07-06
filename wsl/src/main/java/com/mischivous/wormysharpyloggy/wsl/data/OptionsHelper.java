/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.mischivous.wormysharpyloggy.wsl.R;
import com.mischivous.wormysharpyloggy.wsl.model.GameType;

/**
 * A class to assist in saving and retrieving specific game settings.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 30, 2015
 */
public class OptionsHelper {

	// Gets the player's selected game hardness, defaulting to medium
	// when a predetermined value cannot be found.
	public static int GetHardness(Context ctx){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		return Integer.valueOf(sp.getString(ctx.getString(R.string.optDiffKey),
				ctx.getString(R.string.optDiffMidLevelValue)));
		}

	// Returns the minimum difficulty value per set for each game type
	// at the player's chosen hardness.
	public static double GetMinDiff(Context ctx, GameType type) {
		switch (GetHardness(ctx)) {
			case 2:
				return 2.85;
			case 1:
				return 2.5;
			default:
				return 1;
			}
		}

	// Returns the maximum difficulty value per set for each game type
	// at the player's chosen hardness.
	public static double GetMaxDiff(Context ctx, GameType type) {
		switch (GetHardness(ctx)) {
			case 2:
				return 4;
			case 1:
				return 2.85;
			default:
				return 2.5;
			}
		}

	// Gets the player-chosen number of sets to be found per game,
	// defaulting to four when a predetermined value cannot be found.
	public static int GetSetCount(Context ctx) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		return Integer.valueOf(sp.getString(ctx.getString(R.string.optSparseKey),
				ctx.getString(R.string.optSparseMidLevelValue)));
		}
}
