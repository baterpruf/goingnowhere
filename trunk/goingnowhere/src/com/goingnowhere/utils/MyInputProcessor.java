package com.goingnowhere.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.goingnowhere.logic.World;

public class MyInputProcessor implements InputProcessor {
	World world;
	int jumpPointer;
	int leftPointer;
	int rightPointer;
	
	
	public MyInputProcessor(World world){
		this.world=world;
	}
	
	@Override
	public boolean keyDown (int keycode) {
		if(keycode==Keys.A){
			world.hero.goLeft();
		}
		if(keycode==Keys.D){
			world.hero.goRight();
		}
		if(keycode==Keys.W){
			world.hero.jump();
		}
		if(keycode==Keys.I){
			Gdx.app.log("gravity angle", ""+world.gravity.getAngle());
			Gdx.app.log("hero angle", ""+world.hero.angle);
		}
		return false;
	}

	@Override
	public boolean keyUp (int keycode) {
		if(keycode==Keys.A){
			world.hero.stop();
		}
		if(keycode==Keys.D){
			world.hero.stop();
		}
		if(keycode==Keys.W){

		}
		if(keycode==Keys.Q){
			int newAngle=(int)Math.floor(Math.random()*4)*90;
			Gdx.app.log("grav:", ""+newAngle);
			world.gravity.setAngle(newAngle);
			world.hero.needRotation=true;
		}
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		float width=(float)Gdx.graphics.getWidth();
		if(x<0.25*width){
			world.hero.goLeft();
			leftPointer=pointer;
		}else if(x<0.5*width){
			world.hero.goRight();
			rightPointer=pointer;
		}else{
			world.hero.jump();
			jumpPointer=pointer;
		}
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		if(pointer==leftPointer){
			world.hero.stop();
			leftPointer=-1;
		}
		if(pointer==rightPointer){
			world.hero.stop();
			rightPointer=-1;
		}
		if(pointer==jumpPointer){
			jumpPointer=-1;
		}
		return false;
	}

	@Override
	public boolean touchDragged (int x, int y, int pointer) {
		return false;
	}

	public boolean touchMoved (int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled (int amount) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
}