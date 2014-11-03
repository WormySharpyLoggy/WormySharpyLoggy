package fakesetgame.seniordesign;

import fakesetgame.seniordesign.data.GameOutcome;
import fakesetgame.seniordesign.data.GameSummaryListItemCursorAdapter;
import fakesetgame.seniordesign.data.PlayerDataDbHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SummaryScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_activity);
        long lastGameID = getIntent().getLongExtra("lastGame", 0);

        GameOutcome lastGame = PlayerDataDbHelper.getOutcome(this, lastGameID);
        long lastGameTime = lastGame.getElapsed() / 1000;

        TextView elapsed = (TextView) findViewById(R.id.elapsedTime);
        elapsed.setText(String.format("%d:%02d", lastGameTime / 60, lastGameTime % 60));

        GameSummaryListItemCursorAdapter adapter = new GameSummaryListItemCursorAdapter(this, PlayerDataDbHelper.getBestOutcomes(this, lastGame.getMode(), 5, false), 0);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        messageUser(getResources().getString(lastGame.wasHintUsed() ? R.string.hintUsed : R.string.hintNotUsed));
    }

    private void messageUser(String message) {
        Toast toast = Toast.makeText(this, message,
                (message.length() > 24 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT));
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void StartNewGame(View v) {
        Intent i = new Intent(this, GameScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void MainMenu(View v) {
        Intent i = new Intent(this, HomeScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
