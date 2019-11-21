package com.mygdx.ColesGames;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
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

	public Checkers(Game game) {
		this.game = game;
		create();
	}

	@Override
	public void create() {
		Gdx.graphics.setResizable(false);
		Gdx.graphics.setWindowedMode(603, 600);
		
		batch = new SpriteBatch();

		checkersBoard = new Texture("checkersBoard1.png");
		spriteCheckersBoard = new Sprite(checkersBoard);
		spriteCheckersBoard.setSize(600, 600);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleKeys();
		
		batch.begin();
		spriteCheckersBoard.setPosition(0, 0);
		spriteCheckersBoard.draw(batch);
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

}
