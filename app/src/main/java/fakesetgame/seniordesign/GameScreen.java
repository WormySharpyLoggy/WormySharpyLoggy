package fakesetgame.seniordesign;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameScreen extends Activity {

    private final String TAG = "GameScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        Board board = Board.generateRandom(5);

        for (int i = 0; i < 9; i++) {

            final ImageButton imageButton = (ImageButton)findViewById(getResources()
                    .getIdentifier(String.format("tile%d", i), "id", getPackageName()));
            imageButton.setImageDrawable(board.getTile(i).getDrawable(this));
        }
    }
}
