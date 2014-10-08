package fakesetgame.seniordesign;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.util.SystemUiHider;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameScreen extends Activity {

    private static final String TAG = "GameScreen";
    private ImageView[] tiles = new ImageView[9];
    private ImageView[][] found = new ImageView[6][3];
    private Board board = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        // Init board imageviews
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = (ImageView) findViewById(getResources().getIdentifier(
                    "Tile" + Integer.valueOf(i + 1).toString(),
                    "id",
                    getPackageName()));
            tiles[i].setTag(i);

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


    private void BoardSetup() {
        board = Board.generateRandom(6);

        for (int i = 0; i < tiles.length; i++) {
            tiles[i].setImageDrawable(board.getTile(i).getDrawable(this));
            Log.d(TAG, String.format("Set tile image for tiles[%d]", i));
        }
    }
}
