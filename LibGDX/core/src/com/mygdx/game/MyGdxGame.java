package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.games.GameTest;
import com.mygdx.gui.MainScreen;

public class MyGdxGame extends Game {
	private boolean paused;
	private Game game = this;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		game.setScreen(new MainScreen(game));
	}
	
	
	
	
	

}
