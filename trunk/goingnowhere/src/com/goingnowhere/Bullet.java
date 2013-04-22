package com.goingnowhere;

public class Bullet extends DynamicGameObject {
	public static final float BULLET_WIDTH = 0.2f;
	public static final float BULLET_HEIGHT = 0.2f;
	
	boolean direction;//true=right, false=left
	
	public Bullet (float x, float y, boolean direction) {
		super(x, y, BULLET_WIDTH, BULLET_HEIGHT);
		this.direction = direction;
	}
	
	public void update(){
		
	}
	
}
