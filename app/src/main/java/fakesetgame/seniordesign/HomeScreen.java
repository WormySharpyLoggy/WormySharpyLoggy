package fakesetgame.seniordesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import fakesetgame.seniordesign.model.Game;


public class HomeScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
    }

    protected void onNewIntent(Intent i) {
        setContentView(R.layout.home_activity);
    }

    public void StartGame(View v) {
        Intent i = new Intent(HomeScreen.this, GameScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        RadioGroup rg = (RadioGroup) findViewById(R.id.playMode);
        switch (rg.getCheckedRadioButtonId()) {
            default:
            case R.id.singlePlay:
                i.putExtra("mode", "single");
                break;
            case R.id.multiPlay:
                i.putExtra("mode", "multi");
                break;
        }

        switch (v.getId()) {
            default:
            case R.id.newStandard:
                i.putExtra("type", Game.GameType.Normal);
                break;
            case R.id.newTA:
                i.putExtra("type", Game.GameType.TimeAttack);
                break;
        }

        startActivity(i);
    }

    public void ViewOptions(View v) {
        Intent i = new Intent(this, OptionsScreen.class);
        startActivity(i);
    }
}
