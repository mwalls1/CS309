package com.mygdx.Cards;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Deck {
private Card[] cards;
private AssetManager manager;

public Deck(int seed, AssetManager manager) //Creates a deck with 52 unique cards
{
	this.manager = manager;
	loadAllAssets();
	cards = new Deck("organized", manager).getCards();
	shuffle(seed);
}

public Deck(String arg, AssetManager manager)
{
	cards = new Card[52];
	if (arg == "organized")
	{
		for (int i = 0; i<10; i++) cards[i] = new Card("" + (i+1), "spades", manager);
		cards[10] = new Card("jack", "spades", manager);
		cards[11] = new Card("queen", "spades", manager);
		cards[12] = new Card("king", "spades", manager);
		for (int i = 13; i<23; i++) cards[i] = new Card("" + (i-12), "clubs", manager);
		cards[23] = new Card("jack", "clubs", manager);
		cards[24] = new Card("queen", "clubs", manager);
		cards[25] = new Card("king", "clubs", manager);
		for (int i = 26; i<36; i++) cards[i] = new Card("" + (i-25), "diamonds", manager);
		cards[36] = new Card("jack", "diamonds", manager);
		cards[37] = new Card("queen", "diamonds", manager);
		cards[38] = new Card("king", "diamonds", manager);
		for (int i = 39; i<49; i++) cards[i] = new Card("" + (i-38), "hearts", manager);
		cards[49] = new Card("jack", "hearts", manager);
		cards[50] = new Card("queen", "hearts", manager);
		cards[51] = new Card("king", "hearts", manager);
	
	}
	
}

public Card getCard(int index)
{
	return cards[index];
}

public boolean cardInDeck(Card card)
{
	for (int i = 0; i<52; i++)
	{
		if (cards[i].equals(card)) return true;
	}
	return false;
}

public Card[] getCards()
{
	return cards;
}

public void shuffle(int seed){
	Random rand = new Random();  		
	rand.setSeed(seed);
	for (int i=0; i<cards.length; i++) {
	    int randomPosition = rand.nextInt(cards.length);
	    Card temp = cards[i];
	    cards[i] = cards[randomPosition];
	    cards[randomPosition] = temp;
	}



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



public void printDeck()
{
	for (int i = 0; i<cards.length; i++)
	{
		System.out.println(cards[i].getRank() + " of " + cards[i].getSuit());
	}
}

}
