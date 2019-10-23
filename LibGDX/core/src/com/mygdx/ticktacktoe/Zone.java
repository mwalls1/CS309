package com.mygdx.ticktacktoe;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Zone {
	private Rectangle rect;
	private boolean active;
	private char let;
	private Sprite sprite;
	private Texture x = new Texture(Gdx.files.internal("x.png"));
	private Texture o = new Texture(Gdx.files.internal("o.png"));
	public Zone(int startX, int startY, int width, int height)
	{
		rect = new Rectangle(startX, startY, width, height);
		active = false;
		sprite = new Sprite(x);
		sprite.setX(startX);
		sprite.setY(515-startY);
	}
	public boolean contains(int x, int y)
	{
		return rect.contains(x, y);
	}
	public char getLetter()
	{
		return let;
	}
	public void setLetter(char letter)
	{
		let = letter;
		if(let=='x')
			sprite.setTexture(x);
		else
			sprite.setTexture(o);
		active = true;
	}
	public void render(SpriteBatch batch)
	{
		if(active)
		{
			sprite.draw(batch);
		}
	}
	public boolean isActive()
	{
		return active;
	}
}
