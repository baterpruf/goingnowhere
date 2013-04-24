package com.goingnowhere.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Hero extends DynamicGameObject {
	public static final float HERO_WIDTH = 0.8f;
	public static final float HERO_HEIGHT = 0.8f;
	public static final float HERO_JUMP_SPEED = 0.8f;
	
	static final int IDLE = 0;
	static final int RUN = 1;
	static final int JUMP = 2;
	static final int DYING = 3;
	static final int DEAD = 4;
	
	int state;
	int direction;
	
	public Hero (float x, float y) {
		super(x, y, HERO_WIDTH, HERO_HEIGHT);
		state = IDLE;
	}
	
	public void update(float deltaTime){
		processInput();
		
		velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.x = position.x - bounds.width / 2;
		bounds.y = position.y - bounds.height / 2;
	}
	
	private void processInput(){
		if (state == DEAD || state == DYING) return;
		
		//Admito hasta dos toques simultáneos, saltar y disparar.
		float x0 = (Gdx.input.getX(0) / (float)Gdx.graphics.getWidth()) * 480;
		float x1 = (Gdx.input.getX(1) / (float)Gdx.graphics.getWidth()) * 480;
		float y0 = (Gdx.input.getY(0) / (float)Gdx.graphics.getHeight()) * 320;
		float y1 = (Gdx.input.getY(1) / (float)Gdx.graphics.getHeight()) * 320;
		
		if(((x0<240 && y0>160) || (x1<240 && y1>160) || Gdx.input.isKeyPressed(Keys.W)) && state!=JUMP){
			jump();
		}
		if((x0>240 && y0>160) || (x1>240 && y1>160) || Gdx.input.isKeyPressed(Keys.SPACE)){
			shoot();
		}
		if((x0<240 && y0<160) || (x1<240 && y1<160) || Gdx.input.isKeyPressed(Keys.A)){
			direction=-1;
		}
		if((x0>240 && y0<160) || (x1>240 && y1<160) || Gdx.input.isKeyPressed(Keys.D)){
			direction=1;
		}
		
	}
	
	public void run(int direction){
		this.direction=direction;
		state=RUN;
	}
	
	public void jump(){
		velocity.y = HERO_JUMP_SPEED;
        state = JUMP;
	}
	
	public void shoot(){
		
	}
}
