package com.mygdx.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;

public class Spike {
	private int spawnX;
	private int spawnY;
	private Texture spike = new Texture(Gdx.files.internal("spike.png"));
	Sprite sprite = new Sprite(spike);
	private boolean active = false;
	public Spike(int x, int y)
	{
		spawnX = x;
		spawnY = y;
		sprite.setX(spawnX);
		sprite.setY(spawnY);
	}
	public void render(Character player, SpriteBatch batch)
	{
		sprite.draw(batch);
		checkCollision(player);
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
				player.setPos(player.getsX(), player.getsY());
		}
		return false;
	}
}
