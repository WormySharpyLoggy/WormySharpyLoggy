package fakesetgame.seniordesign.data;

import android.content.Context;

import java.lang.reflect.Modifier;

/**
 * Created by Jeremy on 10/31/2014.
 */
public class OptionsHelper {

    public static int getHardness(Context ctx){
        Setting set = PlayerDataDbHelper.getSetting(ctx, "difficulty");
        if (set == null) return 1;
        return Integer.parseInt(set.getValue());
    }

    public static double getMinDiff(Context ctx) {
        switch (getHardness(ctx)) {
            case 2:
                return getSetCount(ctx) * Modifier.COUNT_DIFFERENCE.getValue() *
                        Modifier.COLOR_DIFFERENCE.getValue() * Modifier.SHAPE_DIFFERENCE.getValue();
            case 1:
                return getSetCount(ctx) * Modifier.COUNT_DIFFERENCE.getValue() *
                        Modifier.SHADING_DIFFERENCE.getValue();
            default:
                return getSetCount(ctx) * Modifier.minModifier;
        }
    }

    public static double getMaxDiff(Context ctx) {
        switch (getHardness(ctx)) {
            case 2:
                return getSetCount(ctx) * Modifier.maxModifier;
            case 1:
                return getSetCount(ctx) * Modifier.COUNT_DIFFERENCE.getValue() *
                        Modifier.COLOR_DIFFERENCE.getValue() * Modifier.SHAPE_DIFFERENCE.getValue();
            default:
                return getSetCount(ctx) * Modifier.COUNT_DIFFERENCE.getValue() *
                        Modifier.SHADING_DIFFERENCE.getValue();
        }
    }

    public static int getSetCount(Context ctx) {
        Setting set = PlayerDataDbHelper.getSetting(ctx, "setcount");
        if (set == null) return 4;
        return Integer.parseInt(set.getValue());
    }

    public static void setHardness(Context ctx, Integer newDiff) {
        PlayerDataDbHelper.saveSetting(ctx, "difficulty", newDiff.toString());
    }

    public static void setSetCount(Context ctx, int newCount) {
        Integer normCount;
        switch (newCount) {
            case 2:
                normCount = 5;
                break;
            case 1:
                normCount = 4;
                break;
            default:
                normCount = 3;
                break;
        }
        PlayerDataDbHelper.saveSetting(ctx, "setcount", normCount.toString());
    }
}
