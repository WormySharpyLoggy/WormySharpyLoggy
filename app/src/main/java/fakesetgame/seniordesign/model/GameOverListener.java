package fakesetgame.seniordesign.model;

import java.util.EventListener;

/**
 * An interface for a Game Over event listener.
 */
public interface GameOverListener extends EventListener {
    void gameOver(GameOverEvent e);
}
