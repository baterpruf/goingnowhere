package com.goingnowhere.logic;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.utils.CollisionTest;

public class World {

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_PAUSE = 0;
	public static final int WORLD_STATE_PLAY = 1;
	
	public static final Vector2 gravity = new Vector2(0, -12);

	public final Hero hero;
	public final List<Enemy> enemies;
	public final List<Coin> coins;

	public int score;
	public int state;

	public World () {
		this.hero = new Hero(5, 5);//provisionalmente ubicado, depende del map.
		this.enemies = new ArrayList<Enemy>();
		this.coins = new ArrayList<Coin>();
		generateLevel();
		this.state=WORLD_STATE_PLAY;
		this.score = 0;
	}

	private void generateLevel () {
		//Leer el png y poner los objetos donde toca.
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
		checkPlatformCollisions ();
		checkEnemiesCollisions ();
	}

	private void checkPlatformCollisions () {

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
