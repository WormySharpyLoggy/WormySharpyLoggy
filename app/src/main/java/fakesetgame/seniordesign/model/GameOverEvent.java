package fakesetgame.seniordesign.model;

import java.util.EventObject;

/**
 * An event fired when a game is over.
 */
public class GameOverEvent extends EventObject {

    public GameOverEvent(Object source){
        super(source);
    }
}
