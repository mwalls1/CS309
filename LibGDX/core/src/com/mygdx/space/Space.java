package com.mygdx.space;

import java.util.Random;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gui.MainScreen;

public class Space extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Viewport viewport;
	private BitmapFont font;
	private Game game;
	private Sprite sprite;
	private Level level;
	private int levelNum = 1;
	private Ship[] enemies;
	public static double score = 0;
	public static double shotsTaken = 0;
	public static double shotsLanded = 0;
	private PlayerShip player;
	private boolean shotPressed;
	private  Random rand = new Random();	
	private final int LAST_LEVEL = 7;
	private double accuracy;
	private Timer timer;
	
	
	
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
	     if (Gdx.input.isKeyPressed(Keys.R)) create();
	     player.draw(batch);
	     
		 enemies[rand.nextInt(enemies.length)].shoot(batch);
	    // enemy.draw(batch);
	     for (int i = 0; i < enemies.length; i++)
	     {
	    	 if (enemies[i].getType() == "Captain") enemies[i].shoot(batch);
	    	 if (enemies[i].isShotFired()) player.collision(enemies[i].getShot());
	    	 if (enemies[i].isAlive())
	    		 {
	    		 	enemies[i].draw(batch);
	    		 	enemies[i].move();
	    		 }
	    	 if (enemies[i].isShotFired()) enemies[i].shoot(batch);
	     }
	    	
	    // enemy.move(0,0);
//	     player.collision(enemy.getSprite());
//	     enemy.collision(player.getSprite());
	  
	    
	    if (shotPressed)
	    {
	    	player.shoot(batch);
	    	for (int j = 0; j < enemies.length; j++)
	    	{
	    		enemies[j].collision(player.getShotOneSprite());
	    		enemies[j].collision(player.getShotTwoSprite());
	    	}
	    }
	     
	    accuracy = 0;
	    if (shotsLanded != 0 && shotsTaken != 0) accuracy = shotsLanded/shotsTaken;
	    
	     font.draw(batch, "Score\n" + score + "\nStage: "+ levelNum + "\nAccuracy: " + accuracy, 100, 200);
	     batch.end();
		
	     if (isComplete())
	     {
	    	 timer = new Timer();
	    	 batch.begin();
	    	 int scoreAdded = (int)accuracy * 1000;
	    	 font.draw(batch, "Level " + levelNum + " complete! \nAccuracy Bonus: " + scoreAdded, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	    	 score += (int)(accuracy * 1000);
	    	 levelNum++;
	    	 if (levelNum == LAST_LEVEL) game.setScreen(new MainScreen(game));
	    	 level = new Level(levelNum, player);
	    	 batch.end();
	     }
	}

	@Override
	public void resize(int width, int height) {
		create();
	}

	@Override
	public void pause() {
		
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
		font.dispose();
		for (int i = 0; i<enemies.length; i++) enemies[i].dispose();
	}
	

	
	/**
	 * Describes button functionality and position
	 */
	public void create() {
		font = new BitmapFont();
		score = 0;
		level = new Level(levelNum, player);
		shotsLanded = 0;
		shotsTaken = 0;
		
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
		if ((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) && sprite.getX() > 90) player.getSprite().translate(-sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) && sprite.getX() < Gdx.graphics.getWidth()-100) player.getSprite().translate(sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.DPAD_UP)) && sprite.getY() < 50) player.getSprite().translate(0,sprMoveSpeed);
		if ((Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) && sprite.getY() > 5) player.getSprite().translate(0,-sprMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.SPACE)) shotPressed = true;
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) pause();
		
	}
	
public boolean isComplete()
{
	for (int i = 0; i<enemies.length; i++) {
		if (enemies[i].isAlive()) return false;
	}
	return true;
}
	}

