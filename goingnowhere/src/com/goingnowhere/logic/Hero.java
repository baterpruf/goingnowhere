package com.goingnowhere.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.goingnowhere.utils.CollisionTest;

public class Hero extends DynamicGameObject {
	public static final float HERO_WIDTH = 20f;
	public static final float HERO_HEIGHT = 20f;
	public static final float HERO_ACCELERATION = 18f;	
	public static final float HERO_JUMP_SPEED = 9f;
	public static final float HERO_MAX_SPEED=12f;
	public static final float DAMP=0.9f;
	
	static final int IDLE = 0;
	static final int RUN = 1;
	static final int JUMP = 2;
	static final int DYING = 3;
	static final int DEAD = 4;
	
	
	int state;
	int direction;
	boolean canJump=false;
	World world;
	
	
	public Hero (World world, float x, float y) {
		super(x, y, HERO_WIDTH, HERO_HEIGHT);
		this.world=world;
		state = IDLE;
	}
	
	public void update(float deltaTime){
		processInput();
		accel.x = HERO_ACCELERATION * direction;
		if(Math.abs(vel.x)<Math.abs(HERO_MAX_SPEED)){
			vel.x+=accel.x*deltaTime;
		}
		vel.y+= World.gravity.y * deltaTime;
		//Gdx.app.log("v",""+vel.y );
		tryMove(vel.x*deltaTime*20,vel.y*deltaTime*20);
		//position.add(vel);
		//bounds.x+=vel.x;
		//bounds.y+=vel.y;
	}
	
	private void processInput(){
		if (state == DEAD || state == DYING) return;
		//Los controles podr�an ser la mitad inferior izquierda/derecha disparando simultaneamente
		//y mitad superior donde sea para saltar.
		//Admito hasta dos toques simult�neos, saltar y disparar.
		boolean touchInput=true;
		boolean jump=false;
		
		if(touchInput && Gdx.input.justTouched()){
			float x0 = (Gdx.input.getX(0) / (float)Gdx.graphics.getWidth());
			float y0 = (Gdx.input.getY(0) / (float)Gdx.graphics.getHeight());
			Gdx.app.log("",""+x0);
			if(y0<0.5 ){
				jump=true;
			}else if(x0<0.5){
				direction=-1;
				vel.x=vel.x/3;
				shoot();
			}else{
				direction=1;
				vel.x=vel.x/3;
				shoot();
			}
			
		}
		if(!touchInput){
			if((Gdx.input.isKeyPressed(Keys.W))){
				jump=true;
			}
			if(Gdx.input.isKeyPressed(Keys.A)){
				if(direction!=-1){
					direction=-1;
					vel.x=vel.x/3;
				}
				shoot();
				
			}
			if(Gdx.input.isKeyPressed(Keys.D)){
				if(direction!=1){
					direction=1;
					vel.x=vel.x/3;
				}
				shoot();
			}
		}
		if(jump && canJump)jump();
		
	}
	private void tryMove(float dx,float dy){
		//TODO hacer una lista de bloques candidatos a colisi�n para no tener que mirar todos y mejorar rendimiento
		//TODO si se va a mantener la cuadr�cula de bloques del map se podr�a simplificar mucho todo teniendo en cuenta que 
		//...solo vas a encontrar bloques en las posiciones enteras
		bounds.x+=dx;
		boolean contact=false;
		int len = world.blocks.size();
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			while (CollisionTest.collision(block.bounds, bounds)) {
				bounds.x-=dx/2;
				contact=true;
			}
		}
		if(contact){
			vel.x=0;
			dx=0;
			contact=false;
		}
		bounds.y+=dy;
		for (int i = 0; i < len; i++) {
			Block block = world.blocks.get(i);
			while (CollisionTest.collision(block.bounds, bounds)) {
				bounds.y-=dy/2;
				contact=true;
				if(dy<0)canJump=true;
			}
		}
		if(contact){
			vel.y=0;
			dy=0;
		}
		position.x = bounds.x + bounds.width / 2;
		position.y = bounds.y + bounds.height / 2;
	}
	public void run(int direction){
		this.direction=direction;
		state=RUN;
		accel.x = HERO_ACCELERATION * direction;
	}
	
	public void jump(){
		vel.y = HERO_JUMP_SPEED;
		canJump=false;
	}
	
	public void shoot(){
		
	}
}
