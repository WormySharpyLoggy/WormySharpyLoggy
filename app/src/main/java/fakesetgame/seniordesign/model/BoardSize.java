package fakesetgame.seniordesign.model;

/**
 * A enumeration of available board sizes.
 */
public enum BoardSize {

    NORMAL(9),
    LARGE(12);

    private int size;
    private BoardSize(int size) {this.size = size;}
    public int getValue() {return size;}
}
