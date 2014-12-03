package fakesetgame.seniordesign;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.widget.SeekBar;

import fakesetgame.seniordesign.data.OptionsHelper;


public class OptionsScreen extends Activity {

    SeekBar countBar;
    SeekBar diffBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.options_activity);
//        getFragmentManager().beginTransaction()
//                .replace(R.id.settingsContainer, new OptionsFragment(), "settings")
//                .commit();
    }

    public static class OptionsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        ListPreference diff;
        ListPreference sparse;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.options);
            diff = (ListPreference) findPreference(getString(R.string.optDiffKey));
            sparse = (ListPreference) findPreference(getString(R.string.optSparseKey));

            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
            if(isAdded()) {
                // If user is selecting easy mode, reduce number of sets to find
                if (key.equals(getString(R.string.optDiffKey))) {
                    if (diff.getValue() == getString(R.string.optDiffLowLevelValue)) {
                        sparse.setValue(getString(R.string.optSparseLowLevelValue));
                    }
                    // If user is increasing number of sets, bump them out of easy mode
                } else if (key.equals(getString(R.string.optSparseKey))) {
                    if (sparse.getValue() != getString(R.string.optSparseLowLevelValue)
                            && diff.getValue() == getString(R.string.optDiffLowLevelValue)) {
                        diff.setValue(getString(R.string.optDiffMidLevelValue));
                    }
                }

                ListPreference lp = (ListPreference) findPreference(key);
                lp.setSummary(lp.getEntry());
            }
        }
    }

}