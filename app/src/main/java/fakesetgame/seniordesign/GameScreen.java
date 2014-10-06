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

        ImageView tileTL = (ImageView)findViewById(getResources().getIdentifier("TileTL", "id", getPackageName()));
        ImageView tileTC = (ImageView)findViewById(getResources().getIdentifier("TileTC", "id", getPackageName()));
        ImageView tileTR = (ImageView)findViewById(getResources().getIdentifier("TileTR", "id", getPackageName()));
        ImageView tileCL = (ImageView)findViewById(getResources().getIdentifier("TileCL", "id", getPackageName()));
        ImageView tileCC = (ImageView)findViewById(getResources().getIdentifier("TileCC", "id", getPackageName()));
        ImageView tileCR = (ImageView)findViewById(getResources().getIdentifier("TileCR", "id", getPackageName()));
        ImageView tileBL = (ImageView)findViewById(getResources().getIdentifier("TileBL", "id", getPackageName()));
        ImageView tileBC = (ImageView)findViewById(getResources().getIdentifier("TileBC", "id", getPackageName()));
        ImageView tileBR = (ImageView)findViewById(getResources().getIdentifier("TileBR", "id", getPackageName()));

        tileTL.setImageDrawable(b.getTile(0).getDrawable(this));
        tileTC.setImageDrawable(b.getTile(1).getDrawable(this));
        tileTR.setImageDrawable(b.getTile(2).getDrawable(this));
        tileCL.setImageDrawable(b.getTile(3).getDrawable(this));
        tileCC.setImageDrawable(b.getTile(4).getDrawable(this));
        tileCR.setImageDrawable(b.getTile(5).getDrawable(this));
        tileBL.setImageDrawable(b.getTile(6).getDrawable(this));
        tileBC.setImageDrawable(b.getTile(7).getDrawable(this));
        tileBR.setImageDrawable(b.getTile(8).getDrawable(this));
    }
}
