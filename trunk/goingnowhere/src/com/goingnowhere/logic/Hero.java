package com.goingnowhere.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.utils.CollisionTest;

public class Hero extends DynamicGameObject {
	public static final float HERO_WIDTH = 32f;
	public static final float HERO_HEIGHT = 32f;
	public static final float HERO_ACCELERATION = 35f;	
	public static final float HERO_JUMP_SPEED = 9f;
	public static final float HERO_MAX_SPEED=6f;
	public static final float DAMP=0.15f;
	
	static final int IDLE = 0;
	static final int RUN = 1;
	static final int JUMP = 2;
	static final int DYING = 3;
	static final int DEAD = 4;
	
	
	int state;
	int direction;
	Vector2 rotatedAdvance=new Vector2(0,0);
	public boolean flipped=false;
	public boolean needRotation=false;
	public float angle;
	int coins;
	boolean canJump=false;
	World world;
	Vector2 opposite=new Vector2(0,0);
	public String debugMessage;
	
	
	public Hero (World world, float x, float y) {
		super(x, y, HERO_WIDTH, HERO_HEIGHT);
		this.world=world;
		angle=world.gravity.getWorldAngle();
		state = IDLE;
		needRotation=true;
	}
	public void updateRotation(float angle){
		//needRotation=true;
		this.angle=angle;
	}
	public void update(float deltaTime){
		if(!canJump){
			//vel.set((Math.abs(vel.x))*Math.signum(vel.x),(Math.abs(vel.y))*Math.signum(vel.y));
		}else{
			//damping
			opposite.set(-vel.x*DAMP,-vel.y*DAMP);
			accel.add(opposite);
		}
		if((vel.len())<=HERO_MAX_SPEED){
			vel.add(accel.x*deltaTime,accel.y*deltaTime);
		}
		vel.add( 0, -world.gravityG * deltaTime);
		tryMove(vel.x*deltaTime*20,vel.y*deltaTime*20);
		int len = world.coins.size();
		for (int i = 0; i < len; i++) {
			Coin coin = world.coins.get(i);
			if (CollisionTest.collision(coin.bounds, bounds)) {
				world.coins.remove(coin);
				//ganar puntos, eliminar moneda
				coins++;
				break;
			}
		}
	}
	private void polarMove(float d, float angle){
		float px=(float) (d*Math.cos(angle*World.PI/180));
		float py=(float) (d*Math.sin(angle*World.PI/180));
		bounds.x+=px;
		bounds.y+=py;
		position.add(px,py);
	}
	private void tryMove(float dx,float dy){
		//TODO hacer una lista de bloques candidatos a colisión para no tener que mirar todos y mejorar rendimiento
		polarMove(dx, this.angle);
		boolean contact=false;
		int len = world.blocks.size();
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			while (CollisionTest.collision(block.bounds, bounds)) {
				polarMove(-dx/2, this.angle);
				contact=true;
			}
		}
		if(contact){
			dx=0;
			vel.x=0;
			contact=false;
		}
		
		polarMove(dy, this.angle+90);
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			while (CollisionTest.collision(block.bounds, bounds)) {
				polarMove(-dy/2, this.angle+90);
				contact=true;
				canJump=true;
			}
		}
		if(contact){
			dy=0;
			vel.y=0;
			contact=false;
		}
	}
	public void goRight(){
		accel.add((float) HERO_ACCELERATION,0);
		direction=1;
	}
	public void goLeft(){
		accel.set(-(float) HERO_ACCELERATION,0);
		direction=-1;
	}
	public void stop(){
		accel.set(0,0);
	}

	public void jump(){
		if(canJump){
			vel.add(0,(float) HERO_JUMP_SPEED);
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