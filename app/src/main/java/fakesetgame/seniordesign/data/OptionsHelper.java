package fakesetgame.seniordesign.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import fakesetgame.seniordesign.R;
import fakesetgame.seniordesign.model.Modifier;


/**
 * A class to assist in saving and retrieving specific game settings.
 */
public class OptionsHelper {

    public static int getHardness(Context ctx){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        return Integer.valueOf(sp.getString(ctx.getString(R.string.optDiffKey),
                ctx.getString(R.string.optDiffMidLevelValue)));
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        return Integer.valueOf(sp.getString(ctx.getString(R.string.optSparseKey),
                ctx.getString(R.string.optSparseMidLevelValue)));
    }
}
