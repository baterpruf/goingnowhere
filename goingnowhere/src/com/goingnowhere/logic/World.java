package com.goingnowhere.logic;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.utils.CollisionTest;

public class World {

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_PAUSE = 0;
	public static final int WORLD_STATE_PLAY = 1;

	//C�digos de colores del png
	static int BLOCK = 0x000000;
	static int START = 0xff0000;
	static int END = 0xff00ff;
	static int ENEMY = 0x0000ff;
	static int COIN = 0x00ff00;

	public static final Vector2 gravity = new Vector2(0, -12);

	public Hero hero;
	public List<Enemy> enemies;
	public List<Coin> coins;
	public Exit exit;
	int[][] blocks;

	public int score;
	public int state;

	public World() {
		this.hero = new Hero(5, 5);//provisionalmente ubicado, depende del map.
		this.enemies = new ArrayList<Enemy>();
		this.coins = new ArrayList<Coin>();
		generateLevel();
		this.state=WORLD_STATE_PLAY;
		this.score = 0;
	}

	private void generateLevel () {
		//Leer el png y poner los objetos donde toca.
		Pixmap pixmap = new Pixmap(Gdx.files.internal("data/map.png"));
		int height = pixmap.getHeight();
		int width = pixmap.getWidth();
		blocks = new int[width][height];
		for (int y = 0; y < 35; y++) {
			for (int x = 0; x < 150; x++) {
				int pix = (pixmap.getPixel(x, y) >>> 8) & 0xffffff;
				if (match(pix, START)) {
					hero = new Hero( x, height - 1 - y);
					hero.state = Hero.IDLE;
				} else if (match(pix, ENEMY)) {
					Enemy enemy = new Enemy(x, height - 1 - y);
					enemies.add(enemy);
				} else if (match(pix, COIN)) {
					coins.add(new Coin(x, height - 1 - y));
				} else if (match(pix, END)) {
					exit = new Exit(x, height - 1 - y);
				} else if (match(pix, BLOCK)) {
					blocks[x][y] = pix;
				}
			}
		}
	}
	boolean match (int src, int dst) {
		return src == dst;
	}

	public void update (float deltaTime, float accelX) {
		updateHero(deltaTime, accelX);
		updateEnemies(deltaTime);
		updateCoins(deltaTime);
		checkCollisions();
		checkGameOver();
	}

	private void updateHero (float deltaTime, float accelX) {
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

	}

	private void checkEnemiesCollisions () {
		int len = enemies.size();
		for (int i = 0; i < len; i++) {
			Enemy enemy = enemies.get(i);
			if (CollisionTest.overlapRectangles(enemy.bounds, hero.bounds)) {
				//morirse o quitar vida
			}
		}
	}

	private void checkGameOver () {

	}
}