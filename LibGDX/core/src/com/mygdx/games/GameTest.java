package com.mygdx.games;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
	private int daWayx = Gdx.graphics.getWidth()/2;
	private int daWayy = Gdx.graphics.getHeight()/2;
	private Texture dawayTexture;
	private Sprite sprite;
	
	
	public GameTest(Game game)
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
		 Gdx.gl.glClearColor(1, 1, 1, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	     if(Gdx.input.isKeyPressed(Input.Keys.A))
	     {
	    	 if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
	    		 sprite.translateX(-10f);
	    	 else
	    		 sprite.translateX(-5f);
	     }
	     if(Gdx.input.isKeyPressed(Input.Keys.D))
	     {
	    	 if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
	    		 sprite.translateX(10f);
	    	 else
	    		 sprite.translateX(5f);
	     }
	     if(Gdx.input.isKeyPressed(Input.Keys.W))
	     {
	    	 if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
	    		 sprite.translateY(10f);
	    	 else
	    		 sprite.translateY(5f);
	     }
	     if(Gdx.input.isKeyPressed(Input.Keys.S))
	     {
	    	 if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
	    		 sprite.translateY(-10f);
	    	 else
	    		 sprite.translateY(-5f);
	     }
		 batch.begin();
	     sprite.draw(batch);
	     batch.end();
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
		batch = new SpriteBatch();
		FileHandle dawayFile = Gdx.files.internal("daway.jpg");//add file handle
        dawayTexture = new Texture(dawayFile);
        sprite = new Sprite(dawayTexture);
		skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        final TextButton backButton = new TextButton("Back", skin, "default");

        backButton.setWidth(Constants.BUTTON_WIDTH);
        
        backButton.setHeight(Constants.BUTTON_HEIGHT);
        
        
        backButton.setPosition(10, Gdx.graphics.getHeight()-50);
        
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            }
        });
        
        
        stage.addActor(backButton);
        
        Gdx.input.setInputProcessor(stage);

	}

}
