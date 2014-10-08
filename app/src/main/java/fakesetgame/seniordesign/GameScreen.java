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
    private Board board = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = (ImageView) findViewById(getResources().getIdentifier(
                    "Tile" + Integer.valueOf(i + 1).toString(),
                    "id",
                    getPackageName()));
            tiles[i].setTag(i);
            if (tiles[i] != null)
                Log.d(TAG, String.format("tiles[%d] set to Tile%d", i, i + 1));
            else
                Log.e(TAG, String.format("Attempted to set tiles[%d] set to Tile%d, but value is null.", i, i + 1));
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
