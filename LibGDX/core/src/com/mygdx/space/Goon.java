package com.mygdx.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Goon implements Ship{
/**
 * Sprite of ship
 */
	private Sprite sprite;
	/**
	 * Texture to create ship sprite
	 */
	private Texture texture;
	/**
	 * Whether the ship has been shot down or not
	 */
	private boolean alive;
	/**
	 * X position that ship starts at, for movement relative to this position
	 */
	private float xOrigin;
	/**
	 *  Direction for the ship to move in
	 */
	private float dir = 1;
	/**
	 * Whether the ship has fired or not
	 */
	private boolean shotFired;
	/**
	 * Sprite of ship's fire
	 */
	private Sprite shotSprite;
	/**
	 * Texture of ship's fire for creating sprite
	 */
	private Texture shotTexture;
	/**
	 * How quickly ship shots travel towards player ship
	 */
	private int shotSpeed = 5;
	
	/**
	 * Creates a new ship at a default position with a default speed, used for debugging
	 */
	public Goon()
	{
		texture = new Texture("enemy1.png");
		shotTexture = new Texture("shot.png");
		sprite = new Sprite(texture);
		sprite.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 100);
		sprite.setScale(100);
		alive = true;
		xOrigin = sprite.getX();
		shotSprite = new Sprite(shotTexture);
		
	}
	/**
	 * Creates new ship
	 * @param xPos initial x position of ship
	 * @param yPos initial y position of ship
	 * @param shotSpeed how quickly ship's fire travels
	 */
	public Goon(float xPos, float yPos, int shotSpeed)
	{
		texture = new Texture("enemy1.png");
		shotTexture = new Texture("shot.png");
		sprite = new Sprite(texture);
		sprite.setPosition(xPos, yPos);
		alive = true;
		xOrigin = sprite.getX();
		shotSprite = new Sprite(shotTexture);
		this.shotSpeed = shotSpeed * -1;
	}
/**
 * Moves ship back and forth relative to ship's initial x position
 */
	public void move() {
		if (sprite.getX() > xOrigin + 50 || sprite.getX() > Gdx.graphics.getWidth()-100) dir = -1; //Reached bound of this side, reverse direction
		else if (sprite.getX() < xOrigin - 50 || sprite.getX() < 90) dir = 1; //Reached bound of this side, reverse direction
		
		sprite.translateX(dir*5);
		
		
		
		
		
		if (sprite.getY() < 0 && alive) sprite.setY(Gdx.graphics.getHeight());
	}

	@Override
	/**
	 * Returns ship's sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	/**
	 * Causes ship to fire or continues movement of previously fired shot
	 */
	public void shoot(SpriteBatch batch) {
		if (!shotFired) //If ship has not fired yet, set initial position for shot sprite
		{
			shotFired = true;
			shotSprite.setPosition(sprite.getX(), sprite.getY()-30);
		}
		
		
		if (shotSprite.getY() < 1) { //If shot is off-screen, destroy it
			shotFired = false;
			
		}
		if (shotSprite.getX() < 1) {// If shot is off-screen, destroy it
			shotFired = false;
			
		}
		
		if (shotFired) //If shot has already been fired, move shot downward towards player
			{
			 shotSprite.translateY(shotSpeed);
			 shotSprite.draw(batch);
			}
		
		
		
	}

	@Override
	/**
	 * Tests if ship has collided with a given sprite
	 */
	public void collision(Sprite coll) {
		if (Math.abs(sprite.getX() - coll.getX()) < 25 && Math.abs(sprite.getY() - coll.getY()) < 10 && isAlive()) //If given sprite is within a tolerance of ship's position
		{
			coll.setPosition(0, 0); //Move given sprite off-screen for deletion
			Space.shotsLanded++; //Increase for accuracy calculation
			destroy();
		}		
	}

	@Override
	/**
	 * Returns ship's x position
	 */
	public float getX() {
		// TODO Auto-generated method stub
		return sprite.getX();
	}

	@Override
	/**
	 * Returns ship's y position
	 */
	public float getY() {
		// TODO Auto-generated method stub
		return sprite.getY();
	}
	/**
	 * Draws ship sprite with a given batch
	 */
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	/**
	 * Destroys ship, prevents it from being rendered and increases score
	 */
	public void destroy()
	{
		alive = false;
		sprite.setPosition(0, 0); //Move off screen for deletion
		Space.score += 10;
		Space.enemiesKilled++;
		texture.dispose(); //Free memory held by texture for destroyed ship
		Space.accuracy = Space.shotsLanded/Space.shotsTaken; 
	}
	/**
	 * Returns whether or not ship has been shot down
	 */
	public boolean isAlive()
	{
		return alive;
	}
	
	/**
	 * Returns whether or not ship has fired 
	 */
	public boolean isShotFired()
	{
		return shotFired;
	}
	/**
	 * Returns sprite of ship's fire
	 */
	public Sprite getShot()
	{
		return shotSprite;
	}
	/**
	 * Free memory held by ship fire's texture
	 */
	public void dispose()
	{
		shotTexture.dispose();
	}
	/**
	 * Return type of ship as a string
	 */
	public String getType()
	{
		return "Goon";
	}
}
