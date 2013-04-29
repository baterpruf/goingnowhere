package com.goingnowhere.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.goingnowhere.utils.CollisionTest;

public class Hero extends DynamicGameObject {
	public static final float HERO_WIDTH = 32f;
	public static final float HERO_HEIGHT = 32f;
	public static final float HERO_ACCELERATION = 25f;	
	public static final float HERO_JUMP_SPEED = 9f;
	public static final float HERO_MAX_SPEED=12f;
	public static final float DAMP=0.05f;
	
	static final int IDLE = 0;
	static final int RUN = 1;
	static final int JUMP = 2;
	static final int DYING = 3;
	static final int DEAD = 4;
	
	
	int state;
	int direction;
	boolean canJump=false;
	World world;
	public String debugMessage;
	
	
	public Hero (World world, float x, float y) {
		super(x, y, HERO_WIDTH, HERO_HEIGHT);
		this.world=world;
		state = IDLE;
	}
	
	public void update(float deltaTime){
		vel.x=(Math.abs(vel.x)-0.2f)*Math.signum(vel.x);
		if(Math.abs(vel.x)<Math.abs(HERO_MAX_SPEED)){
			vel.x+=accel.x*deltaTime*direction;
		}
		vel.y+= World.gravity.y * deltaTime;
		tryMove(vel.x*deltaTime*20,vel.y*deltaTime*20);
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
}
