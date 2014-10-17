package fakesetgame.seniordesign.data;

import java.util.Date;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.model.Game;

/**
 * Created by Chris on 10/17/2014.
 */
public class GameOutcome {
    public final long _id;
    public final Board board;
    public final long elapsed;
    public final Date inserted;

    public GameOutcome(long _id, String board, long elapsed, long inserted){
        this._id = _id;
        this.board = Board.fromString(board);
        this.elapsed = elapsed;
        this.inserted = new Date(inserted);
    }
}
