package fakesetgame.seniordesign.model;

/**
 * Created by Chris on 9/10/2014.
 */
public class Tile {

    private Shape shape;
    private Shading shading;
    private Color color;

    public Tile(Shape shape, Shading shading, Color color, int shapeCount){

        if (shapeCount < 1 || shapeCount > 3){
            throw new IllegalArgumentException("Value of shapeCount must be between 1 and 3.");
        }
        
        this.shape = shape;
        this.shading = shading;
        this.color = color;
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
}
