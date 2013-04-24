package com.goingnowhere;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.goingnowhere.logic.Block;
import com.goingnowhere.logic.World;

public class WorldRenderer {
	World world;
	OrthographicCamera cam;
	SpriteCache cache;
	SpriteBatch batch;
	Texture heroImage;
	Texture blockImage;
	Texture mapImage;
	Texture backgroundImage;
	BitmapFont font;
	
	public WorldRenderer (World world) {
		this.world = world;
		this.cam = new OrthographicCamera(480, 320);
		this.cam.position.set(world.hero.position.x, 160, 0);
		heroImage = new Texture(Gdx.files.internal("data/hero.png"));
		blockImage = new Texture(Gdx.files.internal("data/block.png"));
		mapImage = new Texture(Gdx.files.internal("data/background1.png"));
		backgroundImage = new Texture(Gdx.files.internal("data/background_buildings.png"));
	    batch = new SpriteBatch();


	}
	public void render(float delta){

	      Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	      Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	      cam.position.x=world.hero.position.x;
	      cam.update();
	      batch.setProjectionMatrix(cam.combined);

	      batch.begin();
	      batch.draw(backgroundImage, -240,-192);
	      batch.draw(mapImage, -240,-192);
	      for(Block bl: world.blocks){
	    	  batch.draw(blockImage, bl.position.x-240, bl.position.y);
	      }
	      batch.draw(heroImage, world.hero.position.x-240, world.hero.position.y);
	      batch.draw(blockImage, world.hero.position.x-240, world.hero.position.y);
	      font = new BitmapFont();
	      font.setColor(1024,0,0,128);
	      font.draw(batch, "h"+(int)world.hero.position.y+" b"+(int)world.hero.bounds.y, 200,300);
	      batch.end();
	}
}
