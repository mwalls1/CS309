package com.mygdx.gui;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Asteroid {
private Texture texture;
private Sprite sprite;
public static final int SMALL = 0;
public static final int MEDIUM = 1;
public static final int LARGE = 2;
public static final int GOLDEN = 3;
private boolean intact;
private Random rand;


	public Asteroid(int type)
	{
		if (type == SMALL)
		{
			
		}
		
		else if(type == MEDIUM)
		{
			
		}
		else if (type == LARGE)
		{
			
		}
		else if (type == GOLDEN)
		{
			texture = new Texture("goldenAsteroid.png");
		}
	}
	public Asteroid()
	{
		rand = new Random();
		int num = rand.nextInt(100);
		if (num < 50) //Small Asteroid
		{
			
		}
		else if (num < 70) //Medium Asteroid
		{
			
		}
		else if (num < 95) //Large Asteroid
		{
			
		}
		else //Golden Asteroid
		{
			texture = new Texture("goldenAsteroid.png");
		}
		
		sprite = new Sprite(texture);
		
	}
	
	
}
