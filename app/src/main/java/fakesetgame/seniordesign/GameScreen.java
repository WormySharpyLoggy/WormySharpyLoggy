package fakesetgame.seniordesign;

import fakesetgame.seniordesign.model.Board;
import fakesetgame.seniordesign.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);

        final GridLayout boardGrid = (GridLayout) findViewById(R.id.board_grid);
        boardGrid.setColumnCount(3);
        boardGrid.setRowCount(3);

        Board board = Board.generateRandom(5);

        for (int i = 0; i < 9; i++) {
            final ImageButton imageButton = new ImageButton(this);
            imageButton.setImageDrawable(board.getTile(i).getDrawable(this));
            imageButton.setMaxWidth(boardGrid.getWidth() / 3);
            imageButton.setMaxHeight(boardGrid.getHeight() / 3);
            imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            boardGrid.addView(imageButton);
        }
    }
}
