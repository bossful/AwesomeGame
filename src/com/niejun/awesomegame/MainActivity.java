package com.niejun.awesomegame;

import com.niejun.androidgame.framework.Screen;
import com.niejun.androidgame.framework.impl.AndroidGame;

public class MainActivity extends AndroidGame {

	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}


}
