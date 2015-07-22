/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import com.mischivous.wormysharpyloggy.wsl.data.OptionsHelper;
import com.mischivous.wormysharpyloggy.wsl.util.GameOverListener;
import com.mischivous.wormysharpyloggy.wsl.util.SetHelper;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.util.*;

/**
 * Stores all variables related to each individual game
 * and provides basic functionality.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 30, 2015
 */
public class Game {

	// Final variables common to all game types
	private final Set<GameOverListener> listeners = new HashSet<>();
	private final Board board;
	private final int sets;
	private final GameType type;
	private final long[] deltas;

	// Final variables used by normal/time attack games
	private final Set<Set<Tile>> foundSets;
	private final Handler handler = new Handler();
	private final Timer timeRemaining;

	// Final variables used by Powerset games
	private final Queue<Tile> joins;

	// Variables common to all game types
	private Date startTime;
	private long accumulatedTime;
	private boolean paused;
	private boolean isGameOver;
	private boolean hintUsed = false;
	private GameResult outcome;

	public Game(@NonNull GameType gameType,
	            @IntRange(from = 3, to = 6) int setCount,
	            @FloatRange(from = 1) double minDiff,
	            @FloatRange(to = 4) double maxDiff) {
		if (gameType == null) { throw new NullPointerException("Game type cannot be null."); }
		else if (minDiff < 1) { throw new IllegalArgumentException("The minimum average per-set difficulty is one."); }
		else if (maxDiff > 4) { throw new IllegalArgumentException("The maximum average per-set difficulty is four."); }
		else if (minDiff > maxDiff) {
			throw new IllegalArgumentException("Minimum difficulty cannot exceed maximum difficulty.");
		} else if (setCount < 2 || setCount > 7) {
			throw new IllegalArgumentException("Boards can only have between three and six sets, inclusive.");
		}

		sets = setCount;
		type = gameType;
		deltas = new long[setCount];
		board = new Board(type, setCount, minDiff, maxDiff);
		ZeroTimer();

		if (type == GameType.Normal || type == GameType.TimeAttack) {
			foundSets = new HashSet<>(setCount);
			joins = null;
			} else {
			foundSets = null;
			joins = new ArrayDeque<>(setCount);
			joins.addAll(board.GetJoins());
			}

		if (type == GameType.TimeAttack) {
			timeRemaining = new Timer();
			} else { timeRemaining = null; }
		}

	/**
	 * Given an array of Tiles, determines if all three belong to the same set.
	 *
	 * @param tiles The Tiles to check
	 * @return Whether or not they belong to a set that was previously found
	 */
	public boolean IsValidSet(@Size(min = 3, max = 4) Tile[] tiles) {
		if (tiles == null) { throw new NullPointerException("Cannot check validity of Set with no Tiles."); }
		else if (tiles.length < 3 || tiles.length > 4) {
			throw new IllegalArgumentException("Set has incorrect number of Tiles.");
			}

		boolean result;
		// Check the results of a valid set for normal/time attack games
		if (type == GameType.Normal || type == GameType.TimeAttack) {
		if (tiles.length != 3) {
			throw new IllegalArgumentException("Array has incorrect number of Tiles.");
			}

		result = SetHelper.IsValidSet(tiles[0], tiles[1], tiles[2]);

		if (result) {
			int index = foundSets.size();
			if (index == 0) { deltas[0] = GetElapsedTime() / 1000; }
			else { deltas[index] = (GetElapsedTime() / 1000) - deltas[index - 1]; }

			foundSets.add(new HashSet<>(Arrays.asList(tiles)));
			if (foundSets.size() == sets) { onGameOver(GameResult.Win); }
			}

		// Check the results of a valid Powerset for Powerset games
			} else {
			if (tiles.length != 4) {
				throw new IllegalArgumentException("Array has incorrect number of Tiles.");
				}
			Tile join = GetNextJoin();
			result = SetHelper.IsValidSet(tiles[0], tiles[1], join) && SetHelper.IsValidSet(tiles[2], tiles[3], join);

			if (result) {
				if (joins.size() == sets) { deltas[0] = GetElapsedTime() / 1000; }
				else {
					int index = sets - joins.size();
					deltas[index] = (GetElapsedTime() / 1000) - deltas[index - 1];
					}
				joins.remove();
				if (joins.size() == 0) {
					onGameOver(GameResult.Win);
					}
				}
			}

		return result;
		}

	/**
	 * Given an array of Tiles, determines whether or not all three
	 * belong to a set the player has previously found.
	 *
	 * @param tiles The Tiles to check
	 * @return Whether or not they belong to a set that was previously found
	 */
	public boolean WasFoundAlready(@Size(3) Tile[] tiles) {
		if (tiles == null) { throw new NullPointerException("Set of Tiles cannot be null."); }
		else if (tiles.length != 3) { throw new IllegalArgumentException("Incorrect number of Tiles for Set."); }

		Set<Tile> s = new HashSet<>(3);
		s.addAll(Arrays.asList(tiles));
		return foundSets.contains(s);
		}

	/**
	 * Returns the index of a Tile in a Set the player has not yet found.
	 *
	 * @return The index of a Tile in a set the player has not yet found.
	 */
	public int GetHint() {
		Generator<Tile> gen = board.GetBoardCombinations(3);
		int result = -1;
		hintUsed = true;

		for (ICombinatoricsVector<Tile> v : gen) {
			List<Tile> l = v.getVector();
			Tile[] t = l.toArray(new Tile[3]);

			if (SetHelper.IsValidSet(t[0], t[1], t[2]) && !WasFoundAlready(t)) {
				for (int i = 0; i < 9; i++) {
					if (v.getValue(0) == board.GetTile(i)) {
						result = i;
						break;
						}
					}
				}
			}
		return result;
		}

	public int GetHint(Tile[] foundSet) {
		Generator<Tile> gen = board.GetBoardCombinations(2);
		Tile[] t = new Tile[3];
		int result = -1;
		hintUsed = true;

		for (ICombinatoricsVector<Tile> v : gen) {
			t[0] = GetNextJoin();
			t[1] = v.getValue(0);
			t[2] = v.getValue(1);

			if (SetHelper.IsValidSet(t[0], t[1], t[2]) && t[1] != foundSet[0] && t[1] != foundSet[1]) {
				for (int i = 0; i < 9; i++) {
					if (v.getValue(0) == board.GetTile(i)) {
						result = i;
						break;
					}
				}
			}
		}

		return result;
	}
	/**
	 * Resets timer and associated variables before gameplay begins.
	 */
	public void ZeroTimer() {
		startTime = new Date();
		accumulatedTime = 0;
		paused = false;
		isGameOver = false;
		}

	/**
	 * Returns the time elapsed since the game started, in milliseconds
	 *
	 * @return Elapsed play time in milliseconds
	 */
	public long GetElapsedTime() {
		long elapsed = accumulatedTime;
		if (!paused) { elapsed += new Date().getTime() - startTime.getTime(); }
		return elapsed;
		}

	/**
	 * In a time attack game, determines how much time
	 * the player has before they run out.
	 *
	 * @param ctx The Context to retrieve the beginning time allotment and extra time per found set
	 * @return The amount of time remaining in milliseconds
	 */
	public long GetTimeRemaining(Context ctx) {
		if (type != GameType.TimeAttack) { return -1; }
		int startTime = OptionsHelper.GetStartingTimeAllotment(ctx);
		int perSet = OptionsHelper.GetFoundSetTimeAllotment(ctx);
		long result = ((startTime + (perSet * foundSets.size())) - (GetElapsedTime() / 1000))*(1000);
		if (!paused && result <= 0) { onGameOver(GameResult.Loss); }
		return result;
		}

	/**
	 * Pauses the timer.
	 */
	public void PauseTimer() {
		if (!paused) {
			accumulatedTime += new Date().getTime() - startTime.getTime();
			paused = true;
			}
		}

	/**
	 * Restarts the timer after a pause.
	 */
	public void UnpauseTimer() {
		if (paused && !isGameOver) {
			startTime = new Date();
			paused = false;
			}
		}

	/**
	 * Adds a listener to inform once the game has ended.
	 *
	 * @param listener The listener to inform
	 */
	public void AddGameOverListener(@NonNull GameOverListener listener) {
		if (listener == null) { throw new NullPointerException("Cannot add null listener."); }
		listeners.add(listener);
		}

	/**
	 * When the game has ended, informs all listners.
	 *
	 * @param res The result of the game
	 */
	private void onGameOver(@NonNull GameResult res) {
		if (res == null) { throw new NullPointerException("Game result cannot be null."); }
		// In case multiple listeners try to end the game
		if(IsGameOver()) { return; }

		PauseTimer();
		isGameOver = true;
		this.outcome = res;

		if(timeRemaining != null) { timeRemaining.cancel(); }

		GameOverEvent e = new GameOverEvent(this);
		for (GameOverListener listener : listeners) { listener.GameOver(e); }
	}

	/**
	 * Returns the next unfound join for a PowerSet board.
	 *
	 * @return an unfound join
	 */
	@NonNull
	public Tile GetNextJoin() {
		if (type != GameType.PowerSet) {
			throw new IllegalArgumentException("Cannot get joins for non-Powerset Board.");
			} else { return joins.peek(); }
		}

	/**
	 * Given an index into the Board, returns the Tile at that index.
	 *
	 * @param index The index of the Tile to return
	 * @return The Tile at the specified index
	 */
	@NonNull
	public Tile GetTileAt(@IntRange(from = 0, to = 8) int index) {
		if (index < 0 || index > 8) { throw new IllegalArgumentException("Board indexed zero to eight, inclusive."); }
		return board.GetTile(index);
		}

	/**
	 * Returns the number of sets found by the player.
	 *
	 * @return The number of currently-found sets
	 */
	public int GetFoundSetCount() {
		return foundSets.size();
		}

	/**
	 * Returns the number of sets required to be found to win this game.
	 *
	 * @return The number of sets in the game
	 */
	public int GetFullSetCount() {
		return sets;
		}

	/**
	 * Returns the type of game played.
	 *
	 * @return The game's GameType
	 */
	@NonNull
	public GameType GetGameType() {
		return type;
		}

	/**
	 * Returns whether or not the game has ended.
	 *
	 * @return The ultimate status of the game
	 */
	public boolean IsGameOver() {
		return isGameOver;
		}

	/**
	 * Returns whether or not the game is currently paused.
	 *
	 * @return The paused status of the game.
	 */
	public boolean IsPaused() {
		return paused;
		}

	/**
	 * Returns whether the game was won or lost.
	 *
	 * @return The result of the game
	 */
	@NonNull
	public GameResult GetGameResult() {
		return outcome;
		}

	/**
	 * Returns a String representation of the Board.
	 *
	 * @return A String representation of the Board.
	 */
	public String GetBoardString() {
		return  board.toString();
		}

	/**
	 * Returns whether or not hints were used during the game.
	 *
	 * @return Whether or not hints were used
	 */
	public boolean WereHintsUsed() {
		return hintUsed;
		}
	}