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
import com.mygdx.games.*;
import util.Constants;

public class MainScreen extends Game implements Screen{
	private TextButton playButton;
	private TextButton leaderboardButton;
	private TextButton optionsButton;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Skin skin;
	private Stage stage;
	private Viewport viewport;
	private TextureAtlas atlas;
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Game game;
	
	public MainScreen(Game game)
	{
		this.game = game;
		create();
			}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
		
	}
	private void init()
	{
		
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
		// TODO Auto-generated method stub
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

        final TextButton playButton = new TextButton("Play", skin, "default");
        final TextButton leaderboardButton = new TextButton("Leaderboards", skin, "default");
        final TextButton optionsButton = new TextButton("Options", skin, "default");
        
        playButton.setWidth(Gdx.graphics.getWidth() / 3);
        leaderboardButton.setWidth(Gdx.graphics.getWidth() / 3);
        optionsButton.setWidth(Gdx.graphics.getWidth() / 3);
        
        playButton.setHeight(Gdx.graphics.getHeight() / 20);
        leaderboardButton.setHeight(Gdx.graphics.getHeight()/ 20);
        optionsButton.setHeight(Gdx.graphics.getHeight() / 20);
        
        playButton.setPosition(Gdx.graphics.getWidth() /2 - playButton.getWidth()/2, Gdx.graphics.getHeight()/2);
        leaderboardButton.setPosition(Gdx.graphics.getWidth() /2 - leaderboardButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET);
        optionsButton.setPosition(Gdx.graphics.getWidth() /2 - optionsButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET*2);
       
        playButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Do some stuff when clicked
            	playButton.setText("a");
            	game.setScreen(new LobbyScreen(game));
            }
        });
        
        leaderboardButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Do some stuff when clicked
            	game.setScreen(new LeaderboardScreen(game));
            }
        });
        
        optionsButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Do some stuff when clicked
            	optionsButton.setText("a");
            	game.setScreen(new OptionsScreen(game));
            	
            }
        });
        
        stage.addActor(playButton);
        stage.addActor(leaderboardButton);
        stage.addActor(optionsButton);
        
        Gdx.input.setInputProcessor(stage);

	}

}
