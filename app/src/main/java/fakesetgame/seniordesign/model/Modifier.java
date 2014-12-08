package fakesetgame.seniordesign.model;

/**
 * An enumeration of scoring modifiers used in calculating game difficulty.
 */
public enum Modifier {

    COUNT_DIFFERENCE(1.1),
    COLOR_DIFFERENCE(1.45),
    SHADING_DIFFERENCE(1.65),
    SHAPE_DIFFERENCE(1.9);

    private double modValue;

    private Modifier(final double modValue) {this.modValue = modValue;}

    public double getValue() {return modValue;}
    public static double minModifier = 1;
    public static double maxModifier = COUNT_DIFFERENCE.modValue * COLOR_DIFFERENCE.modValue * SHADING_DIFFERENCE.modValue * SHAPE_DIFFERENCE.modValue;
}
