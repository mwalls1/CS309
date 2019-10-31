package com.mygdx.ticktacktoe;

import java.awt.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Zone {
	private Rectangle rect;
	private boolean active;
	private String let;
	private Sprite sprite;
	private Texture x = new Texture(Gdx.files.internal("x.png"));
	private Texture o = new Texture(Gdx.files.internal("o.png"));
	public Zone(int startX, int startY, int width, int height)//The area that a player can make a move on
	{
		rect = new Rectangle(startX, startY, width, height);
		active = false;
		sprite = new Sprite(x);
		sprite.setX(startX);
		sprite.setY(515-startY);
	}
	public boolean contains(int x, int y)//returns whether the coordinates are located in the zone
	{
		return rect.contains(x, y);
	}
	public String getLetter()//return what letter is given in the zone
	{
		return let;
	}
	public void setLetter(String letter)//sets the letter and determines what texture should be applied
	{
		let = letter;
		if(let.equals("x"))
			sprite.setTexture(x);
		else
			sprite.setTexture(o);
		active = true;
	}
	public void render(SpriteBatch batch)//renders the zone to the screen
	{
		if(active)
		{
			sprite.draw(batch);
		}
	}
	public boolean isActive()//returns whether the zone has a letter or not
	{
		return active;
	}
}
