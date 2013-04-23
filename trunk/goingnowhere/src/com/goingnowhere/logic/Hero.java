package com.goingnowhere.logic;

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
	
	public void update(float delta){
		
	}
	
	public void run(int direction){
		this.direction=direction;
	}
	
	public void jump(){
		velocity.y = HERO_JUMP_SPEED;
        state = JUMP;
	}
	
	public void shoot(){
		
	}
}
