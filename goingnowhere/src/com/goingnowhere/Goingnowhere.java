package com.goingnowhere;

import com.badlogic.gdx.Game;
import com.goingnowhere.screens.MainScreen;

public class Goingnowhere extends Game {
		
	@Override
	public void create() {		
		setScreen(new MainScreen(this));
	}
}
