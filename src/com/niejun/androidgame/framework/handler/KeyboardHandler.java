package com.niejun.androidgame.framework.handler;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnKeyListener;

import com.niejun.androidgame.framework.Input.KeyEvent;
import com.niejun.androidgame.framework.Pool;
import com.niejun.androidgame.framework.PoolObjectFactory;

public class KeyboardHandler implements OnKeyListener {

	boolean[] pressdKeys = new boolean[128];
	Pool<KeyEvent> keyEventPool;
	List<KeyEvent> keyEventsBuffer = new ArrayList<KeyEvent>();
	List<KeyEvent> keyEvents = new ArrayList<KeyEvent>();
	
	public KeyboardHandler(View view) {
		this.keyEventPool = new Pool<KeyEvent>(new PoolObjectFactory<KeyEvent>() {
			@Override
			public KeyEvent createObject() {
				return new KeyEvent();
			}
			
		}, 100);
		view.setOnKeyListener(this);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}

	@Override
	public boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
		if(event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE){
			return false;
		}
		synchronized(this){
			KeyEvent keyEvent = this.keyEventPool.newObject();
			keyEvent.keyCode = keyCode;
			keyEvent.keyChar = (char)event.getUnicodeChar();
			if(event.getAction() == android.view.KeyEvent.ACTION_DOWN){
				keyEvent.type = KeyEvent.KEY_DOWN;
				if(keyCode > 0 && keyCode < 127){
					this.pressdKeys[keyCode] = true;
				}
			}
			if(event.getAction() == android.view.KeyEvent.ACTION_UP){
				keyEvent.type = KeyEvent.KEY_UP;
				if(keyCode > 0 && keyCode < 127){
					this.pressdKeys[keyCode] = false;
				}
			}
			this.keyEventsBuffer.add(keyEvent);
		}
		return false;
	}

	public boolean isKeyPressed(int keyCode){
		if(keyCode > 0 && keyCode < 127){
			return false;
		}
		return this.pressdKeys[keyCode];
	}
	
	public List<KeyEvent> getKeyEvents(){
		synchronized(this){
			int len = this.keyEvents.size();
			for(int i = 0; i< len; i++){
				this.keyEventPool.free(this.keyEvents.get(i));
			}
			this.keyEvents.clear();
			this.keyEvents.addAll(this.keyEventsBuffer);
			this.keyEventsBuffer.clear();
			return this.keyEvents;
		}
	}
}
