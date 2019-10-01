package com.mygdx.gui;

public class Score {
	private Integer gameID;
	private Integer score;
	private String user;

	public Score(Integer gameID, Integer score, String user)
	{
		this.gameID = gameID;
		this.score = score;
		this.user = user;
	}
	
	
	public Integer getScore()
	{
		return score;
	}
	
	public Integer getGameID()
	{
		return gameID;
	}
	
	public String getUserName()
	{
		return user;
	}
	
	public void sendScore()
	{
		//Some stuff goes here... idrk
		
	}
}
