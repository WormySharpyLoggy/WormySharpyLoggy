package fakesetgame.seniordesign;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.util.SystemUiHider;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        BoardSetup();
    }

    private void BoardSetup() {
        Board b = Board.generateRandom(6);
        ImageView[] tiles = new ImageView[9];
        tiles[0] = (ImageView)findViewById(R.id.TileTL);
        tiles[1] = (ImageView)findViewById(R.id.TileTC);
        tiles[2] = (ImageView)findViewById(R.id.TileTR);
        tiles[3] = (ImageView)findViewById(R.id.TileCL);
        tiles[4] = (ImageView)findViewById(R.id.TileCC);
        tiles[5] = (ImageView)findViewById(R.id.TileCR);
        tiles[6] = (ImageView)findViewById(R.id.TileBL);
        tiles[7] = (ImageView)findViewById(R.id.TileBC);
        tiles[8] = (ImageView)findViewById(R.id.TileBR);

        for (int i = 0; i < tiles.length; i++)
            tiles[i].setImageDrawable(b.getTile(i).getDrawable(this));
    }
}
