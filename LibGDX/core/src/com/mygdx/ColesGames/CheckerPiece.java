package com.mygdx.ColesGames;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CheckerPiece {

	private boolean color; //false for RED, true for YELLOW
	private int[] position; //position[COL][ROW]
	private boolean king; //True if the piece is king'd
	private Sprite checker;
	ArrayList<int[]> possibleMoves;
	ArrayList<Sprite> possibleMoveSprites;
	
	public CheckerPiece(boolean color, int[] position) {
		this.color = color;
		this.position = position;
		this.king = false;
		updateSkin();
		checker.setSize(60, 60);
		possibleMoveSprites = new ArrayList<Sprite>();
	}
	
	public Sprite getSkin(){return checker;}
	
	public int getCol() {return position[0]/2;}
	
	public int getRow() {return position[1];}
	
	public boolean getColor() {return color;}
	
	public ArrayList<Sprite> getPossibleMoveSprites(){ return possibleMoveSprites;}
	
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
	
	public void setPossibleMoves(ArrayList<int[]> arr){
		possibleMoves = arr;
		possibleMoveSprites.clear();
		for (int[] move : arr) {
			Sprite newMove = new Sprite(new Texture("download.png"));
			newMove.setSize(60, 60);
			newMove.setPosition((float) ((move[1]*2+move[0]%2)*74.5+12), (float) (move[0]*74.0+9));
			possibleMoveSprites.add(newMove);
		}
	}
}
