package com.goingnowhere.screens;

import com.badlogic.gdx.Game;
import com.goingnowhere.logic.World;

public class GameScreen extends BaseScreen {
	World world;

	public GameScreen (Game game) {
		super(game);
	}

	@Override
	public void show () {
		world = new World(1);
	}

	@Override
	public void render (float delta) {

	}

}
