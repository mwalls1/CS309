package com.mygdx.space;

import com.badlogic.gdx.Gdx;
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
	
	public Ram()
	{
		texture = new Texture("enemy2.png");
		damagedTexture = new Texture("enemy2damaged.png");
		
		sprite = new Sprite(texture);
		sprite.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-50);
		alive = true;
		health = 4;
		shotFired = false;
	}

	public Ram(float xPos)
	{
		texture = new Texture("enemy2.png");
		damagedTexture = new Texture("enemy2damaged.png");
		
		sprite = new Sprite(texture);
		sprite.setPosition(xPos, Gdx.graphics.getHeight());
		alive = true;
		health = 4;
		shotFired = false;
	}
	
	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public Sprite getShot() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	public void shoot(SpriteBatch batch) {
		if (!shotFired)
		{
			shotFired = true;
			//shotSprite.setPosition(sprite.getX(), sprite.getY()-30);
		}
		
		
		if (sprite.getY() < 1)
			{
			shotFired = false;
			sprite.setY(Gdx.graphics.getHeight());
			}
		
		if (shotFired) 
			{
			 sprite.translateY(Gdx.graphics.getHeight()/-400);
			 sprite.draw(batch);
			}
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
				texture.dispose();
				sprite = new Sprite(damagedTexture);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	@Override
	public void destroy() {
		alive = false;
		Space.score += 20;
		sprite.setPosition(0, 0);
		sprite.setAlpha(0);
		damagedTexture.dispose();
	}

	@Override
	public boolean isAlive() {
		return alive;
	}

	@Override
	public boolean isShotFired() {
		// TODO Auto-generated method stub
		return shotFired;
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
