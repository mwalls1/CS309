package com.mygdx.space;


import com.badlogic.gdx.Gdx;
public class Level {
private Ship[] enemies;
	/**
	 * Creates new level
	 * @param level what level should be generated
	 * @param ship Player ship
	 */
	public Level(int level, Ship ship)
	{
		initLevel(level, ship);
	}
	
	/**
	 * Pre-determined ship arrays for each level
	 * @param levelNum level to be generated
	 * @param ship player ship
	 */
	public void initLevel(int levelNum, Ship ship)
	{
		if (levelNum == 0) //Debug level
		{
			enemies = new Ship[1];
			enemies[0] = new Captain(ship.getX(), Gdx.graphics.getHeight()-50);
		}
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
			enemies = new Ship[29];
			for (int i = 0; i<10; i++) enemies[i] = new Goon(i*100+200, Gdx.graphics.getHeight()-80,5);
			for (int j = 10; j<19; j++) enemies[j] = new Goon((j-10)*100+250, Gdx.graphics.getHeight()-130,5);
			for (int k = 19; k<29; k++) enemies[k] =  new Ram((k-20)*100+200);
		}
		else if (levelNum == 6)
		{
			enemies = new Ship[21];
			for (int i = 0; i<10; i++) enemies[i] = new Goon(i*100+200, Gdx.graphics.getHeight()-80,8);
			for (int k = 10; k<20; k++) enemies[k] = new Ram((k-10)*100+200);
			enemies[20] = new Captain(ship.getX(), Gdx.graphics.getHeight()-50);
		}
		
	}
	
	
	
	/*
	 * Returns array of enemy ships
	 */
	public Ship[] getShips()
	{
		return enemies;
	}
	
	
	
}
