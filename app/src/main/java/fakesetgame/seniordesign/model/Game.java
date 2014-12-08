package fakesetgame.seniordesign.model;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class to model an instance of a game and the player's interaction with the game.
 */
public class Game {

    public static final int TILES_IN_A_SET = 3;

    Set<GameOverListener> gameOverListeners = new HashSet<GameOverListener>();
    public final Board board;
    public final HintProvider hintProvider = new HintProvider(this);
    private final int sets;
    private final List<FoundSet> foundSetList;
    private final GameType gameType;
    private final Handler handler = new Handler();


    private Date startTime;
    private long accumulatedTime;
    private boolean paused;
    private boolean gameOver;
    private boolean hintUsed = false;
    private Outcome outcome;
    private Timer checkTimeRemainingTimer = null;

    private final Runnable checkGameOver = new Runnable() {

        public void run() {
            if (!paused && getGameType() == GameType.TimeAttack && getTimeRemaining() <= 0) {
                onGameOver(Outcome.Lose);
            }
        }

    };

    public void addGameOverListener(GameOverListener listener) {
        gameOverListeners.add(listener);
    }

    private void onGameOver(Outcome outcome) {
        if(isGameOver()) return; // we already did this

        pauseTimer();
        gameOver = true;
        this.outcome = outcome;

        if(checkTimeRemainingTimer != null)
            checkTimeRemainingTimer.cancel();

        GameOverEvent e = new GameOverEvent(this);
        for (GameOverListener listener : gameOverListeners)
            listener.gameOver(e);
    }

    public Game(GameType gameType, int setCount, double minDiff, double maxDiff) {
        sets = setCount;
        board = Board.generateRandom(setCount, minDiff, maxDiff);
        foundSetList = new ArrayList<FoundSet>();
        restartTimer();
        this.gameType = gameType;

        if(getGameType() == GameType.TimeAttack) {
            checkTimeRemainingTimer = new Timer();
            checkTimeRemainingTimer.schedule(new TimerTask() {
                public void run() {
                    handler.post(checkGameOver);
                }
            }, 0, 200);
        }
    }

    public GameType getGameType() {
        return gameType;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public boolean isFound(Collection<Tile> tiles) {
        FoundSet tileSet = new FoundSet(tiles);
        return foundSetList.contains(tileSet);
    }

    public boolean attemptSet(Tile tile1, Tile tile2, Tile tile3) {
        if (getGameType() == GameType.TimeAttack) {
            checkGameOver.run();
        }
        if (TileSet.isValidSet(tile1, tile2, tile3)) {
            FoundSet tileSet = new FoundSet(
                    new HashSet<Tile>(Arrays.asList(tile1, tile2, tile3)));

            if (!foundSetList.contains(tileSet)) {
                foundSetList.add(tileSet);
                if (getFoundSetCount() == getBoardSetCount()) {
                    onGameOver(Outcome.Win);
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

    public void setHintUsed() {
        hintUsed = true;
    }

    public boolean wasHintUsed() {
        return hintUsed;
    }

    public void restartTimer() {
        startTime = new Date();
        accumulatedTime = 0;
        paused = false;
        gameOver = false;
    }

    public void pauseTimer() {
        if (!paused) {
            accumulatedTime += new Date().getTime() - startTime.getTime();
            paused = true;
        }
    }

    public void unpauseTimer() {
        if (paused && !gameOver) {
            startTime = new Date();
            paused = false;
        }
    }

    /**
     * Gets the time elapsed since the game started, in milliseconds
     *
     * @return Elapsed play time in milliseconds
     */
    public long getElapsedTime() {
        long elapsed = accumulatedTime;
        if (!paused)
            elapsed += new Date().getTime() - startTime.getTime();
        return elapsed;
    }

    public long getTimeRemaining() {
        if (getGameType() != GameType.TimeAttack) {
            return -1;
        }
        return ((15 + (10 * foundSetList.size())) - (getElapsedTime() / 1000))*(1000);
    }

    /* Inner Classes and Enums */
    public class FoundSet {
        private final Set<Tile> tileSet;
        private final long totalElapsed;
        private final long deltaElapsed;
        private final boolean gotHints;

        public Set<Tile> getTileSet() {
            return new HashSet<Tile>(tileSet);
        }

        public long getTotalElapsed() {
            return totalElapsed;
        }

        public long getDeltaElapsed() {
            return deltaElapsed;
        }

        public boolean wasHintProvided() {
            return gotHints;
        }

        public FoundSet(Collection<Tile> tileSet) {
            this.tileSet = new HashSet<Tile>(tileSet);
            this.gotHints = hintProvider.wasHintProvided(new HashSet<Tile>(tileSet));
            this.totalElapsed = getElapsedTime();
            this.deltaElapsed = this.totalElapsed - (foundSetList.size() == 0 ? 0 : foundSetList.get(foundSetList.size() - 1).getTotalElapsed());
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

    /**
     * An enumeration of different modes of play.
     */
    public enum GameType {
        TimeAttack,
        Normal
    }

    /**
     * An enumeration of possible game outcomes (win/lose).
     */
    public enum Outcome {
        Win,
        Lose
    }
}
