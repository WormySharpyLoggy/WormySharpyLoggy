/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.util;

import com.mischivous.wormysharpyloggy.wsl.model.*;
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
public class SetHelperTest {

	@Test(expected = NullPointerException.class)
	public void testNullTileSet() throws Exception {
		boolean b = SetHelper.IsValidSet(null, null, null);
		Assert.assertFalse(b);
		}

	@Test
	public void testIsValidSet() throws Exception {
		Tile t1 = Tile.GetTile(2, Color.Green, Fill.Hollow, Shape.Squiggle);
		Tile t2 = Tile.GetTile(3, Color.Red, Fill.Dashed, Shape.Oval);
		Tile t3 = Tile.GetTile(1, Color.Purple, Fill.Filled, Shape.Diamond);

		boolean b = SetHelper.IsValidSet(t1, t2, t3);
		Assert.assertTrue(b);
		}

	@Test(expected = NullPointerException.class)
	public void testNullTileDifficulty() throws Exception {

		double d = SetHelper.GetSetDifficulty(null, null, null);
		Assert.assertTrue(d == 0);
		}

	@Test
	public void testGetSetDifficulty() throws Exception {
		Tile t1 = Tile.GetTile(2, Color.Green, Fill.Hollow, Shape.Squiggle);
		Tile t2 = Tile.GetTile(3, Color.Red, Fill.Dashed, Shape.Oval);
		Tile t3 = Tile.GetTile(1, Color.Purple, Fill.Filled, Shape.Diamond);

		double d = SetHelper.GetSetDifficulty(t1, t2, t3);
		Assert.assertTrue(d == Modifier.maxModifier);

		d = SetHelper.GetSetDifficulty(t1, t1, t1);
		Assert.assertTrue(d == Modifier.minModifier);

		t2 = Tile.GetTile(2, Color.Green, Fill.Dashed, Shape.Diamond);
		t3 = Tile.GetTile(2, Color.Green, Fill.Filled, Shape.Oval);

		d = SetHelper.GetSetDifficulty(t1, t2, t3);
		Assert.assertTrue(d == Modifier.FILL_DIFFERENCE.GetValue() * Modifier.SHAPE_DIFFERENCE.GetValue());

		}

	@Test(expected = NullPointerException.class)
	public void testNullTileLastTile() throws Exception {
		Tile t = SetHelper.GetLastTile(null, null);
		}

	@Test
	public void testGetLastTile() throws Exception {
		Tile t1 = Tile.GetTile(2, Color.Green, Fill.Hollow, Shape.Squiggle);
		Tile t2 = Tile.GetTile(3, Color.Red, Fill.Dashed, Shape.Oval);
		Tile t3 = Tile.GetTile(1, Color.Purple, Fill.Filled, Shape.Diamond);

		Assert.assertEquals(SetHelper.GetLastTile(t1, t2), t3);
		}

	@Test(expected = NullPointerException.class)
	public void testNullBoardJoins() throws Exception {
		Set<Tile> s = SetHelper.GetJoins(null);
		}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongSizeJoins() throws Exception {
		Set<Tile> s = SetHelper.GetJoins(Tile.GetRandomTiles(8));
		}

	@Test
	public void testGetJoins() throws Exception {
		Board b = new Board(GameType.PowerSet, 3, 1, 4);

		String[] a = b.toString().replace("[", "").replace("]", "").split(", ");
		Tile[] t = new Tile[9];
		for (int i = 0; i < 9; i++) { t[i] = Tile.fromString(a[i+1]); }
		Assert.assertEquals(b.GetJoins(), SetHelper.GetJoins(t));
		}
}