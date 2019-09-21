package com.mygdx.space;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ship{
private Texture texture;
private Sprite sprite;
public static final int PLAYER = 0;
public static final int ENEMY_1 = 1;
	
	public Ship(int shipType)
	{
		if (shipType == PLAYER) texture = new Texture("ship.png");
		else if (shipType == ENEMY_1) texture = new Texture("enemy1.png");
		sprite = new Sprite(texture);
		
		
	}
	
	public Sprite get()
	{
		return sprite;
	}
	
	
	public void testCollisions(Sprite mainSprite, Sprite otherSprite)
	{
			if (Math.abs(otherSprite.getX() - mainSprite.getX()) < 25 && Math.abs(otherSprite.getY() - mainSprite.getY()) < 5) mainSprite.setPosition(0,0);
			
				System.out.println("X distance: " + Math.abs(otherSprite.getX() - mainSprite.getX()));
				System.out.println("Y distance: " + Math.abs(otherSprite.getY() - mainSprite.getY()));
			
		
	}
	
}
