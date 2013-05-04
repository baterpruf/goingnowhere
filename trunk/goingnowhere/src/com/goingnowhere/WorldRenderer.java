package com.goingnowhere;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.goingnowhere.logic.Block;
import com.goingnowhere.logic.Coin;
import com.goingnowhere.logic.World;

public class WorldRenderer {
	World world;
	OrthographicCamera cam;
	SpriteCache cache;
	SpriteBatch batch;
	Texture heroImage;
	Sprite heroSprite;
	Texture heroImageleft;
	Texture blockImage;
	Texture coinImage;
	Texture mapImage;
	Texture backgroundImage;
	Texture leftImage;
	Texture rightImage;
	Texture jumpImage;
	Texture controls;
	Sprite controlLeft;
	Sprite controlRight;
	Sprite controlUp;
	BitmapFont font;
	
	public WorldRenderer (World world) {
		this.world = world;
		this.cam = new OrthographicCamera(480, 320);
		this.cam.position.set(-100, 160, 0);
		//this.cam.zoom=0.8f;
		controls= new Texture(Gdx.files.internal("data/controls.png"));
		heroImage = new Texture(Gdx.files.internal("data/hero.png"));
		heroSprite = new Sprite(heroImage);
		heroImageleft = new Texture(Gdx.files.internal("data/heroleft.png"));
		blockImage = new Texture(Gdx.files.internal("data/block.png"));
		coinImage = new Texture(Gdx.files.internal("data/coin.png"));
		mapImage = new Texture(Gdx.files.internal("data/background1.png"));
		backgroundImage = new Texture(Gdx.files.internal("data/background_buildings.png"));
		controls= new Texture(Gdx.files.internal("data/controls.png"));
		controlLeft= new Sprite(controls,0,0,64,64);
		controlRight= new Sprite(controls,64,0,64,64);
		controlUp= new Sprite(controls,0,64,64,64);
		
		font = new BitmapFont();
	    font.setColor(1024,0,0,128);
	    batch = new SpriteBatch();

	}
	public void render(float delta){

	      Gdx.gl.glClearColor(0, 0, 0.2f, 1);
	      Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	      cam.position.add((world.hero.position.x-cam.position.x)/10,(world.hero.position.y-cam.position.y+15)/10, 0);
	      cam.update();
	      batch.setProjectionMatrix(cam.combined);

	      batch.begin();
	      //batch.draw(backgroundImage, -240,-192);
	      //batch.draw(mapImage, -240,-192);
	      for(Block bl: world.blocks){
	    	  batch.draw(blockImage, bl.position.x, bl.position.y);
	      }
	      for(Coin cn: world.coins){
	    	  batch.draw(coinImage, cn.position.x, cn.position.y);
	      }
	      if(world.hero.getDirection()==-1 && !world.hero.flipped){
	    	  //heroSprite.flip(true,false);
	    	  world.hero.flipped=true;
	      }
	      if(world.hero.getDirection()==1 && world.hero.flipped){
	    	  //heroSprite.flip(true,false);
	    	  world.hero.flipped=false;
	      }
	      if(world.hero.needRotation){
	    	  world.hero.needRotation=false;
	    	  heroSprite.setRotation(world.gravity.getAngle()*180/(World.PI)+90);
	      }
	      //batch.draw(heroSprite, world.hero.position.x, world.hero.position.y);
	      heroSprite.setX(world.hero.position.x);
	      heroSprite.setY(world.hero.position.y);
	      heroSprite.draw(batch);
	      //font.draw(batch, "h: "+world.hero.debugMessage, cam.position.x,cam.position.y-100);
	      font.draw(batch, "Score: "+world.hero.getCoins(), cam.position.x,cam.position.y-100);
	      batch.draw(controlLeft, cam.position.x-180,cam.position.y-120);
	      batch.draw(controlRight, cam.position.x-90,cam.position.y-120);
	      batch.draw(controlUp, cam.position.x+120,cam.position.y-120);
	      batch.end();
	}
}
