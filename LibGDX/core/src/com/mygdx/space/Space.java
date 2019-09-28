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
	private BitmapFont font;
	private Game game;
	private Sprite sprite;
	private Level level;
	private int levelNum = 6;
	private Ship[] enemies;
	public static double score = 0;
	public static double shotsTaken = 0;
	public static double shotsLanded = 0;
	private PlayerShip player;
	private boolean shotPressed;
	private  Random rand = new Random();	
	private final int LAST_LEVEL = 7;
	private double accuracy;
	private Asteroid asteroid;
	private int asteroidSpeedY;
	private int asteroidSpeedX;
	public static int asteroidsShot;
	public static int enemiesKilled;
	
	
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
	     
	     
	     enemies = level.getShips(); //Array of enemy ships determined by current level
	     
	     batch.begin(); //Start drawing
	     handleInput(delta); //Get input from keyboard
	     if (Gdx.input.isKeyPressed(Keys.R)) create(); //Reset; for debugging only
	     player.draw(batch);
	     
		 enemies[rand.nextInt(enemies.length)].shoot(batch);
		 
		if (!asteroid.isIntact() && enemiesKilled < 3 && asteroidsShot < 4) { //Handles logic for streak of hitting asteroids
			asteroid = new Asteroid(asteroidsShot, levelNum);
			asteroidSpeedY = rand.nextInt(1)-2; 
			asteroidSpeedX = rand.nextInt(1)+1;
		}
		 
		 if (asteroid.isIntact()) //Move asteroid as long as it is intact
		 {
			 asteroid.draw(batch);
			 asteroid.move(1, 0);
			
		 }
	     for (int i = 0; i < enemies.length; i++) 
	     {
	    	 if (enemies[i].getType() == "Captain") enemies[i].shoot(batch); //Calls captain's shoot method every frame
	    	 if (enemies[i].isShotFired()) {
	    		 player.collision(enemies[i].getShot()); //Continue moving ship's fire if ship has fired
	    	 }
	    	 if (enemies[i].isAlive())
	    		 {
	    		 	enemies[i].draw(batch);
	    		 	enemies[i].move();
	    		 }
	    	 if (enemies[i].isShotFired()) enemies[i].shoot(batch);
	     }
	    	
	 
	  
	    
	    if (shotPressed) //Handles logic of player ship's fire
	    {
	    	player.shoot(batch);
	    	for (int j = 0; j < enemies.length; j++) //Checks for collisions of player shots with asteroids and enemy ships
	    	{
	    		enemies[j].collision(player.getShotOneSprite());
	    		enemies[j].collision(player.getShotTwoSprite());
	    		asteroid.collision(player.getShotOneSprite());
	    		asteroid.collision(player.getShotTwoSprite());
	    	}
	    }
	     
	    accuracy = 0;
	    if (shotsLanded != 0 && shotsTaken != 0) accuracy = shotsLanded/shotsTaken; //Calculates accuracy, used to avoid division by zero
	    
	     font.draw(batch, "Score\n" + score + "\nStage: "+ levelNum + "\nAccuracy: " + accuracy, 100, 200); //Draws player info
	     batch.end(); //Stop drawing
		
	     if (isComplete()) //If level has been finished
	     {
	    	 asteroidsShot = 0; //Reset asteroid streak counter
	    	 enemiesKilled = 0; //Reset kill count per level
	    	 asteroid = new Asteroid(asteroidsShot, levelNum); //Creates new asteroids for new level
	    	 batch.begin(); //Start drawing
	    	 int scoreAdded = (int)accuracy * 1000; 
	    	 //TODO get this line to work 
	    	 font.draw(batch, "Level " + levelNum + " complete! \nAccuracy Bonus: " + scoreAdded, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
	    	 
	    	 
	    	 score += (int)(accuracy * 1000); //Add accuracy bonus to score
	    	 levelNum++; //Increment level
	    	 shotsTaken = 0; //Reset for accuracy calculation
	    	 shotsLanded = 0; //Reset for accuracy calculation
	    	 //TODO implement real game exiting
	    	 if (levelNum == LAST_LEVEL) game.setScreen(new MainScreen(game)); //Exits game if won
	    	 level = new Level(levelNum, player); //Create new level
	    	 batch.end(); //Stop drawing
	    	 dispose(); //Dispose unused textures
	     }
	}

	@Override
	/*
	 * Called whenever window is resized
	 */
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
		for (int i = 0; i<enemies.length; i++) enemies[i].dispose();
	}
	

	
	/**
	 * Describes button functionality and position
	 */
	public void create() {
		font = new BitmapFont();
		asteroidsShot = 0;
		enemiesKilled = 0;
		score = 0;
		shotsLanded = 0;
		shotsTaken = 0;
		asteroid = new Asteroid(asteroidsShot, levelNum);
		player = new PlayerShip();
		sprite = player.getSprite();
		level = new Level(levelNum, player);
		
	}
	/*
	 * Handles input from keyboard
	 */
	private void handleInput(float deltaTime)
	{
		//TODO implement instructions for touch screen capability
		if (Gdx.app.getType() != Application.ApplicationType.Desktop) //Ensures device is desktop
		{
			return;
		}
		float sprMoveSpeed = 250 * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.R)) {
			dispose();
			create();
		}
		//Movement according to WASD, arrow keys, and space bar
		if ((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) && sprite.getX() > 90) player.getSprite().translate(-sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) && sprite.getX() < Gdx.graphics.getWidth()-100) player.getSprite().translate(sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.DPAD_UP)) && sprite.getY() < 50) player.getSprite().translate(0,sprMoveSpeed);
		if ((Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) && sprite.getY() > 5) player.getSprite().translate(0,-sprMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.SPACE)) shotPressed = true;
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) pause();
		
	}

	/*
	 * Checks if level is complete
	 */
public boolean isComplete()
{
	for (int i = 0; i<enemies.length; i++) {
		if (enemies[i].isAlive()) return false; //If one enemy is alive, returns false
	}
	return true;
}
	}

