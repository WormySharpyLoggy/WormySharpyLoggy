/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import android.support.annotation.NonNull;

/**
 * Enum containing possible Tile fill types
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 28, 2015
 */
public enum Fill {
    Filled(1),
    Dashed(2),
    Hollow(3);

    private int numVal;
    Fill(final int numVal) { this.numVal = numVal; }

	/**
	 * Returns the integer value associated to the Fill.
	 *
	 * @return The integer value related to the Fill
	 */
    public int GetNumVal() { return numVal; }

	/**
	 * Given two different Fill types, returns the last Fill type.
	 *
	 * @param a The first Fill type
	 * @param b The second Fill type
	 * @return The last Fill type
	 */
	@NonNull
	public static Fill OddManOut(@NonNull Fill a, @NonNull Fill b) {
		if (a == null || b == null) { throw new NullPointerException("Fills cannot be null."); }

		if ((a.numVal + b.numVal) % 3 == 0) {return Hollow;}
		else if ((a.numVal + b.numVal) % 3 == 1) { return Dashed;}
		else {return Filled;}
	}
}
