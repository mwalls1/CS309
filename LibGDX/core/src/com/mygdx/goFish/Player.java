package com.mygdx.goFish;

import java.util.ArrayList;

import com.mygdx.Cards.Card;

public class Player {
	private ArrayList<Card> hand;
	private String name;
	private int score;
	private int indexOfCardTaken;
	public Player(String name)
	{
		this.name = name;
		score = 0;
		hand = new ArrayList<Card>();
	}
	
	public void addCard(Card card)
	{
		hand.add(card);
	}
	
	public void removeCard(Card card)
	{
		hand.remove(card);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void checkForScore()
	{
		int[] cardTypes = {0,0,0,0,0,0,0,0,0,0,0,0,0};
		
		for (int i = 0; i<hand.size(); i++)
		{
			if(hand.get(i).getRank() == "jack") cardTypes[10]++;
			else if(hand.get(i).getRank() == "queen") cardTypes[11]++;
			else if (hand.get(i).getRank() == "king") cardTypes[12]++;
			else 
			{
				int rank = hand.get(i).getRank().indexOf(0);
				System.out.println("Interpreted '" + hand.get(i).getRank() +"' as " + rank);
				cardTypes[rank]++;
			}
		}
		for (int i = 0; i<cardTypes.length; i++)
		{
			if (cardTypes[i] >= 4)
			{
				score++;
				removeAllRank(i);
			}
		}
			
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void removeAllRank(int givenRank)
	{
		ArrayList<Card> toRemove = new ArrayList<Card>();
		String rank;
		if (givenRank == 11) rank = "jack";
		else if (givenRank == 12) rank = "queen";
		else if (givenRank == 13) rank = "king";
		else rank = "" + givenRank;
		
		for (int i = 0; i<hand.size(); i++)
		{
			if (hand.get(i).getRank() == rank) toRemove.add(hand.get(i)); 
		}
		
		hand.removeAll(toRemove);
	}
	
	public boolean hasCard(String rank)
	{
		for (int i = 0; i<hand.size(); i++)
		{
			if (hand.get(i).getRank() == rank) {
				indexOfCardTaken = i;
				return true;
			}
		}
		return false;
	}
	
	public int getIndexOfCardTaken()
	{
		return indexOfCardTaken;
	}
	
	public Card getCard(int index)
	{
		return hand.get(index);
	}
	
	public void setHand(Card[] cards)
	{
		hand = new ArrayList<Card>();
		for (int i = 0; i<cards.length; i++) hand.add(cards[i]);
	}
	
	public String toString()
	{
		String result = "";
		for (int i = 0; i<hand.size(); i++)
		{
			result += hand.get(i).toString();
		}
		return result;
	}
	
	public ArrayList<Card> getHand()
	{
		return hand;
	}
	
}
