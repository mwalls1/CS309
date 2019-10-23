package com.mygdx.ticktacktoe;

public class Player {
	private boolean isTurn;
	private String piece;
	public Player(boolean turn, String let)
	{
		isTurn = turn;
		piece = let;
	}
	public boolean getTurn()
	{
		return isTurn;
	}
	public void setTurn(boolean set)
	{
		isTurn = set;
	}
	public String getLet()
	{
		return piece;
	}
}
