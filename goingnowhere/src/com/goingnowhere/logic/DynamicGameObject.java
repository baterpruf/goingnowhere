package com.goingnowhere.logic;

import com.badlogic.gdx.math.Vector2;

public class DynamicGameObject extends GameObject {
	static final int LEFT = -1;
	static final int RIGHT = 1;
	
	public final Vector2 velocity;
	public final Vector2 accel;

	public DynamicGameObject (float x, float y, float width, float height) {
		super(x, y, width, height);
		velocity = new Vector2();
		accel = new Vector2();
	}
}
