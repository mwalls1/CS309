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
	
	private String[] colors = {"red","yellow"};
	private HashMap<String,ArrayList<CheckerPiece>> pieces = new HashMap<String,ArrayList<CheckerPiece>>();
	private int[][] gameBoardArr;
	ArrayList<Sprite> possibleMoves;
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
		if (Constants.playerNumber == 0) isTurn = true;
		isGameOver = false;
		possibleMoves = new ArrayList<Sprite>();
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
				if (checker.getColor()) gameBoardArr[checker.getRow()][checker.getCol()] = 2; //2 for yellow
				else gameBoardArr[checker.getRow()][checker.getCol()] = 1; //1 for red
			}
		}
		for (CheckerPiece checker : pieces.get("red")) { //Finds all possible moves for each piece
			checker.setPossibleMoves(getPossibleMoves(checker.getCol(), checker.getRow()));
		}
		handleKeys();
		
		//Handle the players turn
		if (Gdx.input.justTouched() && !isGameOver) {
			int col = (int) (((Gdx.input.getX()-3)/75)/2);
			int row = (int) 7-(Gdx.input.getY()/75);
			System.out.println(col + " " + row );
			if(gameBoardArr[row][col] == 1) {
				possibleMoves = getPieceByPosition(row, col).getPossibleMoveSprites();
				System.out.println(possibleMoves.toString());
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
	
	
	private void setBoard() {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col <= 7; col+=2) {
				if (row == 1 && col == 0) col++;
				pieces.get(colors[0]).add(new CheckerPiece(false, new int[]{col,row}));
				pieces.get(colors[1]).add(new CheckerPiece(true, new int[]{7-col,7-row}));
			}
		}
	}
	
	private ArrayList<int[]> getPossibleMoves(int col, int row){
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		if (gameBoardArr[row][col] == 0) throw new NoSuchElementException();
		try { //JUMP UP LEFT
			if(gameBoardArr[row+2][col-1] == 0 && gameBoardArr[row+1][col-1+row%2] != gameBoardArr[row][col] && gameBoardArr[row+1][col-1+row%2] != 0) possibleMoves.add(new int[] {row+2,col-1}); //If: up2&left is empty, up&left is not empty, up&left is not same
		} catch(IndexOutOfBoundsException e) {};
		try { //JUMP UP RIGHT
			if(gameBoardArr[row+2][col+1] == 0 && gameBoardArr[row+1][col+row%2] != gameBoardArr[row][col] &&  gameBoardArr[row+1][col+row%2] != 0) possibleMoves.add(new int[] {row+2,col+1});//If: up2&right is empty, up&right is not empty, up&right is not same
		} catch(IndexOutOfBoundsException e) {};
		try { //JUMP UP LEFT
			if(gameBoardArr[row+1][col-1+row%2] == 0) possibleMoves.add(new int[] {row+1,col-1+row%2}); //If: up&left is empty
		} catch(IndexOutOfBoundsException e) {};
		try { //MOVE UP RIGHT
			if(gameBoardArr[row+1][col+row%2] == 0) possibleMoves.add(new int[] {row+1,col+row%2});//If: up&right is empty
		} catch(IndexOutOfBoundsException e) {};
		
		return possibleMoves;
	}
	
	private CheckerPiece getPieceByPosition(int row, int col) {
		for (String color : colors) {
			for (CheckerPiece checker : pieces.get(color)) {
				if(checker.getCol() == col && checker.getRow() == row) return checker;
			}
		}
		return null;
	}
}
