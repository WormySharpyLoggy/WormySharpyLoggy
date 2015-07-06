/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.util;

import android.support.annotation.NonNull;
import android.support.annotation.Size;
import com.mischivous.wormysharpyloggy.wsl.model.*;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Helper class providing functions to decide whether a group of tiles is a Set.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 28, 2015
 */
public class SetHelper {

	/**
	 * Determines if three Tiles actually comprise a Set.
	 * @param tile1 The first Tile in the potential set
	 * @param tile2 The second Tile in the potential set
	 * @param tile3 The third Tile in the potential set
	 * @return Whether or not the three provided tiles comprises a set
	 */
	public static boolean IsValidSet(@NonNull Tile tile1,
	                                 @NonNull Tile tile2,
	                                 @NonNull Tile tile3) {
		if (tile1 == null || tile2 == null || tile3 == null) {
			throw new NullPointerException("Tiles in a Set cannot be null");
			}
		if ((tile1.GetNum() + tile2.GetNum() + tile3.GetNum()) % 3 != 0) { return false; }
		else if ((tile1.GetColorVal() + tile2.GetColorVal() + tile3.GetColorVal()) % 3 != 0) { return false; }
		else if ((tile1.GetFillVal() + tile2.GetFillVal() + tile3.GetFillVal()) % 3 != 0) { return false; }
		else if ((tile1.GetShapeVal() + tile2.GetShapeVal() + tile3.GetShapeVal()) % 3 != 0) { return false; }

		return true;
		}

	/**
	 * Given a Set, determines the difficulty of discovery.
	 * @param tile1 The first Tile of the set
	 * @param tile2 The second Tile of the set
	 * @param tile3 The third Tile of the set
	 * @return the difficulty of the set
	 */
	public static double GetSetDifficulty(@NonNull Tile tile1,
	                                      @NonNull Tile tile2,
	                                      @NonNull Tile tile3) {
		if (tile1 == null || tile2 == null || tile3 == null) {
			throw new NullPointerException("Tiles in a Set cannot be null");
		}
		else if (!IsValidSet(tile1, tile2, tile3)) { return 0; }

		double diff = 1;
		if (tile1.GetNum() != tile2.GetNum()) { diff *= Modifier.NUMBER_DIFFERENCE.GetValue(); }
		if (tile1.GetColor() != tile2.GetColor()) { diff *= Modifier.COLOR_DIFFERENCE.GetValue(); }
		if (tile1.GetFill() != tile2.GetFill()) { diff *= Modifier.FILL_DIFFERENCE.GetValue(); }
		if (tile1.GetShape() != tile2.GetShape()) { diff *= Modifier.SHAPE_DIFFERENCE.GetValue(); }

		return diff;
		}

	/**
	 * Given two Tiles, finds the third Tile that would create a Set.
	 * @param t1 The first Tile
	 * @param t2 The second Tile
	 * @return The final Tile
	 */
	@NonNull
	public static Tile GetLastTile(@NonNull Tile t1, @NonNull Tile t2) {
		if (t1 == null || t2 == null) {
			throw new NullPointerException("Tiles in a set cannot be null");
			}

		int num;
		Color col;
		Fill fil;
		Shape sha;

		if (t1.GetNum() == t2.GetNum()) { num = t1.GetNum(); }
		else { num = 6 - t1.GetNum() - t2.GetNum(); }

		if (t1.GetColor() == t2.GetColor()) { col = t1.GetColor(); }
		else { col = Color.OddManOut(t1.GetColor(), t2.GetColor()); }

		if (t1.GetFill() == t2.GetFill()) { fil = t1.GetFill(); }
		else { fil = Fill.OddManOut(t1.GetFill(), t2.GetFill()); }

		if (t1.GetShape() == t2.GetShape()) { sha = t1.GetShape(); }
		else { sha = Shape.OddManOut(t1.GetShape(), t2.GetShape()); }

		return Tile.GetTile(num, col, fil, sha);
		}

	/**
	 * Given an array of Tiles representing a Powerset board,
	 * finds the Tiles not already in the array that can be used
	 * as joins.
	 *
	 * @param board The Tile array representing a PowerSet Board
	 * @return A set of Tiles comprising all joins not on the board
	 */
	@NonNull
	public static Set<Tile> GetJoins(@Size(9) Tile[] board) {
		if (board == null) { throw new NullPointerException("A set Board cannot be null."); }
		else if (board.length != 9) { throw new IllegalArgumentException("A set Board contains exactly nine Tiles."); }

		Set<Tile> result = new HashSet<>();
		Set<Tile> boardSet = new HashSet<>(Arrays.asList(board));
		ICombinatoricsVector<Tile> tileVector = Factory.createVector(board);
		Generator<Tile> gen = Factory.createSimpleCombinationGenerator(tileVector, 4);
		for (ICombinatoricsVector<Tile> v : gen) {
			Tile a = v.getValue(0);
			Tile b = v.getValue(1);
			Tile c = v.getValue(2);
			Tile d = v.getValue(3);

			// Get the last tile to create a Set from each Tile
			// pair to search for joins
			Tile ab = SetHelper.GetLastTile(a, b);
			Tile ac = SetHelper.GetLastTile(a, c);
			Tile ad = SetHelper.GetLastTile(a, d);
			Tile bc = SetHelper.GetLastTile(b, c);
			Tile bd = SetHelper.GetLastTile(b, d);
			Tile cd = SetHelper.GetLastTile(c, d);

			// Check for AB/CD Powersets
			if (ab == cd) { if (!boardSet.contains(ab)) { result.add(ab);} }

			// Check for AC/BD Powersets
			if (ac == bd) { if (!boardSet.contains(ac)) { result.add(ac);} }

			// Check for AD/BC Powersets
			if (ad == bc) { if (!boardSet.contains(ad)) { result.add(ad);} }
			}
		return result;
		}
	}
