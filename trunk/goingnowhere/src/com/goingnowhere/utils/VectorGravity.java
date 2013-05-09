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
		super(0,World.gravityG);
		this.angle=angle;
	}
	
	public void setAngle(float angle){
		this.angle=angle;
	}
	public float getAngle(){
		return this.angle;
	}
	public void advance(float alpha){
		angle+=alpha;
		if(angle>=360){
			angle=0;
		}
		setAngle(angle);
	}
	public float getWorldAngle(){
		return angle+90;
	}

}
