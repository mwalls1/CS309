package com.mygdx.drop;

public class Player {
	private boolean isTurn;
	private String piece;
	public Player(boolean turn, String let)//creates a player with their game state
	{
		isTurn = turn;
		piece = let;
	}
	public boolean getTurn()//returns if it is there turn or not
	{
		return isTurn;
	}
	public void setTurn(boolean set)//sets the players turn state.
	{
		isTurn = set;
	}
	public String getLet()//returns what letter they are playing as
	{
		return piece;
	}
}
