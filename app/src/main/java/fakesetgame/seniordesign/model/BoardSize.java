package fakesetgame.seniordesign.model;

/**
 * Created by Jeremy on 10/24/2014.
 */
public enum BoardSize {

    NORMAL(9),
    LARGE(12);

    private int size;
    private BoardSize(int size) {this.size = size;}
    public int getValue() {return size;}
}
