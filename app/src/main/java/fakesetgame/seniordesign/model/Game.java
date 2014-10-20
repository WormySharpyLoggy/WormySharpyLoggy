package fakesetgame.seniordesign.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sami on 9/24/2014.
 */
public class Game {

    public static final int SETS = 6;
    public static final int TILES_IN_A_SET = 3;

    Set<GameOverListener> gameOverListeners = new HashSet<GameOverListener>();
    public final Board board;
    private final int sets;
    private final List<FoundSet> foundSetList;

    private Date startTime;
    private long accumulatedTime;
    private boolean active;
    private boolean gameOver;

    public void addGameOverListener(GameOverListener listener) {
        gameOverListeners.add(listener);
    }

    private void onGameOver() {
        pauseTimer();
        gameOver = true;

        GameOverEvent e = new GameOverEvent(this);
        for (GameOverListener listener : gameOverListeners)
            listener.gameOver(e);
    }

    public Game() {
        sets = SETS;
        board = Board.generateRandom(sets);
        foundSetList = new ArrayList<FoundSet>();
        restartTimer();
    }

    public boolean attemptSet(Tile tile1, Tile tile2, Tile tile3) {
        if (TileSet.isValidSet(tile1, tile2, tile3)) {
            long elapsed = getElapsedTime();

            FoundSet tileSet = new FoundSet(
                    new HashSet<Tile>(Arrays.asList(tile1, tile2, tile3)),
                    elapsed,
                    elapsed - (foundSetList.size() == 0 ? 0 : foundSetList.get(foundSetList.size() - 1).getTotalElapsed())
            );

            if (!foundSetList.contains(tileSet)) {
                foundSetList.add(tileSet);
                if (getFoundSetCount() == getBoardSetCount()) {
                    onGameOver();
                }

                return true;
            }
        }
        return false;
    }

    public int getBoardSetCount() {
        return sets;
    }


    public List<FoundSet> getFoundSetList() {
        return foundSetList;
    }

    public int getFoundSetCount() {
        return foundSetList.size();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return foundSetList.size();
    }

    public void restartTimer() {
        startTime = new Date();
        accumulatedTime = 0;
        active = true;
        gameOver = false;
    }

    public void pauseTimer() {
        if (active) {
            accumulatedTime += new Date().getTime() - startTime.getTime();
            active = false;
        }
    }

    public void unpauseTimer() {
        if (!active && !gameOver) {
            startTime = new Date();
            active = true;
        }
    }

    /**
     * Gets the time elapsed since the game started, in milliseconds
     *
     * @return Elapsed play time in milliseconds
     */
    public long getElapsedTime() {
        long elapsed = accumulatedTime;
        if (active)
            elapsed += new Date().getTime() - startTime.getTime();
        return elapsed;
    }

    public class FoundSet {
        private final Set<Tile> tileSet;
        private final long totalElapsed;
        private final long deltaElapsed;

        public Set<Tile> getTileSet() {
            return new HashSet<Tile>(tileSet);
        }

        public long getTotalElapsed() {
            return totalElapsed;
        }

        public long getDeltaElapsed() {
            return deltaElapsed;
        }

        public FoundSet(Collection<Tile> tileSet, long totalElapsed, long deltaElapsed) {
            this.tileSet = new HashSet<Tile>(tileSet);
            this.totalElapsed = totalElapsed;
            this.deltaElapsed = deltaElapsed;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            return o instanceof FoundSet && tileSet.equals(((FoundSet) o).tileSet);
        }

        @Override
        public int hashCode() {
            return tileSet.hashCode();
        }
    }
}
