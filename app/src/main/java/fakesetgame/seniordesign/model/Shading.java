package fakesetgame.seniordesign.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 9/10/2014.
 */
public enum Shading {
    Filled(1),
    Dashed(2),
    Hollow(3);

    private int numVal;
    private static Map<Integer, Shading> shadingMap = new HashMap<Integer, Shading>();

    static{
        for(Shading shadingEnum: Shading.values()){
            shadingMap.put(shadingEnum.numVal, shadingEnum);
        }
    }

    private Shading(final int numVal){
        this.numVal = numVal;
    }

    public int getNumVal(){
        return numVal;
    }

    public static Shading valueOf(int numVal){
        return shadingMap.get(numVal);
    }
}
