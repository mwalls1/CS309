package com.mygdx.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import util.Constants;

public class LobbyScreen extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Skin skin;
	private Stage stage;
	private Viewport viewport;
	private TextureAtlas atlas;
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Game game;
	private float offset = Gdx.graphics.getHeight() / 10;
	
	
	public LobbyScreen(Game game)
	{
		this.game = game;
		create();
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		 Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		offset = Gdx.graphics.getHeight() / 10;
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
		batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        final TextButton backButton = new TextButton("Back", skin, "default");
        final TextButton privateGameButton = new TextButton("Private Game", skin, "default");
        final TextButton publicGameButton = new TextButton("Public Game", skin, "default");
        final TextButton singlePlayerButton = new TextButton("Single Player", skin, "default");
        
        backButton.setWidth(Constants.BUTTON_WIDTH);
        privateGameButton.setWidth(Constants.BUTTON_WIDTH);
        publicGameButton.setWidth(Constants.BUTTON_WIDTH);
        singlePlayerButton.setWidth(Constants.BUTTON_WIDTH);
        
        backButton.setHeight(Constants.BUTTON_HEIGHT);
        privateGameButton.setHeight(Constants.BUTTON_HEIGHT);
        publicGameButton.setHeight(Constants.BUTTON_HEIGHT);
        singlePlayerButton.setHeight(Constants.BUTTON_HEIGHT);
        
        backButton.setPosition(Gdx.graphics.getWidth() /2 - backButton.getWidth()/2, Gdx.graphics.getHeight()/2);
        privateGameButton.setPosition(Gdx.graphics.getWidth() /2 - privateGameButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET);
        publicGameButton.setPosition(Gdx.graphics.getWidth() /2 - publicGameButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET*2);
        singlePlayerButton.setPosition(Gdx.graphics.getWidth() /2 - singlePlayerButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET*3);
        
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	game.setScreen(new MainScreen(game));
            }
        });
        
        privateGameButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	privateGameButton.setText("b");
            }
        });
        
        publicGameButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	publicGameButton.setText("b");
            }
        });
        
        singlePlayerButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	game.setScreen(new  SinglePlayerGameSelectScreen(game));
            }
        });
        
        stage.addActor(backButton);
        stage.addActor(privateGameButton);
        stage.addActor(publicGameButton);
        stage.addActor(singlePlayerButton);
        
        Gdx.input.setInputProcessor(stage);

	}

}