/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

/**
 * The Activity that controls game settings,
 * allowing the user to modify different game parameters
 * for a more enjoyable experience.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 30, 2015
 */
public class SettingsScreen extends Activity {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.options_layout);
	}

	/**
	 * The Fragment that actually displays the settings to the user.
	 *
	 * @author Jeremy Brown
	 * @version 1.0
	 * @since June 30, 2015
	 */
	public static class SettingsFragment extends PreferenceFragment
			implements SharedPreferences.OnSharedPreferenceChangeListener {

		ListPreference diff;
		ListPreference sparse;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			addPreferencesFromResource(R.xml.options);
			diff = (ListPreference) findPreference(getString(R.string.optDiffKey));
			sparse = (ListPreference) findPreference(getString(R.string.optSparseKey));

			getPreferenceScreen().getSharedPreferences()
					.registerOnSharedPreferenceChangeListener(this);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
			if (isAdded()) {
				/* I needed this when I couldn't generate boards so well; I still can't
				   but I don't think I need this right now.
				// If user is selecting easy mode, reduce number of sets to find
				if (key.equals(getString(R.string.optDiffKey))) {
					if (diff.GetValue().equals(getString(R.string.optDiffLowLevelValue))) {
						sparse.setValue(getString(R.string.optSparseLowLevelValue));
					}
					// If user is increasing number of sets, bump them out of easy mode
				} else if (key.equals(getString(R.string.optSparseKey))) {
					if (!sparse.GetValue().equals(getString(R.string.optSparseLowLevelValue))
							&& diff.GetValue().equals(getString(R.string.optDiffLowLevelValue))) {
						diff.setValue(getString(R.string.optDiffMidLevelValue));
					}
				}*/

				ListPreference lp = (ListPreference) findPreference(key);
				lp.setSummary(lp.getEntry());
			}
		}
	}

}