package com.goingnowhere.utils;

import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.logic.World;

public class VectorGravity extends Vector2 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int angle;
	
	public VectorGravity(int angle){
		super((float) (World.gravityG*Math.sin((double)angle)),(float) (World.gravityG*Math.cos((double)angle)));
		this.angle=angle;
	}
	
	public void setAngle(int angle){
		this.x=(float) (World.gravityG*Math.sin((double)angle));
		this.y=(float) (World.gravityG*Math.cos((double)angle));
	}

}
