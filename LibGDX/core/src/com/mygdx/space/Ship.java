

package com.mygdx.space;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//
//public class Ship{
//private Texture texture;
//private Sprite sprite;
//public static final int PLAYER = 0;
//public static final int ENEMY_1 = 1;
//	
//	public Ship(int shipType)
//	{
//		if (shipType == PLAYER) texture = new Texture("ship.png");
//		else if (shipType == ENEMY_1) texture = new Texture("enemy1.png");
//		sprite = new Sprite(texture);
//		
//	}
//	
//	public Sprite get()
//	{
//		return sprite;
//	}
//	
//	
//	public boolean testCollisions(Sprite mainSprite, Sprite otherSprite)
//	{
//			if (Math.abs(otherSprite.getX() - mainSprite.getX()) < 25 && Math.abs(otherSprite.getY() - mainSprite.getY()) < 10)
//			{
//				return true;
//			}
//			else return false;	
//	}
//	
//}


public interface Ship{
	
	public Sprite getSprite();
	public Sprite getShot();
	public void shoot(SpriteBatch batch);
	public void collision(Sprite coll);
	public float getX();
	public float getY();
	public void move();
	public void draw(SpriteBatch batch);
	public void destroy();
	public boolean isAlive();
	public boolean isShotFired();
	public void dispose();
	public String getType();
}
