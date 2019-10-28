package com.mygdx.space;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ram implements Ship{
	private Texture texture;
	private Texture damagedTexture;
	private Sprite sprite;
	private boolean alive;
	private int health;
	private boolean shotFired;
	private Random rand;
	private float speed;
	/*
	 * Creates new ship at default position; used for debugging
	 */
	public Ram(AssetManager manager)
	{
		rand = new Random();
		texture = manager.get("enemy2.png");
		damagedTexture = manager.get("enemy2damaged.png");
		
		sprite = new Sprite(texture);
		sprite.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-50);
		alive = true;
		health = 4;
		speed = rand.nextInt();
	}
	/*
	 * Creates new ship at a given x position
	 */
	public Ram(float xPos, AssetManager manager)
	{
		rand = new Random();
		texture = manager.get("assets/enemy2.png");
		damagedTexture = manager.get("assets/enemy2damaged.png");
		
		sprite = new Sprite(texture);
		sprite.setPosition(xPos, Gdx.graphics.getHeight());
		alive = true;
		health = 4;
		shotFired = false;
		speed = rand.nextFloat()+2;
	}
	
	@Override
	/*
	 * Returns ship sprite
	 */
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	/*
	 * Returns sprite instead of shot; this enemy type does not fire
	 */
	public Sprite getShot() {
		return sprite;
	}

	@Override
	/*
	 * Instead of firing shots, this ship charges downward
	 */
	public void shoot(SpriteBatch batch) {
//		if (!shotFired)
//		{
//			shotFired = true;
//			//shotSprite.setPosition(sprite.getX(), sprite.getY()-30);
//		}
//		
//		
//		if (sprite.getY() < 1)
//			{
//			shotFired = false;
//			sprite.setY(Gdx.graphics.getHeight());
//			}
//		
//		if (shotFired) 
//			{
//			 sprite.translateY(-5);
//			 sprite.draw(batch);
//			}
	}

	@Override
	public void collision(Sprite coll) {
		if (Math.abs(sprite.getX() - coll.getX()) < 25 && Math.abs(sprite.getY() - coll.getY()) < 10 && isAlive())
		{
			coll.setPosition(0, 0);
			Space.shotsLanded++;
			health--;
			if (health == 2) {
				float x = sprite.getX();
				float y = sprite.getY();
				sprite.setTexture(damagedTexture);
				sprite.setPosition(x, y);
			}
			else if(health == 0) destroy();
		}				
	}

	@Override
	public float getX() {
		return sprite.getX();
	}

	@Override
	public float getY() {
		return sprite.getX();
	}

	@Override
	public void move() {
		sprite.translateY(-1*speed);
		System.out.println(speed);
		
		if (sprite.getY() < 1)
			{
			sprite.setY(Gdx.graphics.getHeight());
			}
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void destroy() {
		alive = false;
		Space.score += 20;
		Space.enemiesKilled++;
		sprite.setPosition(0, 0);
		sprite.setAlpha(0);
		Space.accuracy = Space.shotsLanded/Space.shotsTaken; 
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public boolean isShotFired() {
		// TODO Auto-generated method stub
		return true;
	}

	public void dispose()
	{
		//Wow, look! Nothing!
	}
	
	public String getType()
	{
		return "Ram";
	}
}
