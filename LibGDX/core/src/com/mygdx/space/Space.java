package com.mygdx.space;

import java.util.Random;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.gui.MainScreen;

import util.Constants;

public class Space extends Game implements Screen{
	private SpriteBatch batch;
	private BitmapFont font;
	private Game game;
	private Sprite sprite;
	private Level level;
	private int levelNum = 1;
	private Ship[] enemies;
	public static Integer score = 0;
	public static double shotsTaken = 0;
	public static double shotsLanded = 0;
	private PlayerShip player;
	private boolean shotPressed;
	private  Random rand = new Random();	
	private final int LAST_LEVEL = 7;
	private Stage stage;
	public static double accuracy;
	private Asteroid asteroid;
	private TextButton exitButton;
	private TextButton retryButton;
	public static int asteroidsShot;
	private Skin skin;
	public static int enemiesKilled;
	public boolean paused;
	public static boolean gameOver;

	
	
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
		handleInput(delta); //Get input from keyboard
		if (paused) return;
		// TODO Auto-generated method stub
		 Gdx.gl.glClearColor((float)4/255, (float)7/255, (float)40/255, 1); //Color of background
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Don't know why but you need this
	     batch = new SpriteBatch();
	     
	     
	     enemies = level.getShips(); //Array of enemy ships determined by current level
	     
	     batch.begin(); //Start drawing
	     
	     if (Gdx.input.isKeyPressed(Keys.R)) create(); //Reset; for debugging only
	     player.draw(batch);
	     
		 enemies[rand.nextInt(enemies.length)].shoot(batch);
		 
		if (!asteroid.isIntact() && enemiesKilled < 3 && asteroidsShot < 4) { //Handles logic for streak of hitting asteroids
			asteroid = new Asteroid(asteroidsShot, levelNum);
		}
		 
		 if (asteroid.isIntact()) //Move asteroid as long as it is intact
		 {
			 asteroid.draw(batch);
			 asteroid.move(asteroid.getDir(), 0);
			
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
	     
	   if (shotsTaken ==0 || shotsLanded == 0) accuracy = 0;
	    
	    double accToDisplay = accuracy * 100;
	     font.draw(batch, "Score\n" + score + "\nStage: "+ levelNum + "\nAccuracy: " + (int)accToDisplay + "%\n" + Gdx.graphics.getFramesPerSecond() + " FPS", 20, 200); //Draws player info
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
	     
	     if (gameOver)
	     {
	    	 Gdx.input.setCursorCatched(false);
	    	 stage.act();
	    	 stage.draw();
	    	 
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
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
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
		Gdx.input.setCursorCatched(true);
		gameOver = false;
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
		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();
		
		exitButton = new TextButton("Exit", skin, "default");
		retryButton = new TextButton("Retry", skin, "default");
		
		exitButton.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 2*Constants.BUTTON_OFFSET);
		retryButton.setPosition(Gdx.graphics.getWidth() / 2 - 100f, Gdx.graphics.getHeight() / 2 - 3*Constants.BUTTON_OFFSET);
		
		exitButton.setHeight(Constants.BUTTON_HEIGHT);
		retryButton.setHeight(Constants.BUTTON_HEIGHT);
		exitButton.setWidth(Constants.BUTTON_WIDTH);
		retryButton.setWidth(Constants.BUTTON_WIDTH);
		
		
		stage.addActor(exitButton);
		stage.addActor(retryButton);
		Gdx.input.setInputProcessor(stage);
		
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				game.setScreen(new MainScreen(game));
			}
		});
		
		retryButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dispose();
				game.setScreen(new Space(game));
			}
		});

		
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
		if (Gdx.input.isKeyPressed(Keys.ESCAPE) && !paused) pause();
		if (Gdx.input.isKeyPressed(Keys.ESCAPE) && paused) resume();
		
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

