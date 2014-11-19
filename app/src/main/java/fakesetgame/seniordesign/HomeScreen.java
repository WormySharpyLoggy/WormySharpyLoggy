package fakesetgame.seniordesign;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

    public void StartSP(View v) {
        Intent i = new Intent(this, GameScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("mode", Game.GameMode.Normal);
        startActivity(i);
    }

    public void StartTA(View v) {
        Intent i = new Intent(this, GameScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("mode", Game.GameMode.TimeAttack);
        startActivity(i);
    }

    public void SetOptions(View v) {
        Intent i = new Intent(HomeScreen.this, OptionsScreen.class);
        startActivity(i);
    }
}
