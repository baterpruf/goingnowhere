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
	Texture leftImage;
	Texture rightImage;
	Texture jumpImage;
	BitmapFont font;
	
	public WorldRenderer (World world) {
		this.world = world;
		this.cam = new OrthographicCamera(480, 320);
		this.cam.position.set(-100, 160, 0);
		this.cam.zoom=0.8f;
		heroImage = new Texture(Gdx.files.internal("data/hero.png"));
		blockImage = new Texture(Gdx.files.internal("data/block.png"));
		mapImage = new Texture(Gdx.files.internal("data/background1.png"));
		backgroundImage = new Texture(Gdx.files.internal("data/background_buildings.png"));
		leftImage = new Texture(Gdx.files.internal("data/left.png"));
		rightImage = new Texture(Gdx.files.internal("data/right.png"));
		jumpImage = new Texture(Gdx.files.internal("data/jump.png"));
		font = new BitmapFont();
	    font.setColor(1024,0,0,128);
	    batch = new SpriteBatch();


	}
	public void render(float delta){

	      Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	      Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	      cam.position.x+=(world.hero.position.x-cam.position.x)/10;
	      cam.position.y+=(world.hero.position.y-cam.position.y+15)/10;
	      cam.update();
	      batch.setProjectionMatrix(cam.combined);

	      batch.begin();
	      //batch.draw(backgroundImage, -240,-192);
	      //batch.draw(mapImage, -240,-192);
	      for(Block bl: world.blocks){
	    	  batch.draw(blockImage, bl.position.x, bl.position.y);
	      }
	      batch.draw(heroImage, world.hero.position.x, world.hero.position.y);
	      //font.draw(batch, "h: "+world.hero.debugMessage, cam.position.x,cam.position.y-100);
	      batch.draw(leftImage, cam.position.x-180,cam.position.y-120);
	      batch.draw(rightImage, cam.position.x-90,cam.position.y-120);
	      batch.draw(jumpImage, cam.position.x+120,cam.position.y-120);
	      batch.end();
	}
}
