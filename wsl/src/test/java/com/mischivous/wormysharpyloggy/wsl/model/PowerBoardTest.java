/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * A brief description of the class, to be filled in later
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since Jul 02, 2015
 */
public class PowerBoardTest {

	// Verified difficulty: 7.1774255
	// Join: 2 gre hol squ
	// Set 1: 1 red fil squ, 3 pur sha squ
	// Set 2: 1 pur hol squ, 3 red hol ova

	// Join: 1 gre sha ova
	// Set 1: 1 pur sha dia, 1 red sha squ
	// Set 2: 1 pur hol dia, 1 red fil squ

	// Join: 1 red hol squ
	// Set 1: 1 red fil ova, 1 red sha dia
	// Set 2: 1 red fil squ, 1 red sha squ

	String powerBoard = "PowerSet, [1 Purple Hollow Diamond, 1 Red Filled Oval, 3 Purple Dashed Squiggle, 3 Red Hollow Oval, 1 Red Filled Squiggle, 1 Red Dashed Diamond, 1 Purple Dashed Diamond, 1 Red Dashed Squiggle, 2 Green Dashed Oval]";
	@Test(timeout = 100)
	public void testPowerEasyThreeBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 3, 1, 2.5);
	}

	@Test(timeout = 100)
	public void testPowerMediumThreeBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 3, 2.5, 2.85);
	}

	@Test(timeout = 100)
	public void testPowerHardThreeBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 3, 2.85, 4);
	}

	@Test(timeout = 100)
	public void testPowerEasyFourBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 4, 1, 2.5);
	}

	@Test(timeout = 100)
	public void testPowerMediumFourBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 4, 2.5, 2.85);
	}

	@Test(timeout = 100)
	public void testPowerHardFourBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 4, 2.85, 4);
	}

	@Test(timeout = 100)
	public void testPowerEasyFiveBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 5, 1, 2.5);
	}

	@Test(timeout = 100)
	public void testPowerMediumFiveBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 5, 2.5, 2.85);
	}

	@Test(timeout = 100)
	public void testPowerHardFiveBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 5, 2.5, 4);
	}

	@Test(timeout = 100)
	public void testPowerEasySixBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 6, 1, 2.5);
	}

	@Test(timeout = 100)
	public void testPowerMediumSixBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 6, 2.5, 2.85);
	}

	@Test(timeout = 100)
	public void testPowerHardSixBoardGen() throws Exception {
		Board b = new Board(GameType.PowerSet, 6, 2.85, 4);
	}

	@Test
	public void testFromString() throws Exception {
		Board b = Board.fromString(powerBoard);
		Tile j1 = Tile.GetTile(2, Color.Green, Fill.Hollow, Shape.Squiggle);
		Tile j2 = Tile.GetTile(1, Color.Green, Fill.Dashed, Shape.Oval);
		Tile j3 = Tile.GetTile(1, Color.Red, Fill.Hollow, Shape.Squiggle);
		Set<Tile> joins = b.GetJoins();

		Assert.assertEquals(GameType.PowerSet, b.GetGameType());
		Assert.assertTrue(Math.abs(b.GetDifficulty() - 7.177) < 0.001);
		Assert.assertEquals(3, joins.size());
		Assert.assertTrue(joins.contains(j1));
		Assert.assertTrue(joins.contains(j2));
		Assert.assertTrue(joins.contains(j3));
	}

}