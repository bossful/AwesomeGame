package com.niejun.androidgame.framework.impl;

import android.media.SoundPool;

import com.niejun.androidgame.framework.Sound;

public class AndroidSound implements Sound {

	int soundId;
	SoundPool soundPool;
	
	public AndroidSound(SoundPool soundPool, int soundId){
		super();
		this.soundPool = soundPool;
		this.soundId = soundId;
	}
	
	@Override
	public void play(float volume) {
		this.soundPool.play(this.soundId, volume, volume, 0, 0, 1);
	}

	@Override
	public void dispose() {
		this.soundPool.unload(this.soundId);
	}

}
