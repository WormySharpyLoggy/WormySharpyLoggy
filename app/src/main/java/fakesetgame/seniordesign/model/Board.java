package fakesetgame.seniordesign.model;

/**
 * Created by Sami on 9/17/2014.
 */
public class Board {
    private Tile[][] board;
    private int height;
    private int width;
    private static final int BOARD_SIZE = 3;

    public Board() {
        this(BOARD_SIZE, BOARD_SIZE);
    }

    public Board(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width must both be positive.");
        }

        this.height = height;
        this.width = width;
        board = new Tile[height][width];
    }


    public Tile getTile(int row, int column) {
        if (row < 0 || row >= board.length) {
            throw new IllegalArgumentException("Row out of bounds.");
        }
        if (column < 0 || column >= board[row].length) {
            throw new IllegalArgumentException("Column out of bounds.");
        }

        return board[row][column];

    }
}
