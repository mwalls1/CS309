package com.mygdx.Cards;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Card {
private String suit;
private String rank;
private Texture texture;
private Sprite sprite;
private AssetManager manager;
private Random rand;

public Card(String rank, String suit, AssetManager manager)
{
	this.suit = suit;
	this.rank = rank;
	this.manager = manager;
	
	texture = manager.get("Cards/" + rank + suit + ".png", Texture.class);
	sprite = new Sprite(texture);
}




public Card(AssetManager manager) //Generate a random card
{
	rand = new Random();
	int suitDetermine = rand.nextInt(3);
	int rankDetermine = rand.nextInt(13)+1;
	
	if (suitDetermine == 0) this.suit = "spades";
	else if (suitDetermine == 1) this.suit = "diamonds";
	else if (suitDetermine == 2) this.suit = "clubs";
	else if (suitDetermine == 3) this.suit = "hearts";
	
	if (rankDetermine <= 10) this.rank = "" + rankDetermine;
	else if (rankDetermine == 11) this.rank = "jack";
	else if (rankDetermine == 12) this.rank = "queen";
	else if (rankDetermine == 13) this.rank = "king";
	
	
	texture = manager.get("Cards/" + rank + suit + ".png", Texture.class);
	sprite = new Sprite(texture);
}

public String toString()
{
	return rank + " of " + suit;
}

public boolean equals(Card card)
{
	if (suit == card.getSuit() && rank == card.getRank()) return true;
	else return false;
}

public Sprite getSprite()
{
	return sprite;
}
public void setPosition(float xPos, float yPos)
{
	sprite.setPosition(xPos, yPos);
}
public String getSuit()
{
	return suit;
}

public String getRank()
{
	return rank;
}
public void draw(SpriteBatch batch)
{
	sprite.draw(batch);
}

public Texture getTexture()
{
	return texture;
}


}
