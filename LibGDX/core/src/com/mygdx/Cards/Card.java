package com.mygdx.Cards;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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

public Card(String rank, String suit)
{
	this.suit = suit;
	this.rank = rank;
}

public Card() //Generate a random card
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

public String getSuit()
{
	return suit;
}

public String getRank()
{
	return rank;
}

public void loadAllAssets()
{
	
	//Load numbered cards
	for (int i = 1; i<11; i++)
	{
		manager.load("Cards/" + i + "spades.png", Texture.class);
		manager.load("Cards/" + i + "diamonds.png", Texture.class);
		manager.load("Cards/" + i + "clubs.png", Texture.class);
		manager.load("Cards/" + i + "hearts.png", Texture.class);
	}
	//Load joker cards
	for (int i = 1; i<6; i++) manager.load("Cards/" + i + "joker.png", Texture.class);
	
	//Load card backs
	for (int i = 1; i<5; i++) manager.load("Cards/" + i+ "back.png", Texture.class);
	
	String[] suitNames = new String[4];
	suitNames[0] = "spades";
	suitNames[1] = "diamonds";
	suitNames[2] = "clubs";
	suitNames[3] = "hearts";
	
	for (int i = 0; i<4; i++)
	{
		manager.load("Cards/jack" + suitNames[i] + ".png", Texture.class);
		manager.load("Cards/queen" + suitNames[i] + ".png", Texture.class);
		manager.load("Cards/king" + suitNames[i] + ".png", Texture.class);
	}
	manager.finishLoading();
}
}
