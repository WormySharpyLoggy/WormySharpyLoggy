/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

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

	// Per-set difficulty cutoff b/w easy and medium
	double medCutoff = 2.5;

	// Per-set difficulty cutoff b/w medium and hard
	double hardCutoff = 2.85;

	// Number of iterations to run when calculating board generation stats
	int totalRuns = 100;

	// Number of iterations that must be faster than maximum time for test to pass
	int threshold = 95;

	// Maximum amount of time used to generate board in milliseconds
	int maxTime = 50;

	@Test
	public void testPowerEasyThreeBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 3, 1, medCutoff);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerMediumThreeBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 3, medCutoff, hardCutoff);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerHardThreeBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 3, hardCutoff, 4);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerEasyFourBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 4, 1, medCutoff);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerMediumFourBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 4, medCutoff, hardCutoff);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerHardFourBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 4, hardCutoff, 4);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerEasyFiveBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 5, 1, medCutoff);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerMediumFiveBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 5, medCutoff, hardCutoff);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerHardFiveBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 5, hardCutoff, 4);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerEasySixBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 6, 1, medCutoff);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerMediumSixBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 6, medCutoff, hardCutoff);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test
	public void testPowerHardSixBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.PowerSet, 6, hardCutoff, 4);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
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