package com.mygdx.space;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
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
	private Sprite enemy;
	private Sprite shotSprite;
	private boolean shotFired = false;
	private boolean secondShotFired = false;
	private Sprite secondShotSprite;
	private int frameCounter = 0;
	private Ship playerShip;
	
	
	
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
		frameCounter++;
		// TODO Auto-generated method stub
		 Gdx.gl.glClearColor((float)4/255, (float)7/255, (float)40/255, 1); //Color of background
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Don't know why but you need this
	     handleInput(delta);
	     batch.begin();
			//batch.setColor(1, 0, 0, 1);
			//batch.draw(texture, 10, 10);
			//batch.setColor(0, 1, 0, 1);
	     
	   
			sprite.draw(batch);
			enemy.draw(batch);
			if (shotFired) {
				shotSprite.draw(batch);
				shotSprite.translate(0, 10);
			}
			if(secondShotFired)
			{
				secondShotSprite.draw(batch);
				secondShotSprite.translate(0, 10);
			}
			
			if (shotSprite.getY() > Gdx.graphics.getHeight()) shotFired = false;
			if (secondShotSprite.getY() > Gdx.graphics.getHeight()) secondShotFired = false;
			batch.end();
			
			if (frameCounter == 60) frameCounter = 0;
			enemy.translate(0, -1);
			playerShip.testCollisions(sprite, enemy);
			playerShip.testCollisions(enemy, shotSprite);
			
		
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
		
//		texture = new Texture(Gdx.files.internal("ship.png"));
		
		playerShip = new Ship(Ship.PLAYER);
		Ship enemyShip = new Ship(Ship.ENEMY_1);
		batch = new SpriteBatch();
		sprite = playerShip.get();	
		enemy = enemyShip.get();
		enemy.setPosition(Gdx.graphics.getWidth()/2, 300);
		
		Texture lineTexture = new Texture("shot.png");
		shotSprite = new Sprite(lineTexture);
		secondShotSprite = new Sprite(lineTexture);
		
		sprite.setPosition(Gdx.graphics.getWidth()/2, 5);
		
	}
	
	private void handleInput(float deltaTime)
	{

		if (Gdx.app.getType() != Application.ApplicationType.Desktop)
		{
			return;
		}
		float sprMoveSpeed = 500 * deltaTime;
		if ((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) && sprite.getX() > 90) sprite.translate(-sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) && sprite.getX() < Gdx.graphics.getWidth()-100) sprite.translate(sprMoveSpeed, 0);
		if ((Gdx.input.isKeyPressed(Keys.W) || Gdx.input.isKeyPressed(Keys.DPAD_UP)) && sprite.getY() < 50) sprite.translateY(sprMoveSpeed);
		if ((Gdx.input.isKeyPressed(Keys.S) || Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) && sprite.getY() > 5) sprite.translateY(-sprMoveSpeed);
		if ((Gdx.input.isKeyPressed(Keys.SPACE) && !shotFired)) {
			shotFired = true;
			shotSprite.setPosition(sprite.getX(), sprite.getY()+30);
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE) && (shotFired) && (!secondShotFired) && shotSprite.getY() > 100)
		{
			secondShotFired = true;
			secondShotSprite.setPosition(sprite.getX(), sprite.getY()+30);
		}
	}

}
