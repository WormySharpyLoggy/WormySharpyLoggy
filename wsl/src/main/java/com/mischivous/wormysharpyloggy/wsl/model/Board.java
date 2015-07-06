/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import com.mischivous.wormysharpyloggy.wsl.util.SetHelper;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the game board.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 28, 2015
 */
public class Board implements Comparable<Board> {
	private static final String TAG = "Board";
	private final GameType type;
	private double difficulty = 0;
	private int genCount = 0;
	private Tile[] tiles;
	private Set<Tile> joins;

	/**
	 * Create a new Board based on the specified game type,
	 * number of sets and average minimum/maximum per-set difficulty.
	 *
	 * @param type The desired game type
	 * @param setCount The desired number of sets in the Board
	 * @param minDiff The average minimum difficulty of a set
	 * @param maxDiff The average maximum difficulty of a set
	 */
	public Board(@NonNull GameType type,
	             @IntRange(from = 3, to = 6) int setCount,
	             @FloatRange(from = 1) double minDiff,
	             @FloatRange(to = 4) double maxDiff) {
		if (type == null) { throw new NullPointerException("Game type cannot be null."); }
		else if (minDiff < 1) { throw new IllegalArgumentException("The minimum average per-set difficulty is one."); }
		else if (maxDiff > 4) { throw new IllegalArgumentException("The maximum average per-set difficulty is four."); }
		else if (minDiff > maxDiff) {
			throw new IllegalArgumentException("Minimum difficulty cannot exceed maximum difficulty.");
		} else if (setCount < 2 || setCount > 7) {
			throw new IllegalArgumentException("Boards can only have between three and six sets, inclusive.");
			}

		this.type = type;
		double[] stats;

		do {
			genCount += 1;
			// First: generate the board.
			Tile[] maybeBoard;
			if (type == GameType.Normal || type == GameType.TimeAttack) {
				maybeBoard = Tile.GetTilesForBoard(9, 6);
				} else {maybeBoard = Tile.GetTilesForBoard(9, 9);}

			if (maybeBoard.length != 9) {continue;}

			// Next: find the number of sets and difficulty for the
			// generated board.
			stats = ComputeStats(type, maybeBoard);

			// This if should be removed once I've confirmed the
			// difficulty calculation works; until then I don't
			// consider the difficulty of Powerset boards.
			if (type == GameType.Normal || type == GameType.TimeAttack) {
				// Make sure we have the correct number of sets and the
				// right difficulty.
				if (stats[0] != setCount
						|| stats[1] < setCount * minDiff
						|| stats[1] > setCount * maxDiff) {
					continue;
					}
				} else {if (stats[0] != setCount) {continue;}}

			// If we arrive here, the board has the correct number
			// of sets, and is within the required difficulty range
			difficulty = stats[1];
			tiles = maybeBoard;

			if (type == GameType.PowerSet) {joins = SetHelper.GetJoins(tiles);}

		} while (tiles == null);

//		Log.d(TAG, String.format("It took %d tries to generate a %d-set %s board, difficulty %f",
//				genCount, setCount, type.toString(), difficulty));
	}

	/**
	 * Given an array of Tiles, create a Board.
	 *
	 * @param type The desired game type
	 * @param board The Tiles to create the Board with
	 */
	public Board(@NonNull GameType type, @Size(9) Tile[] board) {
		if (type == null) { throw new NullPointerException("Game type cannot be null."); }
		else if (board == null) { throw new NullPointerException("Tile list cannot be null."); }
		else if (board.length != 9) { throw new IllegalArgumentException(
					String.format("Boards does not have correct number of Tiles; only has %d", board.length));
			}

		double[] stats = ComputeStats(type, board);
		this.type = type;
		tiles = board;
		difficulty = stats[1];
		if (type == GameType.PowerSet) {joins = SetHelper.GetJoins(tiles);}
		}

	/**
	 * Given an array of Tiles representing a Board, compute the number
	 * of sets/PowerSets on the Board and the Board's difficulty.
	 *
	 * @param type The type of game to determine statistics for
	 * @param board The Board to compute statistics on
	 * @return The number of sets/Powersets, and the Board's difficulty
	 */
	public static double[] ComputeStats(@NonNull GameType type, @Size(9) Tile[] board) {
		if (type == null) { throw new NullPointerException("Game type cannot be null."); }
		else if (board == null) { throw new NullPointerException("Tile list cannot be null."); }
		else if (board.length != 9) { throw new IllegalArgumentException(
					String.format("Boards does not have correct number of Tiles; only has %d", board.length));
			}

		// stats[0] is the number of sets for the board,
		// stats[1] is the difficulty of the board; the difficulty of
		// each Powerset is obtained by averaging the difficulty of the
		// two Sets that comprise it.
		double[] stats = new double[2];
		// Calculate the statistics of the provided board with regards
		// to a normal Set game and simpler variants, such as time attack.
		if (type == GameType.Normal || type == GameType.TimeAttack) {
			ICombinatoricsVector<Tile> tileVector = Factory.createVector(board);
			Generator<Tile> gen = Factory.createSimpleCombinationGenerator(tileVector, 3);
			for (ICombinatoricsVector<Tile> v : gen) {
				Tile t1 = v.getValue(0);
				Tile t2 = v.getValue(1);
				Tile t3 = v.getValue(2);

				if (SetHelper.IsValidSet(t1, t2, t3)) {
					stats[0] += 1;
					stats[1] += SetHelper.GetSetDifficulty(t1, t2, t3);
				}
			}
		}

		// Calculate the statistics of the provided board with regards
		// to the Powerset variant.
		else {
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
				if (ab == cd) {
					if (!boardSet.contains(ab)) {
						stats[0] += 1;
						stats[1] += (SetHelper.GetSetDifficulty(a, b, ab) + SetHelper.GetSetDifficulty(c, d, cd)) / 2;
						}
					}

				// Check for AC/BD Powersets
				if (ac == bd) {
					if (!boardSet.contains(ac)) {
						stats[0] += 1;
						stats[1] += (SetHelper.GetSetDifficulty(a, c, ac) + SetHelper.GetSetDifficulty(b, d, bd)) / 2;
						}
					}

				// Check for AD/BC Powersets
				if (ad == bc) {
					if (!boardSet.contains(ad)) {
						stats[0] += 1;
						stats[1] += (SetHelper.GetSetDifficulty(a, d, ad) + SetHelper.GetSetDifficulty(b, c, bc)) / 2;
						}
					}

				}

			}
		return stats;
		}

	/**
	 * Given an index into the Board, returns the Tile at that index.
	 *
	 * @param index The index of the Tile to return
	 * @return The Tile at the specified index
	 */
	@NonNull
	public Tile GetTile(@IntRange(from = 0, to = 8) int index) {
		if (index < 0 || index > 8) { throw new IllegalArgumentException("Board indexed zero to eight, inclusive."); }

		return tiles[index];
		}

	/**
	 * Returns the number of potential Boards considered before
	 * settling on the one chosen, plus that one.
	 *
	 * @return The number of discarded Boards, plus the one chosen
	 */
	public int GetGenCount() {
		return genCount;
		}

	/**
	 * Returns the game type used to generate the board.
	 *
	 * @return The game type associated with the board
	 */
	public GameType GetGameType() {
		return type;
		}

	/**
	 * Returns the difficulty of the generated board.
	 *
	 * @return The board's difficulty
	 */
	public double GetDifficulty() {
		return difficulty;
		}

	/**
	 * Returns the joins of the Board as a set of Tiles;
	 * if the game type is not PowerSet, there are no joins and thus
	 * will return null.
	 *
	 * @return The joins of the Board
	 */
	public Set<Tile> GetJoins() {
		if (type != GameType.PowerSet) {
			throw new IllegalArgumentException("Cannot get joins for non-Powerset Board.");
			} else { return joins; }
		}

	/**
	 * Given a String representing a Board, return the corresponding Board.
	 *
	 * @param str The string representation of a Board
	 * @return The Board corresponding to the string
	 */
	public static Board fromString(@NonNull @Size(min = 1) String str) {
		if (str == null) { throw new NullPointerException("Board string cannot be null."); }
		else if (str.isEmpty()) { throw new IllegalArgumentException("Board string cannot be empty"); }

		Board res;

		String[] strings = str.replace("[", "").replace("]", "").split(", ");
		if (!strings[0].matches("(Normal|TimeAttack|PowerSet)")) {
			throw new IllegalArgumentException("Entered string is not a valid Board");
		}

		GameType type = GameType.valueOf(strings[0]);

		Tile[] board = new Tile[strings.length - 1];
		for (int i = 0; i < board.length; i++) {board[i] = Tile.fromString(strings[i + 1]);}
		res = new Board(type, board);
		return res;
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("%s, %s", type.toString(), Arrays.toString(tiles));
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Arrays.hashCode(tiles);
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(@NonNull Object o) {
		if (o == this) { return true; }
		else if (o == null) { throw new NullPointerException("Comparison Board cannot be null."); }
		else if (!(o instanceof Board)) { throw new IllegalArgumentException("Object entered is not Board"); }
		else if (((Board) o).tiles.length != tiles.length) { return false; }
		else if (((Board) o).type != type) { return false; }

		Arrays.sort(this.tiles);
		Arrays.sort(((Board) o).tiles);

		for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] != ((Board) o).tiles[i]) { return false; }
			}

		return true;
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(@NonNull Board b) {
		if (b == this) { return 0; }
		else if (b == null) { throw new NullPointerException("Comparison Board cannot be null."); }

		Arrays.sort(this.tiles);
		Arrays.sort(b.tiles);

		for (int i = 0; i < tiles.length; i++) {
			if (this.tiles[i].hashCode() < b.tiles[i].hashCode()) { return -1; }
			else if (this.tiles[i].hashCode() > b.tiles[i].hashCode()) { return 1; }
			}

		return 0;
		}
	}
