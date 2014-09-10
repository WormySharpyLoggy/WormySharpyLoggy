package fakesetgame.seniordesign.model;

/**
 * Created by Chris on 9/10/2014.
 */
public class Tile {

    private Shape shape;
    private Shading shading;
    private Color color;
    private int shapeCount;

    public Tile(Shape shape, Shading shading, Color color, int shapeCount){

        if(shape == null){
            throw new IllegalArgumentException("Shape value cannot be null.");
        }
        if(shading == null){
            throw new IllegalArgumentException("Shading value cannot be null.");
        }
        if(color == null){
            throw new IllegalArgumentException("Color value cannot be null.");
        }
        if (shapeCount < 1 || shapeCount > 3){
            throw new IllegalArgumentException("Value of shapeCount must be between 1 and 3.");
        }

        this.shape = shape;
        this.shading = shading;
        this.color = color;
        this.shapeCount = shapeCount;
    }

    public Shape getShape(){
        return this.shape;
    }

    public Shading getShading(){
        return this.shading;
    }

    public Color getColor(){
        return this.color;
    }

    public int getShapeCount(){
        return this.shapeCount;
    }
}
