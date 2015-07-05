/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * A brief description of the class, to be filled in later
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since Jul 02, 2015
 */
public class TileTest {
	@Test(expected = IllegalArgumentException.class)
	public void testBadCount() throws Exception {
		Tile t = new Tile(4, Color.Green, Fill.Dashed, Shape.Diamond);
	}

	@Test(expected = NullPointerException.class)
	public void testBadShape() throws Exception {
		Tile t = new Tile(3, Color.Green, Fill.Dashed, null);
	}

	@Test(expected = NullPointerException.class)
	public void testBadFill() throws Exception {
		Tile t = new Tile(3, Color.Green, null, Shape.Diamond);
	}

	@Test(expected = NullPointerException.class)
	public void testBadColor() throws Exception {
		Tile t = new Tile(3, null, Fill.Dashed, Shape.Diamond);
	}

	@Test
	public void testGetTile() throws Exception {
		for (Shape sh : Shape.values()) {
			for (Fill fi : Fill.values()) {
				for (Color co : Color.values())
					for (int i = 1; i < 4; i++) {
						Assert.assertEquals(new Tile(i, co, fi, sh), Tile.GetTile(i, co, fi, sh));
					}
				}
			}

		}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTooFewRandoms() throws Exception {
		Tile[] t = Tile.GetRandomTiles(0);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTooManyRandoms() throws Exception {
		Tile[] t = Tile.GetRandomTiles(82);
		}

	@Test
	public void testGetRandomTiles() throws Exception {
		Random r = new Random();
		int i = r.nextInt(81);
		Assert.assertEquals(i, Tile.GetRandomTiles(i).length);
		}

	@Test
	public void testToString() throws Exception {
		String str = "1 Green Dashed Squiggle";
		Tile t = Tile.GetTile(1, Color.Green, Fill.Dashed, Shape.Squiggle);
		Assert.assertEquals(str, t.toString());
		}

	@Test
	public void testFromString() throws Exception {
		String str = "1 Green Dashed Squiggle";
		Tile t = Tile.GetTile(1, Color.Green, Fill.Dashed, Shape.Squiggle);
		Assert.assertEquals(t, Tile.fromString(str));
		}

	@Test(expected = IllegalArgumentException.class)
	public void testGetWrongTileCount() throws Exception {
		Tile[] t = Tile.GetTilesForBoard(12, 6);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testGetTooManyRandomStarters() throws Exception {
		Tile[] t = Tile.GetTilesForBoard(9, 10);
		}

}