package com.mygdx.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;

public class Checkpoint {
	private int spawnX;
	private int spawnY;
	private Texture chestClosed = new Texture(Gdx.files.internal("chestClosed.png"));
	private Texture chestOpen = new Texture(Gdx.files.internal("chestOpen.png"));
	Sprite sprite = new Sprite(chestClosed);
	private boolean active = false;
	public Checkpoint(int x, int y)
	{
		spawnX = x;
		spawnY = y;
		sprite.setX(spawnX);
		sprite.setY(spawnY);
	}
	public void render(Character player, SpriteBatch batch)
	{
		sprite.draw(batch);
		
	}
	public int getX()
	{
		return spawnX;
	}
	public int getY()
	{
		return spawnY;
	}
	public boolean checkCollision(Character player)
	{
		if(Intersector.overlaps(player.getSprite().getBoundingRectangle(), sprite.getBoundingRectangle()))
		{
			if(!active)
			{
				player.setSpawn(spawnX, spawnY);
				sprite.setTexture(chestOpen);
				active = !active;
				return true;
			}
		}
		return false;
	}
}
