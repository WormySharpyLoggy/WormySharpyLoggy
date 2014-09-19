package fakesetgame.seniordesign.model;

/**
 * Created by Chris on 9/10/2014.
 */
public class TileSet {

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
}
