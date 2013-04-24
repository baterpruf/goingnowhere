package com.goingnowhere.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.utils.CollisionTest;

public class Hero extends DynamicGameObject {
	public static final float HERO_WIDTH = 0.8f;
	public static final float HERO_HEIGHT = 0.8f;
	public static final float HERO_ACCELERATION = 4f;	
	public static final float HERO_JUMP_SPEED = 0.8f;
	public static final float HERO_MAX_SPEED=2f;
	public static final float DAMP=0.9f;
	
	static final int IDLE = 0;
	static final int RUN = 1;
	static final int JUMP = 2;
	static final int DYING = 3;
	static final int DEAD = 4;
	
	
	int state;
	int direction;
	World world;
	
	
	public Hero (World world, float x, float y) {
		super(x, y, HERO_WIDTH, HERO_HEIGHT);
		this.world=world;
		state = IDLE;
	}
	
	public void update(float deltaTime){
		processInput();
		vel.x=Math.abs(vel.x)*direction;
		vel.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		tryMove(deltaTime);
	}
	
	private void processInput(){
		if (state == DEAD || state == DYING) return;
		//Los controles podrían ser la mitad inferior izquierda/derecha disparando simultaneamente
		//y mitad superior donde sea para saltar.
		//Admito hasta dos toques simultáneos, saltar y disparar.
		float x0 = (Gdx.input.getX(0) / (float)Gdx.graphics.getWidth()) * 480;
		float x1 = (Gdx.input.getX(1) / (float)Gdx.graphics.getWidth()) * 480;
		float y0 = (Gdx.input.getY(0) / (float)Gdx.graphics.getHeight()) * 320;
		float y1 = (Gdx.input.getY(1) / (float)Gdx.graphics.getHeight()) * 320;
		
		if((y0>160 || y1>160 || Gdx.input.isKeyPressed(Keys.W)) && state!=JUMP){
			jump();
		}
		if((x0<240 && y0<160) || (x1<240 && y1<160) || Gdx.input.isKeyPressed(Keys.A)){
			direction=-1;
			shoot();
		}
		if((x0>240 && y0<160) || (x1>240 && y1<160) || Gdx.input.isKeyPressed(Keys.D)){
			direction=1;
			shoot();
		}
		
	}
	private void tryMove(float deltaTime){
		float stepX, stepY;
		//TODO hacer una lista de bloques candidatos a colisión para no tener que mirar todos y mejorar rendiimento
		int len = world.blocks.size();
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			for(stepX=0;stepX<vel.x;stepX+=0.05f){
				bounds.x+=stepX;
				if (CollisionTest.overlapRectangles(block.bounds, bounds)) {
					bounds.x-=stepX;
					break;
				}
			}
			for(stepY=0;stepY<vel.y;stepY+=0.05f){
				bounds.y+=stepY;
				if (!CollisionTest.overlapRectangles(block.bounds, bounds)) {
					bounds.y-=stepY;
					break;
				}
			}
		}
		position.add(vel.x * deltaTime, vel.y * deltaTime);
		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;
		
	}
	public void run(int direction){
		this.direction=direction;
		state=RUN;
		accel.x = HERO_ACCELERATION * direction;
	}
	
	public void jump(){
		vel.y = HERO_JUMP_SPEED;
        state = JUMP;
	}
	
	public void shoot(){
		
	}
}
