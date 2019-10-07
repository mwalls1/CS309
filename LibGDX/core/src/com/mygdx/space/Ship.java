

package com.mygdx.space;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface Ship{
	
	/*
	 * Returns sprite of ship
	 */
	public Sprite getSprite();
	
	/*
	 * Returns ship's fire
	 */
	public Sprite getShot();
	
	/*
	 * Runs attack behavior for ship
	 */
	public void shoot(SpriteBatch batch);
	
	/*
	 * Check collisions between ship's sprite and a given sprite
	 */
	public void collision(Sprite coll);
	
	/*
	 * Return x position of ship's sprite
	 */
	public float getX();
	
	/*
	 * Return y position of ship's sprite
	 */
	public float getY();
	
	/*
	 * Moves ship according to each individual ship type
	 */
	public void move();
	
	
	/*
	 * Draws the ship's sprite with a given SpriteBatch
	 */
	public void draw(SpriteBatch batch);
	
	
	/*
	 * Stop rendering the ship
	 */
	public void destroy();
	
	
	/*
	 * Returns true is ship not destroyed
	 */
	public boolean isAlive();
	
	
	/*
	 * Returns true if ship has fired 
	 */
	public boolean isShotFired();
	
	/*
	 * Disposes of textures for the ship
	 */
	public void dispose();
	
	/*
	 * Returns type of ship as a string
	 */
	public String getType();
}
