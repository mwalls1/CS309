package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.gui.*;

public class MyGdxGame extends Game {
	private Game game = this;
	@Override
	public void create() {
		game.setScreen(new MainScreen(game));
	}
	
	
	
	
	

}
