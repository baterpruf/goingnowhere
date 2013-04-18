package com.goingnowhere;

public class Hero extends DynamicGameObject {
	public static final float HERO_WIDTH = 0.8f;
	public static final float HERO_HEIGHT = 0.8f;
	
	int state;
	
	public Hero (float x, float y) {
		super(x, y, HERO_WIDTH, HERO_HEIGHT);
		state = 0;
	}
	
	public void update(){
		
	}
	
	public void run(boolean direction){
		
	}
	
	public void jump(){
		
	}
	
	public void shoot(){
		
	}
}
