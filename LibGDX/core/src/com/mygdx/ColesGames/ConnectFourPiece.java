package com.mygdx.ColesGames;

import java.util.ArrayList;

public class ConnectFourPiece {
	
	private int row;
	private int col;
	// each move is in format row:col
	private ArrayList<String> possibleMoves;

	ConnectFourPiece(){
		
	}
	
	ConnectFourPiece(int r, int c){
		row = -1;
		col = -1;
		possibleMoves = new ArrayList<String>();
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public ArrayList<String> getPossibleMove(){
		return possibleMoves;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public void setCol(int c) {
		col = c;
	}
	
	public void addPossibleMove(String move) {
		possibleMoves.add(move);
	}
	
}
