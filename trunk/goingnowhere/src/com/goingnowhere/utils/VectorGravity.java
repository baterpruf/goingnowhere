package com.goingnowhere.utils;

import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.logic.World;

public class VectorGravity extends Vector2 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float angle;
	
	public VectorGravity(float angle){
		super((float) (World.gravityG*Math.cos(angle)),(float) (World.gravityG*Math.sin(angle)));
		this.angle=angle;
	}
	
	public void setAngle(float angle){
		this.x=(float) (World.gravityG*Math.cos(angle));
		this.y=(float) (World.gravityG*Math.sin(angle));
	}
	public float getAngle(){
		return this.angle;
	}
	public void advance(float alpha){
		angle+=alpha;
		if(angle>6.24){
			angle=0;
		}
	}

}
