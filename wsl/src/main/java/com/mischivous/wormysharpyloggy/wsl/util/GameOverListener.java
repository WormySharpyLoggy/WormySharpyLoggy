/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.util;

import com.mischivous.wormysharpyloggy.wsl.model.GameOverEvent;

import java.util.EventListener;

/**
 * An interface for a GameOverEvent listener.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 30, 2015
 */
public interface GameOverListener extends EventListener {
	/**
	 * Event handler for the Game Over event. Saves
	 * the game outcome and opens the Summary screen.
	 *
	 * @param e The GameOver event
	 */
	void GameOver(GameOverEvent e);
}
