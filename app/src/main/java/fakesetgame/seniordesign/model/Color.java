package fakesetgame.seniordesign.model;

/**
 * Created by Chris on 9/10/2014.
 */
public enum Color {
    Red(1),
    Green(2),
    Purple(3);

    private int numVal;
    Color(int numVal){
        this.numVal = numVal;
    }

    public int getNumVal(){
        return numVal;
    }
}
