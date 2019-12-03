package com.mygdx.ColesGames;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class CheckerPiece {

	private int value; //1 for RED, 2 for YELLOW, 3 for Possible Move
	private int[] position; //position[COL][ROW]
	private boolean king; //True if the piece is king'd
	private Sprite checker;
	ArrayList<int[]> possibleMoves;
	ArrayList<Sprite> possibleMoveSprites;
	
	public CheckerPiece(int value, int[] position) {
		this.value = value;
		this.position = position;
		this.king = false;
		updateSkin();
		checker.setSize(60, 60);
		possibleMoveSprites = new ArrayList<Sprite>();
	}
	
	public Sprite getSkin(){return checker;}
	
	public int getCol() {return position[0]/2;}
	
	public int getRow() {return position[1];}
	
	public int getValue() {return value;}
	
	public ArrayList<int[]> getPosibbleMoves(){ return possibleMoves;}
	
	public ArrayList<Sprite> getPossibleMoveSprites(){ return possibleMoveSprites;}
	
	public Sprite updateSkin() {
		if(value == 1) {
			if(!king) {
				checker = new Sprite(new Texture("redCircle.png"));
			}
			else checker = new Sprite(new Texture("redCircleKing.png"));
		}
		else if (value == 2){
			if(!king) {
				checker = new Sprite(new Texture("yellowCircle.png"));
			}
			else checker = new Sprite(new Texture("yellowCircleKing.png"));
		}
		else {
			checker = new Sprite(new Texture("download.png"));
		}
		checker.setPosition((float) ((position[0])*74.5+3+(new Random().nextInt(11)+3)), (float) (position[1]*74.0+(new Random().nextInt(11)+3))); //When random is 0 piece will touch left side, when 17 right side
		return checker;
	}
	
	public Sprite updateLocaiton(int[] position) {
		this.position = position;
		checker.setPosition((float) ((position[0])*74.5+3+(new Random().nextInt(11)+3)), (float) (position[1]*74.0+(new Random().nextInt(11)+3)));
		return checker;
	}
	
	public void setPossibleMoves(ArrayList<int[]> arr){
		possibleMoves = arr;
		possibleMoveSprites.clear();
		for (int[] move : arr) {
//			Sprite newMove = new Sprite(new Texture("download.png"));
//			newMove.setSize(60, 60);
//			newMove.setPosition((float) ((move[1]*2+move[0]%2)*74.5+12), (float) (move[0]*74.0+9));
//			possibleMoveSprites.add(newMove);
		}
	}
}
