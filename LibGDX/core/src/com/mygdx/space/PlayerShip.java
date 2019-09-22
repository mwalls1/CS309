package com.mygdx.space;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class PlayerShip implements Ship{

private Texture texture;
private Sprite sprite;
public boolean shotOneFired;
public boolean shotTwoFired;
private Sprite shotOne;
private Sprite shotTwo;
private boolean alive;

	public PlayerShip()
	{
		alive = true;
		texture = new Texture("ship.png");
		sprite = new Sprite(texture);
		sprite.setPosition(Gdx.graphics.getWidth()/2, 5);
		shotOneFired = false;
		shotTwoFired = false;
		Texture shotTexture = new Texture("shot.png");
		shotOne = new Sprite(shotTexture);
		shotTwo = new Sprite(shotTexture);
	}
	public PlayerShip(float xPos)
	{
		alive = true;
		texture = new Texture("ship.png");
		sprite = new Sprite(texture);
		sprite.setPosition(xPos, 5);
		shotOneFired = false;
		shotTwoFired = false;
		Texture shotTexture = new Texture("shot.png");
		shotOne = new Sprite(shotTexture);
		shotTwo = new Sprite(shotTexture);
	}
	
	public void move(float x, float y) {
		sprite.translate(x, y);
	}

	@Override
	public Sprite getSprite() {
		return sprite;
	}

	@Override
	public void shoot(SpriteBatch batch) {
		if (!shotOneFired && Gdx.input.isKeyPressed(Keys.SPACE))
		{
			shotOneFired = true;
			shotOne.setPosition(sprite.getX(), sprite.getY()+30);
		}
		else if (shotOneFired && !shotTwoFired && Gdx.input.isKeyPressed(Keys.SPACE) && shotOne.getY() > sprite.getY()+200)
		{
			shotTwoFired = true;
			shotTwo.setPosition(sprite.getX(), sprite.getY()+30);
		}
		
		if (shotOne.getY() > Gdx.graphics.getHeight()) shotOneFired = false;
		if (shotTwo.getY() > Gdx.graphics.getHeight()) shotTwoFired = false;
		if (shotOne.getX() < 1) shotOneFired = false;
		if (shotTwo.getX() < 1) shotTwoFired = false;
		
		if (shotOneFired) 
			{
			 shotOne.translateY(Gdx.graphics.getHeight()/75);
			 shotOne.draw(batch);
			}
		if (shotTwoFired) 
			{
			shotTwo.translateY(Gdx.graphics.getHeight()/75);
			shotTwo.draw(batch);
			}
		
		
		
		
	}

	@Override
	public void collision(Sprite coll) {
		if (Math.abs(sprite.getX() - coll.getX()) < 25 && Math.abs(sprite.getY() - coll.getY()) < 10)
		{
			destroy();
		}
	}
	
	public void shotLanded(boolean whichShot)
	{
		if (!whichShot) shotOneFired = false;
		else if (whichShot) shotTwoFired = false;
		
	}
	
	public void draw(SpriteBatch batch)
	{
		sprite.draw(batch);
	}
	
	public float getX()
	{
		return sprite.getX();
	}
	
	public float getY()
	{
		return sprite.getY();
	}
	
	public void fireMove(int shotNum)
	{
		if (shotNum == 1) shotOne.translate(0, 5);
		if (shotNum == 2) shotTwo.translate(0, 5);
	}
	
	public boolean getShotOne()
	{
		return shotOneFired;
	}
	
	public boolean getShotTwo()
	{
		return shotTwoFired;
	}
	
	public void destroy()
	{
		alive = false;
		sprite.setAlpha(0);
	}
	
	public boolean isAlive()
	{
		return alive;
	}
	
	public Sprite getShotOneSprite()
	{
		return shotOne;
	}
	
	public Sprite getShotTwoSprite()
	{
		return shotTwo;
	}
	
}
