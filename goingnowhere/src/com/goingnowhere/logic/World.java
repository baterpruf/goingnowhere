package com.goingnowhere.logic;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.utils.CollisionTest;
import com.goingnowhere.utils.VectorGravity;

public class World {

	public static final float WORLD_WIDTH = 480;
	public static final float WORLD_HEIGHT = 320;
	public static final int WORLD_STATE_PAUSE = 0;
	public static final int WORLD_STATE_PLAY = 1;
	
	//Códigos de colores del png
	static int BLOCK = 0x000000;
	static int START = 0xff0000;
	static int END = 0xff00ff;
	static int ENEMY = 0x0000ff;
	static int COIN = 0x00ff00;

	
	public static final float gravityG=19f;
	public VectorGravity gravity = new VectorGravity(0);

	public Hero hero;
	public List<Enemy> enemies;
	public List<Coin> coins;
	public Exit exit;
	public List<Block> blocks;	
	
	public int level;
	public int score;
	public int state;
	float totalTime=0;

	public World(int level) {
		this.level=level;
		this.enemies = new ArrayList<Enemy>();
		this.coins = new ArrayList<Coin>();
		this.blocks = new ArrayList<Block>();
		generateLevel();
		this.state=WORLD_STATE_PLAY;
		this.score = 0;
		Gdx.app.log("g", ""+gravity.x+"-"+gravity.y);
	}

	private void generateLevel () {
		//Leer el png y poner los objetos donde toca.
		Pixmap pixmap = new Pixmap(Gdx.files.internal("data/map"+level+".png"));
		int height = pixmap.getHeight();
		int width = pixmap.getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int xp = x*20;
				int yp = (height - 1 - y)*20;
				int pix = (pixmap.getPixel(x, y) >>> 8) & 0xffffff;
				if (match(pix, START)) {
					hero = new Hero( this, xp, yp);
				} else if (match(pix, ENEMY)) {
					enemies.add(new Enemy(xp, yp));
				} else if (match(pix, COIN)) {
					coins.add(new Coin(xp, yp));
				} else if (match(pix, END)) {
					exit = new Exit(xp, yp);
				} else if (match(pix, BLOCK)) {
					blocks.add(new Block(xp, yp));
				}
			}
		}
		//Gdx.app.log("Info", "resultado: "+pix);
	}
	boolean match (int src, int dst) {
		return src == dst;
	}

	public void update (float deltaTime) {
		updateHero(deltaTime);
		updateEnemies(deltaTime);
		updateCoins(deltaTime);
		//checkCollisions();
		checkGameOver();
		totalTime+=deltaTime;
		if(totalTime>2){
			totalTime=0;
			gravity.advance(1.56f);
			hero.needRotation=true;
			Gdx.app.log("e", ""+gravity.getAngle());
		}
	}

	private void updateHero (float deltaTime) {
		hero.update(deltaTime);
	}

	private void updateEnemies (float deltaTime) {
		int len = enemies.size();
		for (int i = 0; i < len; i++) {
			Enemy enemy = enemies.get(i);
			enemy.update(deltaTime);
		}
	}

	private void updateCoins (float deltaTime) {
		int len = coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = coins.get(i);
			coin.update(deltaTime);
		}
	}

	private void checkCollisions () {
		checkBlockCollisions ();
		checkEnemiesCollisions ();
	}

	private void checkBlockCollisions () {
		int len = blocks.size();
		for (int i = 0; i < len; i++) {
			Block block = blocks.get(i);
			if (CollisionTest.collision(block.bounds, hero.bounds)) {
				//No puede acabarse ese movimiento.
			}
		}

	}

	private void checkEnemiesCollisions () {
		int len = enemies.size();
		for (int i = 0; i < len; i++) {
			Enemy enemy = enemies.get(i);
			if (CollisionTest.collision(enemy.bounds, hero.bounds)) {
				//morirse o quitar vida
			}
		}
	}

	private void checkGameOver () {

	}
}
