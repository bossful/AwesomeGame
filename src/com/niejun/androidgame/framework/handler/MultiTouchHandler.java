package com.niejun.androidgame.framework.handler;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.niejun.androidgame.framework.Input;
import com.niejun.androidgame.framework.Input.TouchEvent;
import com.niejun.androidgame.framework.Pool;
import com.niejun.androidgame.framework.PoolObjectFactory;

public class MultiTouchHandler implements TouchHandler {

	private static final int MAX_TOUCHPOINTS = 10;
	boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
	int[] touchX = new int[MAX_TOUCHPOINTS];
	int[] touchY = new int[MAX_TOUCHPOINTS];
	int[] id = new int[MAX_TOUCHPOINTS];

	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<Input.TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<Input.TouchEvent>();
	float scaleX;
	float scaleY;

	public MultiTouchHandler(View view, float scaleX, float scaleY) {
		this.touchEventPool = new Pool<Input.TouchEvent>(
				new PoolObjectFactory<TouchEvent>() {
					@Override
					public TouchEvent createObject() {
						return new TouchEvent();
					}

				}, 100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized (this) {
			int action = event.getAction() & MotionEvent.ACTION_MASK;
			int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
			int pointerCount = event.getPointerCount();
			TouchEvent touchEvent;
			for(int i = 0; i < MAX_TOUCHPOINTS; i++){
				if(i >= pointerCount){
					this.isTouched[i] = false;
					id[i] = -1;
					continue;
				}
				if(event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex){
					continue;
				}
				int pointerId = event.getPointerId(i);
				switch(action){
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					touchEvent = this.touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DOWN;
					touchEvent.x = touchX[i] = (int) (event.getX(i)*this.scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i)*this.scaleY);
					this.isTouched[i] = true;
					id[i] = pointerId;
					this.touchEventsBuffer.add(touchEvent);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					touchEvent = this.touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_UP;
					touchEvent.x = touchX[i] = (int) (event.getX(i)*this.scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i)*this.scaleY);
					this.isTouched[i] = false;
					id[i] = -1;
					this.touchEventsBuffer.add(touchEvent);
					break;
				case MotionEvent.ACTION_MOVE:
					touchEvent = this.touchEventPool.newObject();
					touchEvent.type = TouchEvent.TOUCH_DRAGGED;
					touchEvent.x = touchX[i] = (int) (event.getX(i)*this.scaleX);
					touchEvent.y = touchY[i] = (int) (event.getY(i)*this.scaleY);
					this.isTouched[i] = true;
					id[i] = -1;
					this.touchEventsBuffer.add(touchEvent);
					break;
				}
			}
			return true;
		}
	}

	private int getIndex(int pointer){
		for(int i = 0; i < MAX_TOUCHPOINTS; i++){
			if(id[i] == pointer){
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public boolean isTouchDown(int pointer) {
		synchronized(this){
			int index = getIndex(pointer);
			if(index < 0 || index > MAX_TOUCHPOINTS){
				return false;
			}else{
				return this.isTouched[index];
			}
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index < 0 || index > MAX_TOUCHPOINTS){
				return 0;
			}else{
				return this.touchX[index];
			}
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized (this) {
			int index = getIndex(pointer);
			if(index < 0 || index > MAX_TOUCHPOINTS){
				return 0;
			}else{
				return this.touchY[index];
			}
		}
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		synchronized(this){
			int len = this.touchEvents.size();
			for(int i = 0; i < len; i++){
				this.touchEventPool.free(this.touchEvents.get(i));
			}
			this.touchEvents.clear();
			this.touchEvents.addAll(this.touchEventsBuffer);
			this.touchEventsBuffer.clear();
			return this.touchEvents;
		}
	}

}
