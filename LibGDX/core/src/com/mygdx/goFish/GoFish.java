package com.mygdx.goFish;

import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.Cards.Card;
import com.mygdx.Cards.Deck;

public class GoFish{
	private Player p1;
	private Player p2;
	private Player p3;
	private Player p4;
	private AssetManager mananger;
	private int deckIterator;
	private Deck deck;
	
	public GoFish() //Number of players determined by amount of names
	{
		p1 = new Player("p1");
		p2 = new Player("p2");
		p3 = new Player("p3");
		p4 = new Player("p4");
		
		
	}
	
	public void play(Player currentPlayer, Player otherPlayer, String rank)
	{
		if (otherPlayer.hasCard(rank)) {
			Card toMove = otherPlayer.getCard(otherPlayer.getIndexOfCardTaken());
			currentPlayer.addCard(toMove); //Add other player's card to current player's hand
			otherPlayer.removeCard(toMove);
		}
	}
}