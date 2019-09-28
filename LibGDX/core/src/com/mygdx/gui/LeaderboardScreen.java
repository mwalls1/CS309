package com.mygdx.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import util.Constants;

public class LeaderboardScreen extends Game implements Screen{
	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private Game game;
	
	
	public LeaderboardScreen(Game game)
	{
		this.game = game;
		create();
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		create();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
	

	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        final TextButton backButton = new TextButton("Back", skin, "default");
        
        backButton.setWidth(Constants.BUTTON_WIDTH);        
        backButton.setHeight(Constants.BUTTON_HEIGHT);
        
        backButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2);
       
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Do some stuff when clicked
            	dispose();
            	game.setScreen(new MainScreen(game));
            }
        });
       
        
        
        
        stage.addActor(backButton);
        

	}

}
