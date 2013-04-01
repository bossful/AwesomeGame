package com.niejun.androidgame.framework.handler;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import com.niejun.androidgame.framework.Input;
import com.niejun.androidgame.framework.PoolObjectFactory;
import com.niejun.androidgame.framework.Input.TouchEvent;
import com.niejun.androidgame.framework.Pool;

public class SingleTouchHandler implements TouchHandler {

	boolean isTouched;
	int touchX;
	int touchY;
	Pool<TouchEvent> touchEventPool;
	List<TouchEvent> touchEvents = new ArrayList<Input.TouchEvent>();
	List<TouchEvent> touchEventsBuffer = new ArrayList<Input.TouchEvent>();
	float scaleX;
	float scaleY;
	
	public SingleTouchHandler(View view, float scaleX, float scaleY) {
		this.touchEventPool = new Pool<TouchEvent>(new PoolObjectFactory<TouchEvent>() {

			/* (non-Javadoc)
			 * @see com.niejun.androidgame.framework.PoolObjectFactory#createObject()
			 */
			@Override
			public TouchEvent createObject() {
				return new TouchEvent();
			}
			
		},100);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		synchronized(this){
			TouchEvent touchEvent = this.touchEventPool.newObject();
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				this.isTouched = true;
				break;
			case MotionEvent.ACTION_MOVE:
				touchEvent.type = TouchEvent.TOUCH_DRAGGED;
				this.isTouched = true;
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				touchEvent.type = TouchEvent.TOUCH_UP;
				this.isTouched = false;
				break;
			}
			touchEvent.x = (int)(event.getX()*this.scaleX);
			touchEvent.y = (int)(event.getY()*this.scaleY);
			this.touchEventsBuffer.add(touchEvent);
			return true;
		}
		
	}

	@Override
	public boolean isTouchDown(int pointer) {
		synchronized(this){
			if(pointer == 0){
				return this.isTouched;
			}else{
				return false;
			}
		}
	}

	@Override
	public int getTouchX(int pointer) {
		synchronized(this){
			return this.touchX;
		}
	}

	@Override
	public int getTouchY(int pointer) {
		synchronized(this){
			return this.touchY;
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
