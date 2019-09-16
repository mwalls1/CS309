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

public class LeaderboardScreen extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Skin skin;
	private Stage stage;
	private Viewport viewport;
	private TextureAtlas atlas;
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Game game;
	
	
	public LeaderboardScreen(Game game)
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
		// TODO Auto-generated method stub
		
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
		
	}
	

	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        final TextButton backButton = new TextButton("Back", skin, "default");
        final TextButton leaderboardButton = new TextButton("Public Game", skin, "default");
        final TextButton optionsButton = new TextButton("Private Game", skin, "default");
        
        backButton.setWidth(200f);
        leaderboardButton.setWidth(200f);
        optionsButton.setWidth(200f);
        
        backButton.setHeight(20f);
        leaderboardButton.setHeight(20f);
        optionsButton.setHeight(20f);
        
        backButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2);
        leaderboardButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 50f);
        optionsButton.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 100f);
       
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Do some stuff when clicked
            	backButton.setText("b");
            	game.setScreen(new MainScreen(game));
            }
        });
       
        
        
        
        stage.addActor(backButton);
        
        Gdx.input.setInputProcessor(stage);

	}

}
