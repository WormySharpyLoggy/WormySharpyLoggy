/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import android.support.annotation.NonNull;

/**
 * Enum containing possible Tile color values.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 28, 2015
 */
public enum Color {
    Red(1),
    Green(2),
    Purple(3);

    private int numVal;
    Color(final int numVal) { this.numVal = numVal; }

	/**
	 * Returns the integer value associated to the Color.
	 *
	 * @return The integer value related to the Color
	 */
    public int GetNumVal() { return numVal; }

	/**
	 * Given two different Color types, returns the last Color type.
	 *
	 * @param a The first Color type
	 * @param b The second Color type
	 * @return The last Color type
	 */
	@NonNull
	public static Color OddManOut(@NonNull Color a, @NonNull Color b) {
		if (a == null || b == null) { throw new NullPointerException("Colors cannot be null."); }

		if ((a.numVal + b.numVal) % 3 == 0) { return Purple; }
		else if ((a.numVal + b.numVal) % 3 == 1) { return Green;}
		else { return Red; }
	}
}
