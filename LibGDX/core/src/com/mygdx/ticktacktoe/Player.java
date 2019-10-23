package com.mygdx.ticktacktoe;

public class Player {
	private boolean isTurn;
	private char piece;
	public Player(boolean turn, char let)
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
	public char getLet()
	{
		return piece;
	}
}
