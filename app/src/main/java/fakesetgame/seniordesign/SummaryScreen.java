package fakesetgame.seniordesign;

import fakesetgame.seniordesign.data.GameOutcome;
import fakesetgame.seniordesign.data.GameSummaryListItemCursorAdapter;
import fakesetgame.seniordesign.data.PlayerDataDbHelper;
import fakesetgame.seniordesign.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class SummaryScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_activity);
        long lastGameID = getIntent().getLongExtra("lastGame", 0);

        GameOutcome lastGame = PlayerDataDbHelper.getOutcome(this, lastGameID);
        long lastGameTime = lastGame.getElapsed() / 1000;

        TextView elapsed = (TextView)findViewById(R.id.elapsedTime);
        elapsed.setText(String.format("%d:%02d", lastGameTime / 60, lastGameTime % 60));

        GameSummaryListItemCursorAdapter adapter = new GameSummaryListItemCursorAdapter(this, PlayerDataDbHelper.getBestOutcomes(this, 5), 0);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        // do nothing on back button pressed
    }

    public void StartNewGame(View v) {
        Intent i = new Intent(SummaryScreen.this, GameScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
