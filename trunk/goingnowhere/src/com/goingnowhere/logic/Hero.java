package com.goingnowhere.logic;

import com.badlogic.gdx.Gdx;
import com.goingnowhere.utils.CollisionTest;

public class Hero extends DynamicGameObject {
	public static final float HERO_WIDTH = 32f;
	public static final float HERO_HEIGHT = 32f;
	public static final float HERO_ACCELERATION = 35f;	
	public static final float HERO_JUMP_SPEED = 9f;
	public static final float HERO_MAX_SPEED=6f;
	public static final float DAMP=0.05f;
	
	static final int IDLE = 0;
	static final int RUN = 1;
	static final int JUMP = 2;
	static final int DYING = 3;
	static final int DEAD = 4;
	
	
	int state;
	int direction;
	int angle;
	int coins;
	boolean canJump=false;
	World world;
	public String debugMessage;
	
	
	public Hero (World world, float x, float y) {
		super(x, y, HERO_WIDTH, HERO_HEIGHT);
		this.world=world;
		state = IDLE;
	}
	
	public void update(float deltaTime){
		if(!canJump)vel.x=(Math.abs(vel.x)-0.3f)*Math.signum(vel.x);
		if(Math.abs(vel.x)<Math.abs(HERO_MAX_SPEED)){
			vel.x+=accel.x*deltaTime*direction;
		}
		vel.y+= world.gravity.y * deltaTime;
		vel.x+= world.gravity.x * deltaTime;
		tryMove(vel.x*deltaTime*20,vel.y*deltaTime*20);
		int len = world.coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = world.coins.get(i);
			if (CollisionTest.collision(coin.bounds, bounds)) {
				world.coins.remove(coin);
				//ganar puntos, eliminar moneda
				Gdx.app.log("", ""+i);
				coins++;
				break;
			}
		}
	}
	
	private void tryMove(float dx,float dy){
		//TODO hacer una lista de bloques candidatos a colisión para no tener que mirar todos y mejorar rendimiento
		//TODO si se va a mantener la cuadrícula de bloques del map se podría simplificar mucho todo teniendo en cuenta que 
		//...solo vas a encontrar bloques en las posiciones enteras
		bounds.x+=dx;
		boolean contact=false;
		int len = world.blocks.size();
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			while (CollisionTest.collision(block.bounds, bounds)) {
				bounds.x-=dx/2;
				contact=true;
			}
		}
		if(contact){
			vel.x=0;
			dx=0;
			contact=false;
		}
		bounds.y+=dy;
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			while (CollisionTest.collision(block.bounds, bounds)) {
				bounds.y-=dy/2;
				contact=true;
				if(dy<0)canJump=true;
			}
		}
		if(contact){
			vel.y=0;
			dy=0;
		}
		position.x = bounds.x + bounds.width / 2;
		position.y = bounds.y + bounds.height / 2;
	}
	public void goRight(){
		accel.x=HERO_ACCELERATION;
		direction=1;
	}
	public void goLeft(){
		accel.x=HERO_ACCELERATION;
		direction=-1;
	}
	public void stop(){
		accel.x=0;
	}

	public void jump(){
		if(canJump){
			vel.y = HERO_JUMP_SPEED;
			canJump=false;
		}
	}
	
	public void shoot(){
		
	}
	public int getCoins(){
		return coins;
	}
	public int getDirection(){
		return direction;
	}
}