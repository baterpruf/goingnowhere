package com.goingnowhere;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.goingnowhere.logic.World;

public class WorldRenderer {
	World world;
	OrthographicCamera cam;
	SpriteCache cache;
	SpriteBatch batch = new SpriteBatch(10000);
	
	public WorldRenderer (World world) {
		this.world = world;
		this.cam = new OrthographicCamera(480, 320);
		this.cam.position.set(world.hero.position.x, 160, 0);
	}
	public void render(float delta){
		
	}
}
