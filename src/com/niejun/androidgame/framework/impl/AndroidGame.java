package com.niejun.androidgame.framework.impl;

import com.niejun.androidgame.framework.Audio;
import com.niejun.androidgame.framework.FileIO;
import com.niejun.androidgame.framework.Game;
import com.niejun.androidgame.framework.Graphics;
import com.niejun.androidgame.framework.Input;
import com.niejun.androidgame.framework.Screen;

import android.app.Activity;

public abstract class AndroidGame extends Activity implements Game {

	@Override
	public Input getInput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileIO getFileIO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Graphics getGraphics() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Audio getAudio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setScreen(Screen screen) {
		// TODO Auto-generated method stub

	}

	@Override
	public Screen getCurrentScreen() {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract Screen getStartScreen();

}
