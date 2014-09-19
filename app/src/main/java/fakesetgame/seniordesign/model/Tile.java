package fakesetgame.seniordesign.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Chris on 9/10/2014.
 */
public class Tile {

    private Shape shape;
    private Shading shading;
    private Color color;
    private int shapeCount;

    public Tile(Shape shape, Shading shading, Color color, int shapeCount) {

        if (shape == null) {
            throw new IllegalArgumentException("Shape value cannot be null.");
        }
        if (shading == null) {
            throw new IllegalArgumentException("Shading value cannot be null.");
        }
        if (color == null) {
            throw new IllegalArgumentException("Color value cannot be null.");
        }
        if (shapeCount < 1 || shapeCount > 3) {
            throw new IllegalArgumentException("Value of shapeCount must be between 1 and 3.");
        }

        this.shape = shape;
        this.shading = shading;
        this.color = color;
        this.shapeCount = shapeCount;
    }

    public Shape getShape() {
        return this.shape;
    }

    public Shading getShading() {
        return this.shading;
    }

    public Color getColor() {
        return this.color;
    }

    public int getShapeCount() {
        return this.shapeCount;
    }

    public Drawable getDrawable(Context context) {
        Drawable drawable = context.getResources().getDrawable(
                context.getResources().getIdentifier(
                        "tile_"
                                + Integer.valueOf(getShapeCount()).toString()
                                + Integer.valueOf(getShape().getNumVal()).toString()
                                + Integer.valueOf(getColor().getNumVal()).toString()
                                + Integer.valueOf(getShading().getNumVal()).toString(),
                        "drawable",
                        context.getPackageName()
                ));

        return drawable;
    }

    @Override
    public int hashCode() {
        return (Integer.valueOf(getShapeCount()).toString()
                + Integer.valueOf(getShape().getNumVal()).toString()
                + Integer.valueOf(getColor().getNumVal()).toString()
                + Integer.valueOf(getShading().getNumVal()).toString()).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;
        if(!(o instanceof Tile))
            return false;

        Tile other = (Tile)o;

        return getColor().equals(other.getColor())
                && getShape().equals(other.getShape())
                && getShading().equals(other.getShading())
                && getShapeCount() == other.getShapeCount();
    }
}
