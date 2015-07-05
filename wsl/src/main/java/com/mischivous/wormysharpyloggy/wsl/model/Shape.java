/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import android.support.annotation.NonNull;

/**
 * Enum containing possible Tile shapes.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 28, 2015
 */
public enum Shape {
    Oval(1),
    Diamond(2),
    Squiggle(3);

    private int numVal;
    Shape(int numVal){this.numVal = numVal;}

	/**
	 * Returns the integer value associated to the Shape.
	 *
	 * @return The integer value related to the Shape
	 */
    public int GetNumVal(){return numVal;}

	/**
	 * Given two different Shape types, returns the last Shape type.
	 *
	 * @param a The first Shape type
	 * @param b The second Shape type
	 * @return The last Shape type
	 */
	@NonNull
	public static Shape OddManOut(@NonNull Shape a, @NonNull Shape b) {
		if (a == null || b == null) { throw new NullPointerException("Shapes cannot be null."); }

		if ((a.numVal + b.numVal) % 3 == 0) {return Squiggle;}
		else if ((a.numVal + b.numVal) % 3 == 1) { return Diamond;}
		else {return Oval;}
	}
}
