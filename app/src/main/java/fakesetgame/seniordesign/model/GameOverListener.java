package fakesetgame.seniordesign.model;

import java.util.EventListener;

/**
 * Created by Chris on 10/17/2014.
 */
public interface GameOverListener extends EventListener {
    void gameOver(GameOverEvent e);
}
