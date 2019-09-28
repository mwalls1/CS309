package com.mygdx.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Captain implements Ship {
/**
 * Holds texture for captain ship sprite
 */
private Texture texture;
/**
 * Holds texture for captain ship's fire sprite
 */
private Texture shotTexture;
/**
 * Holds sprite for captain ship
 */
private Sprite sprite;
/**
 * holds sprite for captain ship's fire
 */
private Sprite shotSprite;
/**
 * Whether captain ship has fired or not
 */
private boolean shotFired;
/**
 * Whether the captain ship should be drawn
 */
private boolean alive;
/**
 * How many hits captain ship can take before destroyed
 */
private int health;

/**
 * Creates a new Captain ship at a given coordinate
 * @param xPos x position for new ship
 * @param yPos y position for new ship
 */
	public Captain(float xPos, float yPos)
	{
		texture = new Texture("enemy3.png"); //Texture for ship
		sprite = new Sprite(texture);
		alive = true;
		sprite.setPosition(xPos, yPos);
		sprite.rotate(180); //Sprite was drawn facing the wrong way because I'm dumb, so I rotated it
		shotTexture = new Texture("shot.png"); //Texture for ship's fire
		shotSprite = new Sprite(shotTexture);
		health = 2;
		
	}
	
	@Override
	/**
	 * Returns sprite of ship
	 */
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	/**
	 * Returns sprite of ship's fire
	 */
	public Sprite getShot() {
		return shotSprite;
	}

	@Override
	/**
	 * Fire shot at player
	 */
	public void shoot(SpriteBatch batch) {
		if (!shotFired) //If shot has not yet been fired, set position for a new shot
		{
			shotFired = true;
			shotSprite.setPosition(sprite.getX(), sprite.getY()-30);
		}
		
		
		if (shotSprite.getY() < -50) shotFired = false; //If shot goes beyond view port, destroy it
		
		if (shotFired) //If shot has already been fired, move shot further down view port
			{
			 shotSprite.translateY(-8);
			 shotSprite.draw(batch);
			}
		
	}

	@Override
	/**
	 * Tests if a given sprite has collided with ship
	 */
	public void collision(Sprite coll) {
		if (Math.abs(sprite.getX() - coll.getX()) < 25 && Math.abs(sprite.getY() - coll.getY()) < 10 && isAlive())
		{
			health--;
			if (health == 0)
			{
			coll.setPosition(0, 0);
			Space.shotsLanded++;
			destroy();
			}
		}	
	}

	@Override
	/**
	 * Returns x position of ship sprite
	 */
	public float getX() {
		return sprite.getX();
	}

	@Override
	/**
	 * Return y position of ship sprite
	 */
	public float getY() {
		return sprite.getY();
	}

	@Override
	/**
	 * Moves ship to mirror player movement in x direction
	 */
	public void move() {
		float sprMoveSpeed = 250 * Gdx.graphics.getDeltaTime();
		
		if ((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) && sprite.getX() > 90) sprite.translate(-sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) && sprite.getX() < Gdx.graphics.getWidth()-100) sprite.translate(sprMoveSpeed,0);
	}

	@Override
	/**
	 * Draws sprite with a given SpriteBatch
	 */
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	/**
	 * Destroys ship, preventing it from being rendered
	 */
	public void destroy() {
		alive = false;
		sprite.setPosition(0, 0);
		Space.score += 50;
		texture.dispose();
	}

	@Override
	/**
	 * Returns whether the ship has been shot down or not
	 */
	public boolean isAlive() {
		return alive;
	}

	@Override
	/**
	 * Returns whether or not ship has fired
	 */
	public boolean isShotFired() {
		return shotFired;
	}
	/**
	 * Disposes of texture, called once ship is no longer used
	 */
	public void dispose()
	{
		shotTexture.dispose();
	}
	/**
	 * Returns type of ship as a string
	 */
	public String getType()
	{
		return "Captain";
	}

}
