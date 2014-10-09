package fakesetgame.seniordesign;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.util.SystemUiHider;
import fakesetgame.seniordesign.view.ShadedImageView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameScreen extends Activity implements View.OnClickListener {

    private static final String TAG = "GameScreen";
    private static final int TILES = 9;
    private static final int SETS = 6;
    private static final int TILES_IN_A_SET = 3;

    private ShadedImageView[] tiles = new ShadedImageView[TILES];
    private ImageView[][] found = new ImageView[SETS][TILES_IN_A_SET];
    private Board board = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        // Init board imageviews
        for (int i = 0; i < TILES; i++) {
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

        BoardSetup();
    }

    private void setTileSelected(int tileIndex, boolean selected){
        if(tileIndex < 0 || tileIndex > TILES)
            throw new IllegalArgumentException("tileIndex out of bounds.");

        tiles[tileIndex].setShaded(selected);
    }

    private boolean getTileSelected(int tileIndex){
        if(tileIndex < 0 || tileIndex > TILES)
            throw new IllegalArgumentException("tileIndex out of bounds.");

        return tiles[tileIndex].getShaded();
    }

    public void onClick(View view){
        Object o = view.getTag(R.id.TILE_INDEX);
        if(view instanceof ImageView && o instanceof Integer){
            int idx = (Integer)o;
            Log.d(TAG, "Event: onClick for Tile " + idx);

            setTileSelected(idx, !getTileSelected(idx));
            //TODO: Use this click event to select a potential set
        }
    }

    private void BoardSetup() {
        board = Board.generateRandom(SETS);

        for (int i = 0; i < tiles.length; i++) {
            tiles[i].setImageDrawable(board.getTile(i).getDrawable(this));
            Log.d(TAG, String.format("Set tile image for tiles[%d]", i));
        }
    }
}
