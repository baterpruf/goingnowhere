package com.goingnowhere.utils;

import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.logic.World;

public class VectorGravity extends Vector2 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float angle;
	
	public VectorGravity(double d){
		super((float) (World.gravityG*Math.cos(d)),(float) (World.gravityG*Math.sin(d)));
		this.angle=(float)d;
	}
	
	public void setAngle(int angle){
		this.x=(float) (World.gravityG*Math.cos((double)angle));
		this.y=(float) (World.gravityG*Math.sin((double)angle));
	}

}
