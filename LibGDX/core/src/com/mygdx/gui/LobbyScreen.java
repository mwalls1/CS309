package com.mygdx.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import util.Constants;

public class LobbyScreen extends Game implements Screen{
	private Skin skin;
	private Stage stage;
	private Game game;
	
	
	public LobbyScreen(Game game)
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
		 Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
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
		// TODO Auto-generated method stub
		stage.dispose();
	}
	

	
	@Override
	public void create() {
		// TODO Auto-generated method stub
        skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
        stage = new Stage();

        final TextButton backButton = new TextButton("Back", skin, "default");
        final TextButton privateGameButton = new TextButton("Private Game", skin, "default");
        final TextButton publicGameButton = new TextButton("Public Game", skin, "default");
        final TextButton singlePlayerButton = new TextButton("Single Player", skin, "default");
        final Label userInfoLabel = new Label("HI GUYS: " + Constants.user, skin, "default");
        userInfoLabel.setWidth(Gdx.graphics.getWidth() / 3);
		userInfoLabel.setHeight(Gdx.graphics.getHeight() / 20);
		userInfoLabel.setPosition(Gdx.graphics.getWidth()-userInfoLabel.getWidth(), Gdx.graphics.getHeight()-userInfoLabel.getHeight());
        
        backButton.setWidth(Gdx.graphics.getWidth() / 3);
        privateGameButton.setWidth(Gdx.graphics.getWidth() / 3);
        publicGameButton.setWidth(Gdx.graphics.getWidth() / 3);
        singlePlayerButton.setWidth(Gdx.graphics.getWidth() / 3);
        
        backButton.setHeight(Gdx.graphics.getHeight() / 20);
        privateGameButton.setHeight(Gdx.graphics.getHeight() / 20);
        publicGameButton.setHeight(Gdx.graphics.getHeight() / 20);
        singlePlayerButton.setHeight(Gdx.graphics.getHeight() / 20);
        
        userInfoLabel.setWidth(Gdx.graphics.getWidth() / 3);
		userInfoLabel.setHeight(Gdx.graphics.getHeight() / 20);
		userInfoLabel.setPosition(Gdx.graphics.getWidth()-userInfoLabel.getWidth(), Gdx.graphics.getHeight()-userInfoLabel.getHeight());
        
        backButton.setPosition(Gdx.graphics.getWidth() /2 - backButton.getWidth()/2, Gdx.graphics.getHeight()/2);
        privateGameButton.setPosition(Gdx.graphics.getWidth() /2 - privateGameButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET);
        publicGameButton.setPosition(Gdx.graphics.getWidth() /2 - publicGameButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET*2);
        singlePlayerButton.setPosition(Gdx.graphics.getWidth() /2 - singlePlayerButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET*3);
        
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new MainScreen(game));
            }
        });
        
        privateGameButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	privateGameButton.setText("b");
            	
            }
        });
        
        publicGameButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	game.setScreen(new MultiplayerLobby(game));
            }
        });
        
        singlePlayerButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new  SinglePlayerGameSelectScreen(game));
            }
        });
        
        stage.addActor(backButton);
        stage.addActor(privateGameButton);
        stage.addActor(publicGameButton);
        stage.addActor(singlePlayerButton);
        stage.addActor(userInfoLabel);

        Gdx.input.setInputProcessor(stage);

	}

}
