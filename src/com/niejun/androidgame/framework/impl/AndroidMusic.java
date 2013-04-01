package com.niejun.androidgame.framework.impl;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.niejun.androidgame.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener {

	MediaPlayer mediaPlayer;
	boolean isPrepared = false;

	public AndroidMusic(AssetFileDescriptor assetDescriptor) {
		this.mediaPlayer = new MediaPlayer();
		try {
			this.mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
					assetDescriptor.getStartOffset(), assetDescriptor.getLength());
			this.mediaPlayer.prepare();
			this.isPrepared = true;
			this.mediaPlayer.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException("coundn't load music");
		}
	}

	@Override
	public void play() {
		if(this.mediaPlayer.isPlaying()){
			return;
		}else{
			try{
				synchronized (this) {
					if(!this.isPrepared){
						this.mediaPlayer.prepare();
					}
					this.mediaPlayer.start();
				}
			}catch(Exception e){
				//TODO 
			}
		}
	}

	@Override
	public void stop() {
		this.mediaPlayer.stop();
		synchronized(this){
			this.isPrepared = false;
		}
	}

	@Override
	public void pause() {
		if(this.mediaPlayer.isPlaying()){
			this.mediaPlayer.pause();
		}
	}

	@Override
	public void setLooping(boolean looping) {
		this.mediaPlayer.setLooping(looping);
	}

	@Override
	public void setVolume(float volume) {
		this.mediaPlayer.setVolume(volume, volume);
	}

	@Override
	public boolean isPlaying() {
		return this.mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped() {
		return !this.isPrepared;
	}

	@Override
	public boolean isLooping() {
		return this.mediaPlayer.isLooping();
	}

	@Override
	public void dispose() {
		if(this.mediaPlayer.isPlaying()){
			this.mediaPlayer.stop();
		}
		this.mediaPlayer.release();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		synchronized(this){
			this.isPrepared = false;
		}
	}

}
