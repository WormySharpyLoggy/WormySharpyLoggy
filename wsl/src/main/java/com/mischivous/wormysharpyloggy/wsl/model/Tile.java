/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.v4.content.res.ResourcesCompat;
import com.mischivous.wormysharpyloggy.wsl.util.SetHelper;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.*;

/**
 * Provides a representation of an individual tile used in the game.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 28, 2015
 */
public class Tile implements Comparable<Tile> {
	private static final List<Tile> allTiles;
	static {
		allTiles = new ArrayList<>(81);
		for (Shape sh : Shape.values()) {
			for (Fill fi : Fill.values()) {
				for (Color co : Color.values())
					for (int i = 1; i < 4; i++) {
						int index = (i - 1)
								+ (3 * (co.GetNumVal() - 1))
								+ (9 * (fi.GetNumVal() - 1))
								+ (27 * (sh.GetNumVal() - 1));
						allTiles.add(index, new Tile(i, co, fi, sh));
					}
			}
		}
	}

	static Random r = new Random();
	private int num;
	private Color col;
	private Fill fil;
	private Shape sha;

	public Tile(@IntRange(from = 1, to = 3) int count,
	            @NonNull Color color,
	            @NonNull Fill fill,
	            @NonNull Shape shape) {
		if (count < 1 || count > 3) { throw new IllegalArgumentException("Tiles have between one and three shapes."); }
		else if (color == null) { throw new NullPointerException("The Color of a Tile cannot be null."); }
		else if (fill == null) { throw new NullPointerException("The Fill of a Tile cannot be null."); }
		else if (shape == null) { throw new NullPointerException("The Shape of a Tile cannot be null."); }

		num = count;
		col = color;
		fil = fill;
		sha = shape;
		}

	/**
	 * Return a previously-generated Tiles instead of creating a new one
	 *
	 * @param count The number of items on the Tile
	 * @param color The Color of the items on the Tile
	 * @param fill The Fill of the items on the Tile
	 * @param shape the Shape of the items on the Tile
	 * @return The Tile conforming to the provided attributes
	 */
	@NonNull
	public static Tile GetTile(@IntRange(from = 1, to = 3) int count,
	                           @NonNull Color color,
	                           @NonNull Fill fill,
	                           @NonNull Shape shape) {
		if (count < 1 || count > 3) { throw new IllegalArgumentException("Tiles have between one and three shapes."); }
		else if (color == null) { throw new NullPointerException("The Color of a Tile cannot be null."); }
		else if (fill == null) { throw new NullPointerException("The Fill of a Tile cannot be null."); }
		else if (shape == null) { throw new NullPointerException("The Shape of a Tile cannot be null."); }

		int index = (count - 1)
				+ (3 * (color.GetNumVal() - 1))
				+ (9 * (fill.GetNumVal() - 1))
				+ (27 * (shape.GetNumVal() - 1));
		return allTiles.get(index);
		}

	/**
	 * Return the specified number of random Tiles.
	 *
	 * @param count The number of Tiles to return
	 * @return An array containing the specified number of Tiles
	 */
	@NonNull
	public static Tile[] GetRandomTiles(@IntRange(from = 1, to = 81) int count) {
		if (count < 1 || count > 81) { throw new IllegalArgumentException("There are only 81 Tiles in the deck"); }
		Set<Tile> tiles = new HashSet<>(count);
		Tile t;
		while (tiles.size() != count) {
			t = allTiles.get(r.nextInt(81));
			if (!tiles.contains(t)) {tiles.add(t);}
		}

		return tiles.toArray(new Tile[count]);
		}

	/**
	 * Return the specified amount of Tiles using the specified amount of random Tiles
	 * <p>
	 * Create a Set board with the requested amount of Tiles using the
	 * requested amount of random Tiles. The difference will be made up
	 * for by manual selection.
	 *
	 * @param boardSize The size of the board to return
	 * @param randomStarters The number of random Tiles to use when creating the board
	 * @return A Tile array conforming to the given inputs
	 */
	@NonNull
	public static Tile[] GetTilesForBoard(int boardSize, int randomStarters) {
		if (boardSize != 9) { throw new IllegalArgumentException("Boards must have nine Tiles."); }
		else if (randomStarters > boardSize) { throw new IllegalArgumentException(
					String.format("Cannot use %d random starters for Board of size %d.", randomStarters, boardSize)); }
		else if (randomStarters == 0) { return GetRandomTiles(boardSize); }

		Set<Tile> maybeBoard = new HashSet<>(boardSize);
		Set<Tile> starters;

		do {
			// Starting with six random Tiles and building the Board
			// to nine yields a more optimal distribution for normal
			// and time attack games, which is why I do it here.
			maybeBoard.clear();
			starters = new HashSet<>(Arrays.asList(Tile.GetRandomTiles(randomStarters)));
			maybeBoard.addAll(starters);

			// Check for Powerset games so they don't have to go
			// through the loop.
			if (maybeBoard.size() == boardSize) {break;}

			ICombinatoricsVector<Tile> tileVector = Factory.createVector(maybeBoard);
			Generator<Tile> gen = Factory.createSimpleCombinationGenerator(tileVector, 2);
			for (ICombinatoricsVector<Tile> v : gen) {
				Tile t1 = v.getValue(0);
				Tile t2 = v.getValue(1);
				Tile t = SetHelper.GetLastTile(t1, t2);

				if (!maybeBoard.contains(t)) {maybeBoard.add(t);}
				if (maybeBoard.size() == boardSize) {break;}
			}
			// It's possible there is no way to create a Board of
			// size nine with the selected starters, hence the
			// redundant checks.
			} while (maybeBoard.size() != boardSize);

		return maybeBoard.toArray(new Tile[boardSize]);
		}

	/**
	 * Returns the Drawable representing the Tile.
	 *
	 * @param context The context to retrieve the Tile from
	 * @return A Drawable representing the Tile
	 */
	@NonNull
	public Drawable GetDrawable(@NonNull Context context) {
		if (context == null) { throw new NullPointerException("Context cannot be null."); }

		int id = context.getResources().getIdentifier(
				String.format("tile_%d%d%d%d",
						GetNum(),
						GetShapeVal(),
						GetColorVal(),
						GetFillVal()),
				"mipmap",
				context.getPackageName());

		return ResourcesCompat.getDrawable(
				context.getResources(),
				id,
				null);
		}

	/**
	 * Returns a miniature version of the Drawable representing the Tile.
	 *
	 * @param context The context to retrieve the Tile from
	 * @return A Drawable representing the Tile
	 */
	@NonNull
	public Drawable GetSmallDrawable(@NonNull Context context) {
		if (context == null) { throw new NullPointerException("Context cannot be null."); }

		int id = context.getResources().getIdentifier(
				String.format("tile_small_%d%d%d%d",
						GetNum(),
						GetShapeVal(),
						GetColorVal(),
						GetFillVal()),
				"mipmap",
				context.getPackageName());

		return ResourcesCompat.getDrawable(
				context.getResources(),
				id,
				null);
		}

	/**
	 * Get the number of items on the Tile
	 *
	 * @return The number of items on the Tile
	 */
	public int GetNum() {
		return num;
		}

	/**
	 * Get the Color value of the Tile
	 *
	 * @return The Color value of the Tile
	 */
	@NonNull
	public Color GetColor() {
		return col;
		}

	/**
	 * Get the integer value relating to the Tile's Color
	 *
	 * @return The integer value relating to the Color of the Tile
	 */
	public int GetColorVal() {
		return col.GetNumVal();
		}

	/**
	 * Get the Fill value of the Tile
	 *
	 * @return The Fill value of the Tile
	 */
	@NonNull
	public Fill GetFill() {
		return fil;
	}

	/**
	 * Get the integer value of the Tile's Fill
	 *
	 * @return The integer value relating to the Fill of the Tile
	 */
	public int GetFillVal() {
		return fil.GetNumVal();
		}

	/**
	 * Get the Shape value of the Tile
	 *
	 * @return The Shape value of the Tile
	 */
	@NonNull
	public Shape GetShape() {
		return sha;
	}

	/**
	 * Get the integer value of the Tile's Shape
	 *
	 * @return The integer value relating to the Shape of the Tile
	 */
	public int GetShapeVal() {
		return sha.GetNumVal();
		}

	/**
	 * Given a Tile's string, retrieve the original Tile
	 *
	 * @param str The String representation of the Tile
	 * @return The Tile represented by the string
	 */
	@NonNull
	public static Tile fromString(@NonNull @Size(min = 1) String str) {
		if (str == null) { throw new NullPointerException("Tile String cannot be null."); }
		else if (str.isEmpty()) { throw new IllegalArgumentException("Tile String cannot be empty."); }
		else if (!str.matches("[123] (Red|Green|Purple) (Filled|Dashed|Hollow) (Oval|Diamond|Squiggle)")) {
			throw new IllegalArgumentException("Entered string not valid Tile");
			}

		String[] parts = str.split(" ");
		return Tile.GetTile(Integer.parseInt(parts[0]),
				Color.valueOf(parts[1]),
				Fill.valueOf(parts[2]),
				Shape.valueOf(parts[3]));
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format(Locale.US, "%d %s %s %s", num, col.toString(), fil.toString(), sha.toString());
			}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(@NonNull Object o) {
		if (o == null) { return false; }
		else if (o == this) { return true; }
		else if (!(o instanceof Tile)) { throw new IllegalArgumentException("Object entered is not Tile"); }

		Tile t = (Tile) o;
		if (t.num != num) { return false; }
		if (t.col != col) { return false; }
		if (t.fil != fil) { return false; }
		if (t.sha != sha) { return false; }

		return true;
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return (num - 1)
				+ (3 * (col.GetNumVal() - 1))
				+ (9 * (fil.GetNumVal() - 1))
				+ (27 * (sha.GetNumVal() - 1));
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(@NonNull Tile t) {
		if (t == null) { throw new NullPointerException("Comparison object cannot be null."); }

		if (this.hashCode() == t.hashCode()) { return 0; }
		else if (this.hashCode() < t.hashCode()) { return -1; }
		else { return 1; }
		}
}
