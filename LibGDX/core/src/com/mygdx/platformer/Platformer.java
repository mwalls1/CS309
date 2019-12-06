package com.mygdx.platformer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.gui.MainScreen;

public class Platformer extends Game implements Screen{
	private lvlOne levelOne;
	private Stage stage;
	private Game game;
	public Platformer(Game game)
	{
		this.game = game;
		create();
	}
	@Override
	public void create() {
		stage = new Stage();
		levelOne = new lvlOne(game);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		levelOne.render(delta);
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
	     {
			 levelOne.dispose();
	    	 dispose();
	    	 game.setScreen(new MainScreen(game));
	     }
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		
	}

}

