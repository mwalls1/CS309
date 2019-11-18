package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.goFish.GoFishScreen;

public class MyGdxGame extends Game {
	private Game game = this;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		game.setScreen(new GoFishScreen(game));
	}
	
	
	
	
	

}
