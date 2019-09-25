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
private Asteroid[] asteroids;
	
	public Level(int level, Ship ship)
	{
		this.level = level;
		initLevel(level, ship);
	}
	
	
	public void initLevel(int levelNum, Ship ship)
	{
		if (levelNum == 1)
		{
			enemies = new Ship[5];
			for (int i = 0; i<enemies.length; i++) enemies[i] = new Goon(i*200+200, Gdx.graphics.getHeight()-80,5);
		}
		else if (levelNum == 2)
		{
			enemies = new Ship[10];
			for (int i = 0; i<enemies.length; i++) enemies[i] = new Goon(i*100+200, Gdx.graphics.getHeight()-80,5);
		
		}
		else if (levelNum == 3)
		{
			enemies = new Ship[15];
			for (int i = 0; i<enemies.length; i++) enemies[i] = new Goon(i*100+200, Gdx.graphics.getHeight()-80,5);
			for (int j = 10; j<15; j++) enemies[j] = new Ram((j-10)*200+200);
		}
		else if (levelNum == 4)
		{
			enemies = new Ship[20];
			for (int i = 0; i<10; i++) enemies[i] = new Goon(i*100+200, Gdx.graphics.getHeight()-80,5);
			for (int j = 10; j<20; j++) enemies[j] = new Ram((j-10)*100+200);
		}
		else if (levelNum == 5)
		{
			enemies = new Ship[34];
			for (int i = 0; i<10; i++) enemies[i] = new Goon(i*100+200, Gdx.graphics.getHeight()-80,8);
			for (int j = 10; j<19; j++) enemies[j] = new Goon((j-10)*100+250, Gdx.graphics.getHeight()-130,8);
			for (int k = 19; k<34; k++) enemies[k] = new Ram((k-20)*100+200);
		}
		else if (levelNum == 6)
		{
			enemies = new Ship[25];
			for (int i = 0; i<10; i++) enemies[i] = new Goon(i*100+200, Gdx.graphics.getHeight()-100,9);
			for (int j = 10; j<19; j++) enemies[j] = new Goon((j-10)*100+250, Gdx.graphics.getHeight()-150,9);
			for (int k = 19; k<24; k++) enemies[k] = new Ram((k-20)*100+200);
			enemies[24] = new Captain(ship.getX(), Gdx.graphics.getHeight()-50);
		}
	}
	
	
	
	public Ship[] getShips()
	{
		return enemies;
	}
	
	
	
}
