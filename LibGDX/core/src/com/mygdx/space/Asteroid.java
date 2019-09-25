package com.mygdx.space;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Asteroid {
	private Texture texture;
	private Sprite sprite;
	private int type;
	private boolean intact;
	private Random rand;
	
	public static final int SMALL = 2;
	public static final int MEDIUM = 1;
	public static final int LARGE = 0;
	public static final int GOLDEN = 3;
	
public Asteroid(int type)
{
	this.type = type;
	
	if (type == SMALL) texture = new Texture("smallAsteroid.png");
	else if (type == MEDIUM) texture = new Texture("mediumAsteroid.png");
	else if (type == LARGE) texture = new Texture("largeAsteroid.png");
	else if (type == GOLDEN) texture = new Texture("GoldenAsteroid.png");
	rand = new Random();
	intact = true;
	sprite = new Sprite(texture);
	sprite.setPosition(0, rand.nextInt(Gdx.graphics.getHeight()/2) + 300);
	
	
}

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

public boolean isIntact()
{
	return intact;
}

public void collision(Sprite coll)
{
	if (Math.abs(sprite.getX() - coll.getX()) < 25 && Math.abs(sprite.getY() - coll.getY()) < 10 && isIntact())
	{
		coll.setPosition(0, 0);
		Space.shotsLanded++;
		Space.asteroidsShot++;
		destroy();
	}		
}

public void move(int x, int y)
{
	sprite.rotate(1);
	sprite.translate(x, -y);
}

public void draw(SpriteBatch batch)
{
	sprite.draw(batch);
}

public Sprite getSprite()
{
	return sprite;
}

public void destroy()
{
	intact = false;
	sprite.setPosition(0, 0);
	if (type == SMALL) Space.score += 15;
	if (type == MEDIUM) Space.score += 30;
	if (type == LARGE) Space.score += 60;
	if (type == GOLDEN) Space.score += 300;
	texture.dispose();
	
}
}
