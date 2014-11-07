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

        diffBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() == 0) { countBar.setProgress(0); }
            }
        });
        countBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() != 0 && diffBar.getProgress() == 0) { diffBar.setProgress(1); }
            }
        });

        diffBar.setProgress(OptionsHelper.getHardness(this));
        countBar.setProgress(OptionsHelper.getSetCount(this) - 3);

        //handleUICornerCases();
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
}
