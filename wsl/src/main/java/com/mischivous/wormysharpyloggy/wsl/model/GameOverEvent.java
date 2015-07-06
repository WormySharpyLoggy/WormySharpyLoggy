/*
 * Copyright (C) 2015 Jeremy Brown. Released under the Non-Profit Open Software License version 3.0 (NPOSL-3.0)
 */

package com.mischivous.wormysharpyloggy.wsl.model;

import java.util.EventObject;

/**
 * An event fired upon completion of a Game.
 *
 * @author Jeremy Brown
 * @version 1.0
 * @since June 30, 2015
 */
public class GameOverEvent extends EventObject {
	private static final long serialVersionUID = 6611030258112238996L;

	public GameOverEvent(Object source){
		super(source);
	}
}
