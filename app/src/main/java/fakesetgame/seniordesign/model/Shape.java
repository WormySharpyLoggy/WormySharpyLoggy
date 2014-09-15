package fakesetgame.seniordesign.model;

/**
 * Created by Chris on 9/10/2014.
 */
public enum Shape {
    Oval(1),
    Diamond(2),
    Squiggle(3);

    private int numVal;
    Shape(int numVal){
        this.numVal = numVal;
    }

    public int getNumVal(){
        return numVal;
    }
}
