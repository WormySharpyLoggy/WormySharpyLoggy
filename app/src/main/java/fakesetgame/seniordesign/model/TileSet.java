package fakesetgame.seniordesign.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Chris on 9/10/2014.
 */
public class TileSet {

    private static final Random random = new Random();
    private static final Set<Tile> allTiles;

    static{
        allTiles = new HashSet<Tile>();
        for(int num=1; num<=3; num++)
            for(int shape=1; shape<=3; shape++)
                for(int shading=1; shading<=3; shading++)
                    for(int color=1; color<=3; color++)
                        allTiles.add(new Tile(Shape.valueOf(shape), Shading.valueOf(shading), Color.valueOf(color), num));
    }

    public static boolean isValidSet(Tile tile1, Tile tile2, Tile tile3) {
        if (tile1 == null || tile2 == null || tile3 == null) {
            return false;
        }

        boolean colorsAllTheSame = tile1.getColor() == tile2.getColor()
                && tile2.getColor() == tile3.getColor();
        boolean colorsAllDifferent = tile1.getColor() != tile2.getColor()
                && tile2.getColor() != tile3.getColor()
                && tile3.getColor() != tile1.getColor();

        boolean shapesAllTheSame = tile1.getShape() == tile2.getShape()
                && tile2.getShape() == tile3.getShape();
        boolean shapesAllDifferent = tile1.getShape() != tile2.getShape()
                && tile2.getShape() != tile3.getShape()
                && tile3.getShape() != tile1.getShape();

        boolean shadingsAllTheSame = tile1.getShading() == tile2.getShading()
                && tile2.getShading() == tile3.getShading();
        boolean shadingsAllDifferent = tile1.getShading() != tile2.getShading()
                && tile2.getShading() != tile3.getShading()
                && tile3.getShading() != tile1.getShading();

        boolean countsAllTheSame = tile1.getShapeCount() == tile2.getShapeCount()
                && tile2.getShapeCount() == tile3.getShapeCount();
        boolean countsAllDifferent = tile1.getShapeCount() != tile2.getShapeCount()
                && tile2.getShapeCount() != tile3.getShapeCount()
                && tile3.getShapeCount() != tile1.getShapeCount();

        return (colorsAllTheSame || colorsAllDifferent)
                && (shapesAllTheSame || shapesAllDifferent)
                && (shadingsAllTheSame || shadingsAllDifferent)
                && (countsAllTheSame || countsAllDifferent);
    }

    public static double getSetDifficulty (Tile tile1, Tile tile2, Tile tile3) {
        double diff = 1;

        if (!isValidSet(tile1, tile2, tile3)) {
            return 0;
        }

        boolean colorsAllDifferent = tile1.getColor() != tile2.getColor()
                && tile2.getColor() != tile3.getColor()
                && tile3.getColor() != tile1.getColor();
        diff *= colorsAllDifferent ? Modifier.COLOR_DIFFERENCE.getValue() : 1;

        boolean shapesAllDifferent = tile1.getShape() != tile2.getShape()
                && tile2.getShape() != tile3.getShape()
                && tile3.getShape() != tile1.getShape();
        diff *= shapesAllDifferent ? Modifier.SHAPE_DIFFERENCE.getValue() : 1;

        boolean shadingsAllDifferent = tile1.getShading() != tile2.getShading()
                && tile2.getShading() != tile3.getShading()
                && tile3.getShading() != tile1.getShading();
        diff *= shadingsAllDifferent ? Modifier.SHADING_DIFFERENCE.getValue() : 1;

        boolean countsAllDifferent = tile1.getShapeCount() != tile2.getShapeCount()
                && tile2.getShapeCount() != tile3.getShapeCount()
                && tile3.getShapeCount() != tile1.getShapeCount();
        diff *= countsAllDifferent ? Modifier.COUNT_DIFFERENCE.getValue() : 1;

        return diff;
    }

    public static double getSetDifficulty(Set<Tile> tileSet) {
        if (tileSet.size() != 3)
            return 0;

        Tile[] tileAry = tileSet.toArray(new Tile[3]);

        return getSetDifficulty(tileAry[0], tileAry[1], tileAry[2]);
    }

    public static Set<Set<Tile>> getSets(Tile... tiles){
        Set<Set<Tile>> sets = new HashSet<Set<Tile>>();

        for(int i=0; i<tiles.length; i++)
            for(int j=i+1; j<tiles.length; j++)
                for(int k=j+1; k<tiles.length; k++)
                    if(isValidSet(tiles[i], tiles[j], tiles[k]))
                        sets.add(new HashSet<Tile>(Arrays.asList(tiles[i], tiles[j], tiles[k])));

        return sets;
    }

    public static int countSets(Tile... tiles){
        int setCount = 0;

        for(int i=0; i<tiles.length; i++)
            for(int j=i+1; j<tiles.length; j++)
                for(int k=j+1; k<tiles.length; k++)
                    if(isValidSet(tiles[i], tiles[j], tiles[k]))
                        setCount++;

        return setCount;
    }

    public static int countSetsContainingTile(Tile tile, Tile... tiles){
        int setCount = 0;

        // check each pair of tiles from the remainder of the set
        // and see if it makes a set with the provided tile
        for(int i=0; i<tiles.length; i++)
            for(int j=i+1; j<tiles.length; j++)
                if(
                        !tile.equals(tiles[i])
                        && !tile.equals(tiles[j])
                        && isValidSet(tile, tiles[i], tiles[j])
                        )
                    setCount++;

        return setCount;
    }

    public static Set<Tile> getRandom(int count){

        if(count > allTiles.size())
            throw new IllegalArgumentException(String.format("There are only %d distinct tiles. Cannot create a random set of %d.", allTiles.size(), count));
        if(count < 0)
            throw new IllegalArgumentException("Cannot create a set of negative size.");

        Tile[] tiles = allTiles.toArray(new Tile[allTiles.size()]);
        Set<Tile> randSet = new HashSet<Tile>();

        for(int i=0; i<count; i++){
            // pick a random element from zero to (length-i)
            // add that element to the set
            // move the (length-i)th element to the spot we just took a tile from
            int idx = random.nextInt(tiles.length - i);
            randSet.add(tiles[idx]);
            tiles[idx] = tiles[tiles.length-i-1];
        }

        return randSet;
    }
}
