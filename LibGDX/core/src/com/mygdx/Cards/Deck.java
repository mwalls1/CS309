package com.mygdx.Cards;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;

public class Deck {
private Card[] cards;
private AssetManager manager;

public Deck() //Creates a deck with 52 unique cards
{
	cards = new Deck("organized").getCards();
	shuffle();
}

public Deck(String arg)
{
	cards = new Card[52];
	if (arg == "organized")
	{
		for (int i = 0; i<10; i++) cards[i] = new Card("" + (i+1), "spades");
		cards[10] = new Card("jack", "spades");
		cards[11] = new Card("queen", "spades");
		cards[12] = new Card("king", "spades");
		for (int i = 13; i<23; i++) cards[i] = new Card("" + (i-12), "clubs");
		cards[23] = new Card("jack", "clubs");
		cards[24] = new Card("queen", "clubs");
		cards[25] = new Card("king", "clubs");
		for (int i = 26; i<36; i++) cards[i] = new Card("" + (i-25), "diamonds");
		cards[36] = new Card("jack", "diamonds");
		cards[37] = new Card("queen", "diamonds");
		cards[38] = new Card("king", "diamonds");
		for (int i = 39; i<49; i++) cards[i] = new Card("" + (i-38), "hearts");
		cards[49] = new Card("jack", "hearts");
		cards[50] = new Card("queen", "hearts");
		cards[51] = new Card("king", "hearts");
	
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

public void shuffle(){
	Random rand = new Random();  		

	for (int i=0; i<cards.length; i++) {
	    int randomPosition = rand.nextInt(cards.length);
	    Card temp = cards[i];
	    cards[i] = cards[randomPosition];
	    cards[randomPosition] = temp;
	}

}
}
