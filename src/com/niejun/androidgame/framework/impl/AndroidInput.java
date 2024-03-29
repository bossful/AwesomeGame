package com.niejun.androidgame.framework.impl;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.niejun.androidgame.framework.Input;
import com.niejun.androidgame.framework.handler.AccelerometerHandler;
import com.niejun.androidgame.framework.handler.KeyboardHandler;
import com.niejun.androidgame.framework.handler.MultiTouchHandler;
import com.niejun.androidgame.framework.handler.SingleTouchHandler;
import com.niejun.androidgame.framework.handler.TouchHandler;

public class AndroidInput implements Input {

	AccelerometerHandler accelHandler;
	KeyboardHandler keyHandler;
	TouchHandler touchHandler;
	
	@SuppressWarnings("deprecation")
	public AndroidInput(Context context,View view, float scaleX,float scaleY) {
		this.accelHandler = new AccelerometerHandler(context);
		this.keyHandler = new KeyboardHandler(view);
		if(Integer.parseInt(VERSION.SDK) < 5){
			this.touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
		}else{
			this.touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
		}
	}

	@Override
	public boolean isKeyPressed(int keyCode) {
		return this.keyHandler.isKeyPressed(keyCode);
	}

	@Override
	public boolean isTouchDown(int pointer) {
		return this.touchHandler.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		return this.touchHandler.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		return this.touchHandler.getTouchY(pointer);
	}

	@Override
	public float getAccelX() {
		return this.accelHandler.getAccelX();
	}

	@Override
	public float getAccelY() {
		return this.accelHandler.getAccelY();
	}

	@Override
	public float getAccelZ() {
		return this.getAccelZ();
	}

	@Override
	public List<KeyEvent> getKeyEvents() {
		return this.keyHandler.getKeyEvents();
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		return this.touchHandler.getTouchEvents();
	}

}
