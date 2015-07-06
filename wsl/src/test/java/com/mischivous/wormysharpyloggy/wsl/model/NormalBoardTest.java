/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * A brief description of the class, to be filled in later
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since Jul 02, 2015
 */
public class NormalBoardTest {

	// Verified difficulty 10.15743132
	// Set: 1 red sha ova, 1 pur sha dia, 1 gre sha squ
	// Set: 1 gre sha squ, 2 red fil ova, 3 pur hol dia
	// Set: 1 gre sha squ, 2 pur fil ova, 3 red hol dia
	String normalBoard = "Normal, [2 Red Filled Oval, 1 Green Dashed Squiggle, 3 Purple Hollow Diamond, 2 Purple Filled Oval, 1 Purple Dashed Diamond, 1 Red Dashed Oval, 1 Green Dashed Oval, 2 Red Hollow Diamond, 3 Red Hollow Diamond]";

	@Test(timeout = 50)
	public void testNormalEasyThreeBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 3, 1, 2.5);
	}

	@Test(timeout = 50)
	public void testNormalMediumThreeBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 3, 2.5, 2.85);
	}

	@Test(timeout = 50)
	public void testNormalHardThreeBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 3, 2.85, 4);
	}

	@Test(timeout = 50)
	public void testNormalEasyFourBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 4, 1, 2.5);
	}

	@Test(timeout = 50)
	public void testNormalMediumFourBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 4, 2.5, 2.85);
	}

	@Test(timeout = 50)
	public void testNormalHardFourBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 4, 2.85, 4);
	}

	@Test(timeout = 50)
	public void testNormalEasyFiveBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 5, 1, 2.5);
	}

	@Test(timeout = 50)
	public void testNormalMediumFiveBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 5, 2.5, 2.85);
	}

	@Test(timeout = 50)
	public void testNormalHardFiveBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 5, 2.5, 4);
	}

	@Test(timeout = 50)
	public void testNormalEasySixBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 6, 1, 2.5);
	}

	@Test(timeout = 50)
	public void testNormalMediumSixBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 6, 2.5, 2.85);
	}

	@Test(timeout = 50)
	public void testNormalHardSixBoardGen() throws Exception {
		Board b = new Board(GameType.Normal, 6, 2.85, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromString() throws Exception {
		Board b = Board.fromString(normalBoard);

		Assert.assertEquals(GameType.Normal, b.GetGameType());
		Assert.assertTrue(Math.abs(b.GetDifficulty() - 10.157) < 0.001);
		b.GetJoins();
	}
}