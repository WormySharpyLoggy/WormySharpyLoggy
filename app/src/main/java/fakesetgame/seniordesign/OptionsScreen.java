package fakesetgame.seniordesign;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import fakesetgame.seniordesign.data.OptionsHelper;


public class OptionsScreen extends Activity {

    SeekBar countBar;
    SeekBar diffBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);

        countBar = (SeekBar) findViewById(R.id.countSlider);
        diffBar = (SeekBar) findViewById(R.id.diffSlider);

        if (OptionsHelper.getHardness(this) == 0) {
            handleUICornerCases();
        } else {
            diffBar.setProgress(OptionsHelper.getHardness(this));
            countBar.setProgress(OptionsHelper.getSetCount(this) - 3);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        OptionsHelper.setHardness(this, diffBar.getProgress());
        OptionsHelper.setSetCount(this, countBar.getProgress());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    private void handleUICornerCases() {
        if (OptionsHelper.getHardness(this) == 0) {
            countBar.setProgress(0);
            countBar.setEnabled(false);
        } else { countBar.setEnabled(true); }
    }
}
