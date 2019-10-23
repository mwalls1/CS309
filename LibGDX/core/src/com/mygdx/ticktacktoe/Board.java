package com.mygdx.ticktacktoe;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Board {
	private int width, height;
	private Sprite sprite;
	private ArrayList<Zone> zones = new ArrayList<Zone>();
	private Texture texture = new Texture(Gdx.files.internal("board.png"));
	public Board()
	{
		width = 600;
		height = 600;
		sprite = new Sprite(texture);
		sprite.setSize(width, height);
		zones.add(new Zone(0, 100, 189, 189));
		zones.add(new Zone(213, 100, 189, 189));
		zones.add(new Zone(413, 100, 189, 189));
		zones.add(new Zone(0, 312, 189, 189));
		zones.add(new Zone(213,312,189,189));
		zones.add(new Zone(413,312,189,189));
		zones.add(new Zone(0, 511, 189, 189));
		zones.add(new Zone(213,511,189,189));
		zones.add(new Zone(413,511,189,189));
	}
	public String checkWin()
	{
		//Check top row
		if(zones.get(0).isActive())
		{
			if(zones.get(0).getLetter().equals(zones.get(1).getLetter())&&zones.get(0).getLetter().equals(zones.get(2).getLetter()))
			{
				return zones.get(0).getLetter();
			}
		}
		//check middle row
		if(zones.get(3).isActive())
		{
			if(zones.get(3).getLetter().equals(zones.get(4).getLetter())&&zones.get(3).getLetter().equals(zones.get(5).getLetter()))	
			{
				return zones.get(3).getLetter();
			}
		}
		//check bottom row
		if(zones.get(6).isActive())
		{
			if(zones.get(6).getLetter().equals(zones.get(7).getLetter())&&zones.get(6).getLetter().equals(zones.get(8).getLetter()))
			{
				return zones.get(6).getLetter();
			}
		}
		//check left coloumn
		if(zones.get(0).isActive())
		{
			if(zones.get(0).getLetter().equals(zones.get(3).getLetter())&&zones.get(0).getLetter().equals(zones.get(6).getLetter()))
			{
				return zones.get(0).getLetter();
			}
		}
		//check middle column
		if(zones.get(1).isActive())
		{
			if(zones.get(1).getLetter().equals(zones.get(4).getLetter())&&zones.get(1).getLetter().equals(zones.get(7).getLetter()))
			{
				return zones.get(1).getLetter();
			}
		}
		//check right column
		if(zones.get(2).isActive())
		{
			if(zones.get(2).getLetter().equals(zones.get(5).getLetter())&&zones.get(2).getLetter().equals(zones.get(7).getLetter()))	
			{
				return zones.get(2).getLetter();
			}
		}
		//check top left to bottom right diagonal
		if(zones.get(0).isActive())
		{
			if(zones.get(0).getLetter().equals(zones.get(4).getLetter())&&zones.get(0).getLetter().equals(zones.get(8).getLetter()))	
			{
				return zones.get(0).getLetter();
			}
		}
		//check top right to bottom left diagonal
		if(zones.get(2).isActive())
		{
			if(zones.get(2).getLetter().equals(zones.get(4).getLetter())&&zones.get(2).getLetter().equals(zones.get(6).getLetter()))	
			{
				return zones.get(2).getLetter();
			}
		}
		return "n";
	}
	public void render(SpriteBatch batch, Player play1, Player play2, int mouseX, int mouseY)
	{
		sprite.draw(batch);
		if(play1.getTurn())
		{
			for(Zone zone : zones)
			{
				if(!zone.isActive()&&zone.contains(mouseX, mouseY)&&Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
				{
					zone.setLetter(play1.getLet());
					play1.setTurn(false);
					play2.setTurn(true);
					System.out.println("Player 2's turn now");
				}
				zone.render(batch);
			}
		}
		else if(play2.getTurn())
		{
			//When multiplayer, we will call for the update here
			for(Zone zone : zones)
			{
				if(!zone.isActive()&&zone.contains(mouseX, mouseY)&&Gdx.input.isButtonJustPressed(Input.Buttons.LEFT))
				{
					zone.setLetter(play2.getLet());
					play2.setTurn(false);
					play1.setTurn(true);
					System.out.println("Player 1's turn now");
				}
				zone.render(batch);
			}
		}
	}

}
