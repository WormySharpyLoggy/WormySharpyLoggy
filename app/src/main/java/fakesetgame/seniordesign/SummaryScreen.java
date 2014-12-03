package fakesetgame.seniordesign;

import fakesetgame.seniordesign.data.GameOutcome;
import fakesetgame.seniordesign.data.GameSummaryListItemCursorAdapter;
import fakesetgame.seniordesign.data.PlayerDataDbHelper;
import fakesetgame.seniordesign.model.Game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SummaryScreen extends Activity {

    private Game.GameType mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_activity);
        long lastGameID = getIntent().getLongExtra("lastGame", 0);

        GameOutcome lastGame = PlayerDataDbHelper.getOutcome(this, lastGameID);
        mode = lastGame.getMode();
        long lastGameTime = lastGame.getElapsed() / 1000;

        TextView elapsed = (TextView) findViewById(R.id.elapsedTime);
        if(lastGame.getOutcome() == Game.Outcome.Win)
            elapsed.setText(String.format("%d:%02d", lastGameTime / 60, lastGameTime % 60));
        else elapsed.setText(":(");

        GameSummaryListItemCursorAdapter adapter = new GameSummaryListItemCursorAdapter(this, PlayerDataDbHelper.getBestOutcomes(this, lastGame.getMode(), 5, lastGame.wasHintUsed()), 0);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        if(lastGame.getOutcome() == Game.Outcome.Win)
            messageUser(getResources().getString(lastGame.wasHintUsed() ? R.string.hintUsed : R.string.hintNotUsed));
        else messageUser(getResources().getString(R.string.lostGame));
    }

    private void messageUser(String message) {
        Toast toast = Toast.makeText(this, message,
                (message.length() > 24 ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT));
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void StartNewGame(View v) {
        Intent i = new Intent(this, GameScreen.class);
        i.putExtra("type", mode);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void MainMenu(View v) {
        Intent i = new Intent(this, HomeScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
