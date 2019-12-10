package com.mygdx.space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Level {
	private Ship[] enemies;
	private AssetManager manager;

	/**
	 * Creates new level
	 * 
	 * @param level what level should be generated
	 * @param ship  Player ship
	 */
	public Level(int level, Ship ship, AssetManager manager) {
		this.manager = manager;
		initLevel(level, ship);
	}

	/**
	 * Pre-determined ship arrays for each level
	 * 
	 * @param levelNum level to be generated
	 * @param ship     player ship
	 */
	public void initLevel(int levelNum, Ship ship) {
		if (levelNum == 0) // Debug level
		{
			enemies = new Ship[10];
			for (int k = 0; k < 10; k++)
				enemies[k] = new Ram((k) * 100 + 200, manager);

		}
		if (levelNum == 1) {
			enemies = new Ship[5];
			for (int i = 0; i < enemies.length; i++)
				enemies[i] = new Goon(i * 200 + 200, Gdx.graphics.getHeight() - 80, 5, manager);
		} else if (levelNum == 2) {
			enemies = new Ship[10];
			for (int i = 0; i < enemies.length; i++)
				enemies[i] = new Goon(i * 100 + 200, Gdx.graphics.getHeight() - 80, 5, manager);

		} else if (levelNum == 3) {
			enemies = new Ship[15];
			for (int i = 0; i < enemies.length; i++)
				enemies[i] = new Goon(i * 100 + 200, Gdx.graphics.getHeight() - 80, 5, manager);
			for (int j = 10; j < 15; j++)
				enemies[j] = new Ram((j - 10) * 200 + 200, manager);
		} else if (levelNum == 4) {
			enemies = new Ship[20];
			for (int i = 0; i < 10; i++)
				enemies[i] = new Goon(i * 100 + 200, Gdx.graphics.getHeight() - 80, 5, manager);
			for (int j = 10; j < 20; j++)
				enemies[j] = new Ram((j - 10) * 100 + 200, manager);
		} else if (levelNum == 5) {
			enemies = new Ship[29];
			for (int i = 0; i < 10; i++)
				enemies[i] = new Goon(i * 100 + 200, Gdx.graphics.getHeight() - 80, 5, manager);
			for (int j = 10; j < 19; j++)
				enemies[j] = new Goon((j - 10) * 100 + 250, Gdx.graphics.getHeight() - 130, 5, manager);
			for (int k = 19; k < 29; k++)
				enemies[k] = new Ram((k - 20) * 100 + 200, manager);
		} else if (levelNum == 6) {
			enemies = new Ship[21];
			for (int i = 0; i < 10; i++)
				enemies[i] = new Goon(i * 100 + 200, Gdx.graphics.getHeight() - 120, 8, manager);
			for (int k = 10; k < 20; k++)
				enemies[k] = new Ram((k - 10) * 100 + 200, manager);
			enemies[20] = new Captain(ship.getX(), (float) Gdx.graphics.getHeight() - 60, manager);
		}

	}

	/*
	 * Returns array of enemy ships
	 */
	public Ship[] getShips() {
		return enemies;
	}

}
