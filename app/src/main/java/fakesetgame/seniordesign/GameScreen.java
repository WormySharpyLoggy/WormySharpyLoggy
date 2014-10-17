package fakesetgame.seniordesign;

import fakesetgame.seniordesign.data.PlayerDataDbHelper;
import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.model.Game;
import fakesetgame.seniordesign.model.GameOverEvent;
import fakesetgame.seniordesign.model.GameOverListener;
import fakesetgame.seniordesign.model.Tile;
import fakesetgame.seniordesign.util.SystemUiHider;
import fakesetgame.seniordesign.view.ShadedImageView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameScreen extends Activity implements View.OnClickListener, GameOverListener {

    private static final String TAG = "GameScreen";
    private ShadedImageView[] tiles = new ShadedImageView[Board.TILES];
    private ImageView[][] found = new ImageView[Game.SETS][Game.TILES_IN_A_SET];
    private TextView timeView = null;
    private final Handler handler = new Handler();
    private Game game = null;
    private List<Tile> selectedTiles = new ArrayList<Tile>();

    private final Runnable updateClock = new Runnable(){
        @Override
        public void run() {
            long elapsedSeconds = game.getElapsedTime() / 1000;
            timeView.setText(String.format("%d:%02d", elapsedSeconds / 60, elapsedSeconds % 60));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        // Init board imageviews
        for (int i = 0; i < Board.TILES; i++) {
            tiles[i] = (ShadedImageView) findViewById(getResources().getIdentifier(
                    "Tile" + Integer.valueOf(i + 1).toString(),
                    "id",
                    getPackageName()));
            tiles[i].setTag(R.id.TILE_INDEX, i);
            tiles[i].setOnClickListener(this);

            if (tiles[i] == null)
                Log.e(TAG, String.format("Failed to set tiles[%d]. Value is null.", i));
        }

        // Init found set imageviews
        for (int set = 0; set < found.length; set++) {
            for (int tile = 0; tile < found[set].length; tile++) {
                found[set][tile] = (ImageView) findViewById(getResources().getIdentifier(
                        "FoundSet" + Integer.valueOf(set + 1).toString() + "_" + Integer.valueOf(tile + 1).toString(),
                        "id",
                        getPackageName()));

                if (found[set][tile] == null)
                    Log.e(TAG, String.format("Failed to set found[%d][%d]. Value is null.", set, tile));
            }
        }

        newGame();

        timeView = (TextView)findViewById(R.id.TimeView);
        Timer clockUpdateTimer = new Timer();
        clockUpdateTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                handler.post(updateClock);
            }
        }, 0, 200);
    }

    @Override
    protected void onStop() {
        super.onStop();

        game.pauseTimer();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        game.unpauseTimer();
    }

    private void setTileSelected(int tileIndex, boolean selected){
        if(tileIndex < 0 || tileIndex > Board.TILES)
            throw new IllegalArgumentException("tileIndex out of bounds.");

        if(getTileSelected(tileIndex) == selected)
            return;

        tiles[tileIndex].setShaded(selected);
        Log.d(TAG, String.format("Setting tile %d shading = %s", tileIndex, Boolean.valueOf(selected).toString()));

        if(selected){
            selectedTiles.add(game.board.getTile(tileIndex));

            // this should call Game class, but it isn't finished yet
            if(selectedTiles.size()==3){
                Tile[] tiles = selectedTiles.toArray(new Tile[3]);
                if(game.attemptSet(tiles[0], tiles[1], tiles[2])){
                    messageUser("Good job!");
                    int set = game.getFoundSetCount() - 1;

                    found[set][0].setImageDrawable(tiles[0].getDrawable(this));
                    found[set][1].setImageDrawable(tiles[1].getDrawable(this));
                    found[set][2].setImageDrawable(tiles[2].getDrawable(this));
                }
                else{
                    messageUser("Try again");
                }
                clearTileSelection();
            }
        }
        else{
            selectedTiles.remove(game.board.getTile(tileIndex));
        }
    }

    @Override
    public void gameOver(GameOverEvent e) {
        PlayerDataDbHelper.saveOutcome(this, game);
    }

    private boolean getTileSelected(int tileIndex){
        if(tileIndex < 0 || tileIndex > Board.TILES)
            throw new IllegalArgumentException("tileIndex out of bounds.");

        return tiles[tileIndex].getShaded();
    }

    private void clearTileSelection(){
        selectedTiles.clear();
        for(int i=0; i<Board.TILES; i++)
            setTileSelected(i, false);
    }

    public void onClick(View view){
        Object o = view.getTag(R.id.TILE_INDEX);
        if(view instanceof ImageView && o instanceof Integer){
            if(game.isGameOver())
                return;

            int idx = (Integer)o;
            Log.d(TAG, "Event: onClick for Tile " + idx);

            setTileSelected(idx, !getTileSelected(idx));
        }
    }

    private void messageUser(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    private void newGame() {
        game = new Game();
        game.addGameOverListener(this);

        clearTileSelection();

        for (int i = 0; i < tiles.length; i++) {
            tiles[i].setImageDrawable(game.board.getTile(i).getDrawable(this));
            Log.d(TAG, String.format("Set tile image for tiles[%d]", i));
        }

        for(int set = 0; set < found.length; set++)
            for(int tile = 0; tile < found[set].length; tile++)
                found[set][tile].setImageDrawable(getResources().getDrawable(R.drawable.tile_blank));
    }
}
