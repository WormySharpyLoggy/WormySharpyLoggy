/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * A brief description of the class, to be filled in later
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since Jul 02, 2015
 */
public class CommonBoardTest {

	@Test(expected = NullPointerException.class)
	public void testNullFromString() throws Exception {
		Board b = Board.fromString(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyFromString() throws Exception {
		Board b = Board.fromString("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadFromString() throws Exception {
		String normalBoard = "Time Attak, [2 Red Filled Oval, 1 Green Dashed Squiggle, 3 Purple Hollow Diamond, 2 Purple Filled Oval, 1 Purple Dashed Diamond, 1 Red Dashed Oval, 1 Green Dashed Oval, 2 Red Hollow Diamond, 3 Red Hollow Diamond]";
		Board b = Board.fromString(normalBoard);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBadIndexGetTile() throws Exception {
		Board b = new Board(GameType.PowerSet, 3, 1, 4);
		Tile t = b.GetTile(9);
		}

	@Test
	public void testToString() throws Exception {
		Tile[] t = Tile.GetRandomTiles(9);
		Board b = new Board(GameType.PowerSet, t);

		String gen = String.format("%s, %s", GameType.PowerSet.toString(), Arrays.toString(t));
		Assert.assertEquals(gen, b.toString());
		}

	@Test
	public void testEquals() throws Exception {
		Tile[] t = Tile.GetRandomTiles(9);
		Board b1 = new Board(GameType.PowerSet, t);
		Board b2 = new Board(GameType.Normal, t);

		String b11 = b1.toString();
		Assert.assertEquals(b1, Board.fromString(b11));
		Assert.assertNotEquals(b1, b2);
		}

}