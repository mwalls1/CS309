package com.mygdx.space;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Random;
public class Level {
private int level;
private Ship[] enemies;
private Sprite[] sprites;
private boolean[] alive;
private Texture texture;
private int dir;
private double score;
private boolean[] aliveArray;
	
	public Level(int level)
	{
		this.score = score;
		this.level = level;
		initLevel(level);
	}
	
	
	public void initLevel(int levelNum)
	{
		if (levelNum == 1)
		{
			enemies = new Ship[10];
			for (int i = 0; i < enemies.length; i++)
			{
				enemies[i] = new Goon(100*i+150);
			}
			
		}
		
	}
	
	
	
	public Ship[] getShips()
	{
		return enemies;
	}
	
	
	
}
