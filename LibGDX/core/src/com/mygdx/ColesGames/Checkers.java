package com.mygdx.ColesGames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gui.MainScreen;

import util.Constants;

/**
 * Checkers
 * @author Cole Weitzel
 *
 */
public class Checkers extends Game implements Screen {

	// variables needed for the game
	private Game game;
	private SpriteBatch batch;

	// textures / sprites
	private Texture checkersBoard;
	private Sprite spriteCheckersBoard;

	// in game variables
	private boolean isTurn = false;
	private boolean isGameOver;
	private int mousex;
	private int mousey;
	
	private String[] colors = {"red","yellow","posMove"};
	private HashMap<String,ArrayList<CheckerPiece>> pieces = new HashMap<String,ArrayList<CheckerPiece>>();
	private int[][] gameBoardArr;
	ArrayList<Sprite> possibleMoves;
	String[] currentPiece; 
	
	
	public Checkers(Game game) {
		this.game = game;
		create();
	}

	@Override
	public void create() {
		Gdx.graphics.setResizable(false);
		Gdx.graphics.setWindowedMode(603, 600);
		//Initialize board
		checkersBoard = new Texture("checkersBoard1.png");
		spriteCheckersBoard = new Sprite(checkersBoard);
		spriteCheckersBoard.setSize(600, 600);
		gameBoardArr = new int[8][4];
		//Initialize game
		if (Constants.playerNumber == 0) isTurn = true; Constants.playerNumber = 1;
		isGameOver = false;
		possibleMoves = new ArrayList<Sprite>();
		currentPiece = null;
		//Initialize Colors
		for (String color : colors) pieces.put(color, new ArrayList<CheckerPiece>());
		setBoard();
		
		batch = new SpriteBatch();
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//Render the board
		spriteCheckersBoard.setPosition(0, 0);
		spriteCheckersBoard.draw(batch);
		gameBoardArr = new int[][] {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}}; //0 for empty
		//Render all pieces
		for (String color : colors) {
			for (CheckerPiece checker : pieces.get(color)) {
				checker.getSkin().draw(batch);
				gameBoardArr[checker.getRow()][checker.getCol()] = checker.getValue(); //1 for red, 2 for yellow, 3 for possible move
			}
		}
		for (String color : colors) {
			for (CheckerPiece checker : pieces.get(color)) { //Finds all possible moves for each piece
				checker.setPossibleMoves(getPossibleMoves(checker.getCol(), checker.getRow()));
			}
		}
			
		handleKeys();
		
		//Handle the players turn
		if (Gdx.input.justTouched() && !isGameOver) {
			int col = (int) (((Gdx.input.getX()-3)/75)/2);
			int row = (int) 7-(Gdx.input.getY()/75);
			System.out.println(col + " " + row );
			//Check to see if piece is clicked on
			if(gameBoardArr[row][col] == Constants.playerNumber || gameBoardArr[row][col] == 2) {
				pieces.get("posMove").clear();
				currentPiece = getPieceByPosition(row, col).split(" ");
				CheckerPiece piece = pieces.get(currentPiece[0]).get(Integer.parseInt(currentPiece[1]));
				possibleMoves = piece.getPossibleMoveSprites();
				for (int[] arr : piece.getPosibbleMoves()) {
					pieces.get("posMove").add(new CheckerPiece(3,new int[] {arr[1]*2+arr[0]%2,arr[0]}));
				}
				System.out.println(possibleMoves.toString());
			}
			//Check to see if possible move is clicked on and moves piece
			else if(gameBoardArr[row][col] == 3) {
				//Checks to see if move was a jump
				CheckerPiece currentChecker = pieces.get(currentPiece[0]).get(Integer.parseInt(currentPiece[1]));
				if(Math.abs(currentChecker.getRow() - row) == 2) {
					boolean leftOrRight = ((col)-currentChecker.getCol()) == 1;
					int jumpedCol = 0;// = upOrDown * Math.abs(currentChecker.getRow()%2-1)+currentChecker.getCol();
					if (!leftOrRight && currentChecker.getRow()%2 == 0) jumpedCol = -1 + currentChecker.getCol();
					else if (leftOrRight && currentChecker.getRow()%2 == 1) jumpedCol = 1 + currentChecker.getCol();
					else jumpedCol = currentChecker.getCol();
					int jumpedRow = (row+currentChecker.getRow())/2;
					System.out.println("JumpedCol: "+jumpedCol+",JumpedRow: " + jumpedRow);
					String[] jumpedPiece = getPieceByPosition(jumpedRow,jumpedCol).split(" ");
					pieces.get(jumpedPiece[0]).remove(Integer.parseInt(jumpedPiece[1]));
				}
				currentChecker.updateLocaiton(new int[]{col*2+row%2,row});
				pieces.get("posMove").clear();
				
			}
		}
		for (Sprite possibleMove : possibleMoves) {
			possibleMove.draw(batch);
		}
		
		
		
		batch.end();
	}
	
	/**
	 * checks whether the escape key was pressed to end the game
	 */
	private void handleKeys() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			game.setScreen(new MainScreen(game));
		}
		if(Gdx.input.isKeyJustPressed(Keys.P)) {
			pause();
		}
		if(Gdx.input.isKeyJustPressed(Keys.R)) {
			restart();
		}
	}
	
	/**
	 * restarts the game
	 */
	public void restart() {
		create();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}
	
	private String getOtherColor(String color) {
		if (colors[1].equals(color)) return colors[1];
		else return colors[0];
	}
	
	private void setBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col <= 7; col+=2) {
				if (row == 1 && col == 0) col++;
				pieces.get(colors[0]).add(new CheckerPiece(1, new int[]{col,row}));
				pieces.get(colors[1]).add(new CheckerPiece(2, new int[]{7-col,7-row}));
			}
		}
	}
	
	private ArrayList<int[]> getPossibleMoves(int col, int row){
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		if (gameBoardArr[row][col] == 0) throw new NoSuchElementException();
		try { //JUMP UP LEFT
			if(gameBoardArr[row+2][col-1] %3 == 0 && gameBoardArr[row+1][col-1+row%2] != gameBoardArr[row][col] && gameBoardArr[row+1][col-1+row%2] %3 != 0) possibleMoves.add(new int[] {row+2,col-1}); //If: up2&left is empty, up&left is not empty, up&left is not same
		} catch(IndexOutOfBoundsException e) {};
		try { //JUMP UP RIGHT
			if(gameBoardArr[row+2][col+1] %3 == 0 && gameBoardArr[row+1][col+row%2] != gameBoardArr[row][col] &&  gameBoardArr[row+1][col+row%2] %3 != 0) possibleMoves.add(new int[] {row+2,col+1});//If: up2&right is empty, up&right is not empty, up&right is not same
		} catch(IndexOutOfBoundsException e) {};
		try { //JUMP UP LEFT
			if(gameBoardArr[row+1][col-1+row%2] %3 == 0) possibleMoves.add(new int[] {row+1,col-1+row%2}); //If: up&left is empty
		} catch(IndexOutOfBoundsException e) {};
		try { //MOVE UP RIGHT
			if(gameBoardArr[row+1][col+row%2] %3 == 0) possibleMoves.add(new int[] {row+1,col+row%2});//If: up&right is empty
		} catch(IndexOutOfBoundsException e) {};
		try { //JUMP DOWN LEFT
			if(gameBoardArr[row-2][col-1] %3 == 0 && gameBoardArr[row-1][col-1+row%2] != gameBoardArr[row][col] && gameBoardArr[row-1][col-1+row%2] %3 != 0) possibleMoves.add(new int[] {row-2,col-1}); //If: down2&left is empty, up&left is not empty, up&left is not same
		} catch(IndexOutOfBoundsException e) {};
		try { //JUMP DOWN RIGHT
			if(gameBoardArr[row-2][col+1] %3 == 0 && gameBoardArr[row-1][col+row%2] != gameBoardArr[row][col] &&  gameBoardArr[row-1][col+row%2] %3 != 0) possibleMoves.add(new int[] {row-2,col+1});//If: down2&right is empty, up&right is not empty, up&right is not same
		} catch(IndexOutOfBoundsException e) {};
		try { //JUMP DOWN LEFT
			if(gameBoardArr[row-1][col-1+row%2] %3 == 0) possibleMoves.add(new int[] {row-1,col-1+row%2}); //If:downp&left is empty
		} catch(IndexOutOfBoundsException e) {};
		try { //MOVE DOWN RIGHT
			if(gameBoardArr[row-1][col+row%2] %3 == 0) possibleMoves.add(new int[] {row-1,col+row%2});//If: down&right is empty
		} catch(IndexOutOfBoundsException e) {};
		
		return possibleMoves;
	}
	
	private String getPieceByPosition(int row, int col) {
		for (String color : colors) {
			for (int i = 0; i < pieces.get(color).size(); i++) {
				if(pieces.get(color).get(i).getCol() == col && pieces.get(color).get(i).getRow() == row) return color + " " + i;
			}
//			for (CheckerPiece checker : pieces.get(color)) {
//				if(checker.getCol() == col && checker.getRow() == row) return checker;
//			}
		}
		return null;
	}
}
