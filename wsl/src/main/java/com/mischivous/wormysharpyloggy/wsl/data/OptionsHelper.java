/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.mischivous.wormysharpyloggy.wsl.R;

/**
 * A class to assist in saving and retrieving specific game settings.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 30, 2015
 */
public class OptionsHelper {

	/**
	 * Gets the player's selected game hardness, defaulting to medium.
	 *
	 * @param ctx The Context used to access the player's settings.
	 * @return A 1-indexed integer representing the player's specified hardness.
	 */
	public static int GetHardness(Context ctx){
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		return Integer.valueOf(sp.getString(ctx.getString(R.string.optDiffKey),
				ctx.getString(R.string.optDiffMidLevelValue)));
		}

	/**
	 * Gets the player-chosen number of sets to be found per game, defaulting to four.
	 *
	 * @param ctx The Context used to access the player's settings.
	 * @return The number of sets the player has requested to find
	 */
	public static int GetSetCount(Context ctx) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		return Integer.valueOf(sp.getString(ctx.getString(R.string.optSparseKey),
		                                    ctx.getString(R.string.optSparseMidLevelValue)));
	}

	/**
	 * Returns the average minimum difficulty value per set at the player's chosen hardness.
	 *
	 * @param ctx The Context used to access the player's settings.
	 * @return The average minimum per-set difficulty for the player's specified difficulty.
	 */
	public static double GetMinDiff(Context ctx) {
		switch (GetHardness(ctx)) {
			case 2:
				return 3;
			case 1:
				return 2;
			default:
				return 1;
			}
		}

	/**
	 * Returns the average maximum difficulty value per set at the player's chosen hardness.
	 *
	 * @param ctx The Context used to access the player's settings.
	 * @return The average maximum per-set difficulty for the player's specified difficulty.
	 */
	public static double GetMaxDiff(Context ctx) {
		switch (GetHardness(ctx)) {
			case 2:
				return 4;
			case 1:
				return 3;
			default:
				return 2;
			}
		}

	/**
	 * Returns the amount of starting time for Time Attack at the player's specified difficulty.
	 *
	 * @param ctx The Context used to access the player's settings.
	 * @return The amount of starting time for a Time Attack game at the player's specified difficulty.
	 */
	public static int GetStartingTimeAllotment(Context ctx) {
		switch (GetHardness(ctx)) {
			case 2:
				return 15;
			case 1:
				return 30;
			default:
				return 45;
			}
		}

	/**
	 * Returns the amount of time given for finding a new Time Attack Set based at the player's specified difficulty.
	 *
	 * @param ctx The Context used to access the player's settings.
	 * @return The amount of time given for finding a new Time Attack Set at the player's specified difficulty.
	 */
	public static int GetFoundSetTimeAllotment(Context ctx) {
		switch (GetHardness(ctx)) {
			case 2:
				return 10;
			case 1:
				return 15;
			default:
				return 20;
			}
		}
}
