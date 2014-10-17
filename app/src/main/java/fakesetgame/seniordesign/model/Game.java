package fakesetgame.seniordesign.model;

import java.util.Date;
import java.util.HashSet;
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
    private final Set<Set<Tile>> trackSet;

    private Date startTime;
    private long accumulatedTime;
    private boolean active;
    private boolean gameOver;

    public void addGameOverListener(GameOverListener listener){
        gameOverListeners.add(listener);
    }

    private void onGameOver(){
        pauseTimer();
        gameOver = true;

        GameOverEvent e = new GameOverEvent(this);
        for(GameOverListener listener: gameOverListeners)
            listener.gameOver(e);
    }

    public Game() {
        sets = SETS;
        board = Board.generateRandom(sets);
        trackSet = new HashSet<Set<Tile>>();
        restartTimer();
    }

    public boolean attemptSet(Tile tile1, Tile tile2, Tile tile3) {
        if (TileSet.isValidSet(tile1, tile2, tile3)) {
            Set<Tile> tileSet = new HashSet<Tile>();
            tileSet.add(tile1);
            tileSet.add(tile2);
            tileSet.add(tile3);
            if (trackSet.add(tileSet)) {
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

    public int getFoundSetCount() {
        return trackSet.size();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getScore() {
        return trackSet.size();
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
}
