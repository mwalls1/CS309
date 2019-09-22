package com.mygdx.space;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Space extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private Texture texture;
	private BitmapFont font;
	private Game game;
	private Sprite sprite;
	private Goon enemy;
	private Sprite eSprite;
	private int frameCounter = 0;
	private Level level;
	private Ship[] enemies;
	public static double score = 0;
	private boolean alive[];
	private PlayerShip player;
	private boolean shotPressed;
	
	/**
	 * Main screen with play, options and leaderboard buttons
	 * @param game game object, we use Game's setScreen() method to switch between different screens
	 */

	public Space(Game game)
	{
		this.game = game;
			}
	/**
	 * From screen interface, called when this screen is set
	 */
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Runs every frame, draws updated stage and sets background color
	 */
	public void render(float delta) {
		// TODO Auto-generated method stub
		 Gdx.gl.glClearColor((float)4/255, (float)7/255, (float)40/255, 1); //Color of background
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Don't know why but you need this
	     batch = new SpriteBatch();
	     
	     enemies = level.getShips();
	     batch.begin();
	     handleInput(delta);
	     player.draw(batch);
	    // enemy.draw(batch);
	     for (int i = 0; i < enemies.length; i++)
	     {
	    	 if (enemies[i].isAlive())
	    		 {
	    		 	enemies[i].draw(batch);
	    		 	enemies[i].move(0.0f,0.0f);
	    		 }
	     }
	    	
	    // enemy.move(0,0);
//	     player.collision(enemy.getSprite());
//	     enemy.collision(player.getSprite());
	     
	    if (shotPressed)
	    {
	    	player.shoot(batch);
	    	enemies[0].shoot(batch);
	    	for (int j = 0; j < enemies.length; j++)
	    	{
	    		enemies[j].collision(player.getShotOneSprite());
	    		enemies[j].collision(player.getShotTwoSprite());
	    	}
	    }
	     
	     font.draw(batch, "Score\n" + score, 100, 50);
	     batch.end();
		
		
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
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	/**
	 * Clears screen to free memory
	 */
	public void dispose() {
	}
	

	
	/**
	 * Describes button functionality and position
	 */
	public void create() {
		font = new BitmapFont();
		score = 0;
		level = new Level(1);
		player = new PlayerShip();
		sprite = player.getSprite();
		
	}
	
	private void handleInput(float deltaTime)
	{

		if (Gdx.app.getType() != Application.ApplicationType.Desktop)
		{
			return;
		}
		float sprMoveSpeed = 250 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.R)) create();
		if ((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) && sprite.getX() > 90) player.move(-sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) && sprite.getX() < Gdx.graphics.getWidth()-100) player.move(sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.DPAD_UP)) && sprite.getY() < 50) player.move(0,sprMoveSpeed);
		if ((Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) && sprite.getY() > 5) player.move(0,-sprMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.SPACE)) shotPressed = true;
		
		
	}
	

	}

