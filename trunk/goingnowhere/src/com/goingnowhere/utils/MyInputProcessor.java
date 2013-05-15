package com.goingnowhere.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.goingnowhere.logic.World;

public class MyInputProcessor implements InputProcessor {
	World world;
	public static final int NONE = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int JUMP = 3;
	public static final int ROTATELEFT = 4;
	public static final int ROTATERIGHT = 5;
	int jumpPointer;
	int leftPointer;
	int rightPointer;
	int rotateRightPointer;
	int rotateLeftPointer;
	int isPressed;
	float width=(float)Gdx.graphics.getWidth();
	float height=(float)Gdx.graphics.getHeight();
	
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
		if(keycode==Keys.Q){
			world.hero.rotate(45);
		}
		if(keycode==Keys.E){
			world.hero.rotate(-45);
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
		return false;
	}

	@Override
	public boolean keyTyped (char character) {
		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		if(y<0.5*height){
			if(x<0.5*width){
				rotateRightPointer=pointer;
				isPressed=ROTATERIGHT;
			}
			if(x>0.5*width){
				rotateLeftPointer=pointer;
				isPressed=ROTATELEFT;
			}
		}else{

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
		}
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		if(y<0.5*height){
			if(x<0.5*width){
				if(isPressed==ROTATELEFT){
					world.hero.rotate(180);
				}else{
					world.hero.rotate(45);
				}
			}
			if(x>0.5*width){
				if(isPressed==ROTATERIGHT){
					world.hero.rotate(180);
				}else{
					world.hero.rotate(-45);
				}
			}
		}
		isPressed=NONE;
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