package fakesetgame.seniordesign.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 9/10/2014.
 */
public enum Color {
    Red(1),
    Green(2),
    Purple(3);

    private int numVal;
    private static Map<Integer, Color> colorMap = new HashMap<Integer, Color>();

    static {
        for(Color colorEnum: Color.values()){
            colorMap.put(colorEnum.numVal, colorEnum);
        }
    }

    private Color(final int numVal){
        this.numVal = numVal;
    }

    public int getNumVal(){
        return numVal;
    }

    public static Color valueOf(int numVal){
        return colorMap.get(numVal);
    }
}
