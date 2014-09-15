package fakesetgame.seniordesign.model;

/**
 * Created by Chris on 9/10/2014.
 */
public enum Shading {
    Filled(1),
    Dashed(2),
    Hollow(3);

    private int numVal;
    Shading(int numVal){
        this.numVal = numVal;
    }

    public int getNumVal(){
        return numVal;
    }
}
