package fakesetgame.seniordesign.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 9/10/2014.
 */
public enum Shape {
    Oval(1),
    Diamond(2),
    Squiggle(3);

    private int numVal;
    private static Map<Integer, Shape> shapeMap = new HashMap<Integer, Shape>();

    static{
        for(Shape shapeEnum: Shape.values()){
            shapeMap.put(shapeEnum.numVal, shapeEnum);
        }
    }

    private Shape(int numVal){
        this.numVal = numVal;
    }

    public int getNumVal(){
        return numVal;
    }

    public Shape valueOf(int numVal){
        return shapeMap.get(numVal);
    }
}
