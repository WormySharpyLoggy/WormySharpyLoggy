package fakesetgame.seniordesign;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;

import java.util.Arrays;
import java.util.Random;

import fakesetgame.seniordesign.model.Color;
import fakesetgame.seniordesign.model.TileSet;
import fakesetgame.seniordesign.model.Shading;
import fakesetgame.seniordesign.model.Shape;
import fakesetgame.seniordesign.model.Tile;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    Random random = new Random();

    public void testSetEvaluation_SameEverything() throws Exception{

        for(int i=0; i<100; i++) {
            Shape shape = Shape.valueOf(random.nextInt(3) + 1);
            Shading shading = Shading.valueOf(random.nextInt(3) + 1);
            Color color = Color.valueOf(random.nextInt(3) + 1);
            int shapeNum = random.nextInt(3) + 1;

            Tile t1 = new Tile(shape, shading, color, shapeNum);
            Tile t2 = new Tile(shape, shading, color, shapeNum);
            Tile t3 = new Tile(shape, shading, color, shapeNum);

            Assert.assertTrue(TileSet.isValidSet(t1, t2, t3));

            Tile badTile = new Tile(shape, shading, color, shapeNum % 3 + 1);
            Assert.assertFalse(TileSet.isValidSet(t1, badTile, t2));
        }
    }

    public void testSetEvaluation_DifferentEverything() throws Exception{
        int[] seed = new int[]{1,2,3};

        int[] rand3 = randomize(seed);
        Color[] colors = new Color[3];
        for(int i=0; i<3; i++)
            colors[i] = Color.valueOf(rand3[i]);

        rand3 = randomize(seed);
        Shading[] shadings = new Shading[3];
        for(int i=0; i<3; i++)
            shadings[i] = Shading.valueOf(rand3[i]);

        rand3 = randomize(seed);
        Shape[] shapes = new Shape[3];
        for(int i=0; i<3; i++)
            shapes[i] = Shape.valueOf(rand3[i]);

        int[] quantities = randomize(seed);

        Tile[] tiles = new Tile[3];
        for(int i=0; i<3; i++)
            tiles[i] = new Tile(shapes[i], shadings[i], colors[i], quantities[i]);

        Assert.assertTrue(TileSet.isValidSet(tiles[0], tiles[1], tiles[2]));
    }

    private int[] randomize(int[] input){
        int[] copy = Arrays.copyOf(input, input.length);

        for(int i=0; i<copy.length; i++){
            int j = random.nextInt(copy.length);
            int temp = copy[j];
            copy[j] = copy[i];
            copy[i] = temp;
        }

        return copy;
    }
}