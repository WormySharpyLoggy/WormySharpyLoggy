/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import org.junit.Test;

/**
 * A brief description of the class, to be filled in later
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since Jul 02, 2015
 */
public class GameTest {

	Game normal = new Game(GameType.Normal, 4, 2.5, 2.85);
	Game power = new Game(GameType.PowerSet, 4, 2.5, 2.85);

	@Test(expected = NullPointerException.class)
	public void testNullTypeGame() throws Exception {
		Game g = new Game(null, 4, 1, 4);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongCountSetsGame() throws Exception {
		Game g = new Game(GameType.PowerSet, 0, 1, 4);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testLowDiffGame() throws Exception {
		Game g = new Game(GameType.TimeAttack, 4, 0, 4);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testHighDiffGame() throws Exception {
		Game g = new Game(GameType.Normal, 4, 1, 5);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testInvertedDiffGame() throws Exception {
		Game g = new Game(GameType.TimeAttack, 5, 4, 1);
	}

	@Test(expected = NullPointerException.class)
	public void testNullTileValidSet() throws Exception {
		boolean b = normal.IsValidSet(null);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testNormalWrongCountValidSet() throws Exception {
		boolean b = normal.IsValidSet(Tile.GetRandomTiles(4));
		}

	@Test(expected = IllegalArgumentException.class)
	public void testPowerWrongCountValidSet() throws Exception {
		boolean b = power.IsValidSet(Tile.GetRandomTiles(3));
		}

	@Test(expected = NullPointerException.class)
	public void testNullTileFoundAlready() throws Exception {
		boolean b = normal.WasFoundAlready(null);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongCountFoundAlready() throws Exception {
		boolean b = normal.WasFoundAlready(Tile.GetRandomTiles(4));
		}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongTypeGetNextJoin() throws Exception {
		Tile t = normal.GetNextJoin();
		}

	@Test(expected = IllegalArgumentException.class)
	public void testBadIndexGetTileAt() throws Exception {
		Tile t = power.GetTileAt(9);
		}
}