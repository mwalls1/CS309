package com.mygdx.games;

import com.mygdx.gui.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import util.Constants;

public class GameTest extends Game implements Screen{
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
	private int daWayx;
	private int daWayy;
	private Texture dawayTexture;
	private Texture background;
	private CharSequence str;
	private Sound theway;
	private Player player;
	private ShapeRenderer shape;
	private int height;
	private int width;
	private Sprite knuckles;
	
	public GameTest(Game game)
	{
		this.game = game;
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		camera.update();
		player = new Player();
		shape = new ShapeRenderer();
		knuckles = new Sprite(new Texture(Gdx.files.internal("Daway.png")));
		knuckles.setPosition(width/2, height/2);
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
		 Gdx.gl.glClearColor(1, 1, 1, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	     batch.begin();
		 batch.draw(background, -1*(camera.position.x - Gdx.graphics.getWidth()/2), -1*(camera.position.y - Gdx.graphics.getHeight()/2));
		 knuckles.draw(batch);
	     font.setColor(Color.BLACK);
	     str = "Sprite x,y pos: "+player.getX()+", "+player.getY();
	     font.draw(batch, str, 10, 20);
	     str = "Camera x,y pos: "+camera.position.x+", "+camera.position.y;
	     font.draw(batch, str, 10, 60);
	     batch.end();
	     player.render(shape, camera, knuckles);
	     player.update();
	     if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
	     {
	    	 game.setScreen(new MainScreen(game));
	     }
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			theway.stop();
			theway.play();
		}
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
		Gdx.graphics.setResizable(false);
		font = new BitmapFont();
		batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("island2.png"));
		skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        final TextButton backButton = new TextButton("Back", skin, "default");
        theway = Gdx.audio.newSound(Gdx.files.internal("douknow.mp3"));
        backButton.setWidth(Constants.BUTTON_WIDTH);
        backButton.setHeight(Constants.BUTTON_HEIGHT);
        
        
        backButton.setPosition(10, Gdx.graphics.getHeight()-60);
        
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            }
        });
        
        
        stage.addActor(backButton);
        
        Gdx.input.setInputProcessor(stage);

	}

}
