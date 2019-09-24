package com.mygdx.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Captain implements Ship {
private Texture texture;
private Texture shotTexture;
private Sprite sprite;
private Sprite shotSprite;
private boolean shotFired;
private boolean alive;
private boolean inPosition;
private int health;
int step;
	
	public Captain(float xPos, float yPos)
	{
		texture = new Texture("enemy3.png");
		sprite = new Sprite(texture);
		alive = true;
		sprite.setPosition(xPos, yPos);
		sprite.rotate(180);
		shotTexture = new Texture("shot.png");
		shotSprite = new Sprite(shotTexture);
		health = 2;
		
	}
	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return sprite;
	}

	@Override
	public Sprite getShot() {
		// TODO Auto-generated method stub
		return shotSprite;
	}

	@Override
	public void shoot(SpriteBatch batch) {
		if (!shotFired)
		{
			shotFired = true;
			shotSprite.setPosition(sprite.getX(), sprite.getY()-30);
		}
		
		
		if (shotSprite.getY() < 1) shotFired = false;
		
		if (shotFired) 
			{
			 shotSprite.translateY(Gdx.graphics.getHeight()/-100);
			 shotSprite.draw(batch);
			}
		
	}

	@Override
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
	public float getX() {
		// TODO Auto-generated method stub
		return sprite.getX();
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return sprite.getY();
	}

	@Override
	public void move() {
float sprMoveSpeed = 250 * Gdx.graphics.getDeltaTime();
		
		if ((Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) && sprite.getX() > 90) sprite.translate(-sprMoveSpeed,0);
		if ((Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) && sprite.getX() < Gdx.graphics.getWidth()-100) sprite.translate(sprMoveSpeed,0);
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
		
	}

	@Override
	public void destroy() {
		alive = false;
		sprite.setPosition(0, 0);
		Space.score += 50;
		texture.dispose();
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return alive;
	}

	@Override
	public boolean isShotFired() {
		// TODO Auto-generated method stub
		return shotFired;
	}
	
	public void dispose()
	{
		shotTexture.dispose();
	}
	
	public String getType()
	{
		return "Captain";
	}

}
