package com.mygdx.ColesGames;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gui.MainScreen;

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
	private boolean isGameOver;
	private int mousex;
	private int mousey;
	
	private String[] colors = {"red","yellow"};
	private HashMap<String,ArrayList<CheckerPiece>> pieces = new HashMap<String,ArrayList<CheckerPiece>>();

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
		//Render all pieces
		for (String color : colors) {
			for (CheckerPiece checker : pieces.get(color)) {
				checker.getSkin().draw(batch);
			}
		}
		handleKeys();
		
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
				CheckerPiece checker = new CheckerPiece(false, new int[]{col,row});
				pieces.get(colors[0]).add(checker);
			}
		}
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col <= 7; col+=2) {
				if (row == 1 && col == 0) col++;
				CheckerPiece checker = new CheckerPiece(true, new int[]{7-col,7-row});
				pieces.get(colors[1]).add(checker);
			}
		}
	}
}
