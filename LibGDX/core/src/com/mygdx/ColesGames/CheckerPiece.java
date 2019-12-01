package com.mygdx.ColesGames;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CheckerPiece {

	private boolean color; //false for RED, true for YELLOW
	private int[] position; //position[X][Y]
	private boolean king; //True if the piece is king'd
	private Sprite checker;
	
	public CheckerPiece(boolean color, int[] position) {
		this.color = color;
		this.position = position;
		this.king = false;
		updateSkin();
		checker.setSize(60, 60);
	}
	
	public Sprite getSkin(){return checker;}
	
	public Sprite updateSkin() {
		if(!color) {
			if(!king) {
				checker = new Sprite(new Texture("redCircle.png"));
			}
			else checker = new Sprite(new Texture("redCircleKing.png"));
		}
		else {
			if(!king) {
				checker = new Sprite(new Texture("yellowCircle.png"));
			}
			else checker = new Sprite(new Texture("yellowCircleKing.png"));
		}
		checker.setPosition((float) ((position[0])*74.5+3+(new Random().nextInt(11)+3)), (float) (position[1]*74.0+(new Random().nextInt(11)+3))); //When random is 0 piece will touch left side, when 17 right side
		return checker;
	}
	
	public Sprite updateLocaiton(int[] position) {
		this.position = position;
		checker.setPosition(position[0]*75, position[1]*75);
		return checker;
	}
	
	
}
