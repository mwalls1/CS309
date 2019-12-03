package com.mygdx.game;

import com.badlogic.gdx.Game;

import Race.RaceScreen;

public class MyGdxGame extends Game {
	private Game game = this;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		game.setScreen(new RaceScreen(game));
	}
	
	
	
	
	

}
