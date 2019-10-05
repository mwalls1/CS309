package com.mygdx.space;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Asteroid {
	/**
	 * Texture to be used for the asteroid's sprite
	 */
	private Texture texture;
	/**
	 * Sprite to be drawn to represent the asteroid
	 */
	private Sprite sprite;
	/**
	 * Type of asteroid; can be small, medium, large, or golden
	 */
	private int type;
	/**
	 * Holds state of asteroid and whether or not it has been destroyed
	 */
	private boolean intact;
	/**
	 * Random number generator for asteroid placement
	 */
	private Random rand;
	
	/**
	 * Holds number of current level; used for score scaling
	 */
	private int levelNum;
	
	/**
	 * Numerical representation for small asteroid
	 */
	public static final int SMALL = 2;
	/**
	 * Numerical representation for medium asteroid
	 */
	public static final int MEDIUM = 1;
	/**
	 * Numerical representation for large asteroid
	 */
	public static final int LARGE = 0;
	/**
	 * Numerical representation for golden asteroid
	 */
	public static final int GOLDEN = 3;
	/*
	 * Direction for movement
	 */
	private boolean directionSelect;
	
	/*
	 * Selects between move left or right
	 */
	private int dir;
	
	/**
	 * Creates an asteroid object
	 * @param type type of asteroid, small, medium, large or golden
	 * @param levelNum level number for score scaling
	 */
public Asteroid(int type, int levelNum)
{
	this.type = type;
	this.levelNum = levelNum;
	//Determine texture of asteroid object from given type int
	if (type == SMALL) texture = new Texture("smallAsteroid.png");
	else if (type == MEDIUM) texture = new Texture("mediumAsteroid.png");
	else if (type == LARGE) texture = new Texture("largeAsteroid.png");
	else if (type == GOLDEN) texture = new Texture("GoldenAsteroid.png");
	
	rand = new Random();
	intact = true;
	sprite = new Sprite(texture);
	sprite.setPosition(0, rand.nextInt(Gdx.graphics.getHeight()/2) + 300); //Sets initial position off-screen
	directionSelect = new Random().nextBoolean();
	
	if (directionSelect)
	{
		dir = 1;
		sprite.setPosition(0, rand.nextInt(Gdx.graphics.getHeight()/2) + 300); 
	}
	
	else if (!directionSelect)
	{
		dir = -1;
		sprite.setPosition(Gdx.graphics.getWidth(), rand.nextInt(Gdx.graphics.getHeight()/2) + 300); 
	}
	
	
}
/**
 * Generates an asteroid of random type, used for debugging
 */
public Asteroid()
{
	rand = new Random();
	type = rand.nextInt(4);
	
	if (type == SMALL) texture = new Texture("smallAsteroid.png");
	else if (type == MEDIUM) texture = new Texture("mediumAsteroid.png");
	else if (type == LARGE) texture = new Texture("largeAsteroid.png");
	else if (type == GOLDEN) texture = new Texture("GoldenAsteroid.png");
	
	intact = true;
	sprite = new Sprite(texture);
	sprite.setPosition(0, rand.nextInt(Gdx.graphics.getHeight()-200));
	
	
}

/**
 * 
 * @return whether the asteroid is intact or not
 */
public boolean isIntact()
{
	return intact;
}
/**
 * Tests if asteroid has collided with a player or player's fire
 * @param coll
 */
public void collision(Sprite coll)
{
	//Compare positions of a given sprite with the asteroid's sprite
	if (Math.abs(sprite.getX() - coll.getX()) < 25 && Math.abs(sprite.getY() - coll.getY()) < 10 && isIntact())
	{
		coll.setPosition(0, 0); //Move shot off-screen for destruction
		Space.shotsLanded++; //Increment for accuracy calculations
		Space.asteroidsShot++; //Increment for accuracy calculations
		destroy();
	}		
}
/**
 * Moves the asteroid sprite with Sprite.translate()
 * @param x translation for x direction
 * @param y translation for y direction
 */
public void move(int x, int y)
{
	sprite.rotate(1);
	sprite.translate(x, -y);
}
/**
 * Draws sprite
 * @param batch given SpriteBatch for drawing sprite
 */
public void draw(SpriteBatch batch)
{
	sprite.draw(batch);
}

/**
 * Returns asteroid's sprite
 * @return asteroid's sprite
 */
public Sprite getSprite()
{
	return sprite;
}

/**
 * Destroys asteroid, called when collision is detected with either player or player fire
 */
public void destroy()
{
	intact = false;
	sprite.setPosition(0, 0);
	//How much is added to score, based on difficulty to shoot (i.e. size) and current level
	if (type == SMALL) Space.score += 60*levelNum;
	if (type == MEDIUM) Space.score += 30*levelNum;
	if (type == LARGE) Space.score += 15*levelNum;
	if (type == GOLDEN) Space.score += 300*levelNum;
	texture.dispose();
	Space.accuracy = Space.shotsLanded/Space.shotsTaken; 
	
}

public int getDir()
{
	return dir;
}
}
