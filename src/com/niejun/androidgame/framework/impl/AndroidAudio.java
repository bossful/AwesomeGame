package com.niejun.androidgame.framework.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.niejun.androidgame.framework.Audio;
import com.niejun.androidgame.framework.Music;
import com.niejun.androidgame.framework.Sound;

public class AndroidAudio implements Audio {

	AssetManager assetManager;
	SoundPool soundPool;
	
	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assetManager = activity.getAssets();
		this.soundPool = new SoundPool(20,AudioManager.STREAM_MUSIC,0);
	}

	@Override
	public Music newMusic(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = this.assetManager.openFd(filename);
			return new AndroidMusic(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Coundn't load music '" + filename+"'");
		}
	}

	@Override
	public Sound newSound(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = this.assetManager.openFd(filename);
			int soundId = this.soundPool.load(assetDescriptor, 0);
			return new AndroidSound(this.soundPool,soundId);
		} catch (IOException e) {
			throw new RuntimeException("Coundn't load sound '" + filename+"'");
		}
	}

}
