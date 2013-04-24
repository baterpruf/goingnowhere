package com.goingnowhere.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.goingnowhere.WorldRenderer;
import com.goingnowhere.logic.World;

public class GameScreen extends BaseScreen {
	World world;
	WorldRenderer renderer;

	public GameScreen (Game game) {
		super(game);
	}

	@Override
	public void show () {
		world = new World(1);
		renderer = new WorldRenderer(world);
	}

	@Override
	public void render (float delta) {
		delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		world.update(delta);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
	}

}
