package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.space.Space;
import com.mygdx.gui.*;

public class MyGdxGame extends Game {
	private boolean paused;
	private Game game = this;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		game.setScreen(new Space(game));
	}
	
	
	
	
	

}
