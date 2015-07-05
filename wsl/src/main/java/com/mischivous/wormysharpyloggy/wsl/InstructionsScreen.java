/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl;

import android.app.Activity;
import android.os.Bundle;


/**
 * A screen that informs users on the basics of the Set game and how
 * to play the different modes.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since July 3, 2015
 */
public class InstructionsScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions_layout);

		}

}
