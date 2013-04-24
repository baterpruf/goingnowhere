package com.goingnowhere.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.goingnowhere.utils.CollisionTest;

public class Hero extends DynamicGameObject {
	public static final float HERO_WIDTH = 0.8f;
	public static final float HERO_HEIGHT = 0.8f;
	public static final float HERO_ACCELERATION = 0.3f;	
	public static final float HERO_JUMP_SPEED = 1f;
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
		accel.x = HERO_ACCELERATION * direction;
		vel.x+=accel.x*deltaTime;
		vel.y+= World.gravity.y * deltaTime;
		//Gdx.app.log("v",""+vel.y );
		tryMove(vel.x*deltaTime,vel.y*deltaTime);
		//position.add(vel);
		//bounds.x+=vel.x;
		//bounds.y+=vel.y;
	}
	
	private void processInput(){
		if (state == DEAD || state == DYING) return;
		//Los controles podrían ser la mitad inferior izquierda/derecha disparando simultaneamente
		//y mitad superior donde sea para saltar.
		//Admito hasta dos toques simultáneos, saltar y disparar.
		boolean touchInput=false;
		boolean jump=false;
		
		if(touchInput && Gdx.input.justTouched()){
			float x0 = (Gdx.input.getX(0) / (float)Gdx.graphics.getWidth());
			float x1 = (Gdx.input.getX(1) / (float)Gdx.graphics.getWidth());
			float y0 = (Gdx.input.getY(0) / (float)Gdx.graphics.getHeight());
			float y1 = (Gdx.input.getY(1) / (float)Gdx.graphics.getHeight());
			if(y0>0.5 || y1>0.5){
				jump=true;
			}else if(x0<0.5 || x1<0.5){
				direction=-1;
				shoot();
			}else{
				direction=1;
				shoot();
			}
			
		}
		if(!touchInput){
			if((Gdx.input.isKeyPressed(Keys.W)) && state!=JUMP){
				jump=true;
				Gdx.app.log("ini","jump!" );
			}
			if(Gdx.input.isKeyPressed(Keys.A)){
				direction=-1;
				shoot();
			}
			if(Gdx.input.isKeyPressed(Keys.D)){
				direction=1;
				shoot();
			}
		}
		if(jump)jump();
		
	}
	private void tryMove(float dx,float dy){
		float stepX, stepY;
		//TODO hacer una lista de bloques candidatos a colisión para no tener que mirar todos y mejorar rendimiento
		//TODO si se va a mantener la cuadrícula de bloques del map se podría simplificar mucho todo teniendo en cuenta que 
		//...solo vas a encontrar bloques en las posiciones enteras
		int len = world.blocks.size();
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			for(stepX=0;stepX<Math.abs(dx);stepX+=0.01f){
				bounds.x+=stepX*Math.signum(dx);
				if (CollisionTest.overlapRectangles(block.bounds, bounds)) {
					bounds.x-=stepX*Math.signum(dx);
					vel.x=0;
					break;
				}
			}
			for(stepY=0;stepY<Math.abs(dy);stepY+=0.01f){
				bounds.y+=stepY*Math.signum(dy);
				if (CollisionTest.overlapRectangles(block.bounds, bounds)) {
					bounds.y-=stepY*Math.signum(dy);
					vel.y=0;
					state=IDLE;
					break;
				}
			}
		}
		position.x = bounds.x - bounds.width / 2;
		position.y = bounds.y - bounds.height / 2;
		
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
