/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

/**
 * Enum containing modifiers for difficulty of Sets.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 28, 2015
 */
public enum Modifier {

	NUMBER_DIFFERENCE(1.27),
	COLOR_DIFFERENCE(1.41),
	FILL_DIFFERENCE(1.46),
	SHAPE_DIFFERENCE(1.53);

	public static final double minModifier = 1;
	public static final double maxModifier = NUMBER_DIFFERENCE.modValue * COLOR_DIFFERENCE.modValue * FILL_DIFFERENCE.modValue * SHAPE_DIFFERENCE.modValue;

	private double modValue;
	Modifier(double modValue) { this.modValue = modValue; }
	public double GetValue() { return modValue; }
}
