/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import com.google.common.base.Stopwatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

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
	public void testNormalEasyThreeBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 3, 1, medCutoff);
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
	public void testNormalMediumThreeBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 3, medCutoff, hardCutoff);
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
	public void testNormalHardThreeBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 3, hardCutoff, 4);
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
	public void testNormalEasyFourBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 4, 1, medCutoff);
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
	public void testNormalMediumFourBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 4, medCutoff, hardCutoff);
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
	public void testNormalHardFourBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 4, hardCutoff, 4);
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
	public void testNormalEasyFiveBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 5, 1, medCutoff);
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
	public void testNormalMediumFiveBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 5, medCutoff, hardCutoff);
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
	public void testNormalHardFiveBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 5, hardCutoff, 4);
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
	public void testNormalEasySixBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 6, 1, medCutoff);
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
	public void testNormalMediumSixBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 6, medCutoff, hardCutoff);
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
	public void testNormalHardSixBoardGen() throws Exception {
		Stopwatch sw = Stopwatch.createUnstarted();
		long totalTime = 0;
		int fastRuns = 0;

		for (int i = 0; i < totalRuns; i++) {
			sw.start();
			Board b = new Board(GameType.Normal, 6, hardCutoff, 4);
			sw.stop();
			long runTime = sw.elapsed(TimeUnit.MILLISECONDS);
			sw.reset();

			if (runTime < maxTime) { fastRuns += 1; }
			totalTime +=  runTime;
		}

		Assert.assertTrue("Did not meet minimum number of fast runs", fastRuns > threshold);
		Assert.assertTrue("Average time to generate board too high", totalTime / totalRuns < maxTime);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFromString() throws Exception {
		Board b = Board.fromString(normalBoard);

		Assert.assertEquals(GameType.Normal, b.GetGameType());
		Assert.assertTrue(Math.abs(b.GetDifficulty() - 10.157) < 0.001);
		b.GetJoins();
	}
}