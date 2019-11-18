package com.mygdx.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gui.MainScreen;
import java.util.concurrent.TimeUnit;

public class ConnectFour extends Game implements Screen {
	private Game game;
	private SpriteBatch batch;
	private Texture yellowCircleImage;
	private Texture redCircleImage;
	private Texture connectFourBoard;
	private Zone[][] zones;
	private Sprite spriteYellow;
	private Sprite spriteRed;

	private int mousex;
	private int mousey;
	private boolean playerRedorYellow;
	private boolean isGameOver;
	private String winner = "empty";

	public ConnectFour(Game game) {
		this.game = game;
		create();
	}

	/**
	 * initializes all necessary components for the game
	 */
	@Override
	public void create() {
		zones = new Zone[6][7];

		yellowCircleImage = new Texture("yellowCircle.png");
		redCircleImage = new Texture("redCircle.png");
		connectFourBoard = new Texture("connectFourBoard.png");

		spriteYellow = new Sprite(yellowCircleImage);
		spriteRed = new Sprite(redCircleImage);
		spriteYellow.setSize(80, 80);
		spriteRed.setSize(80, 80);

		batch = new SpriteBatch();

		playerRedorYellow = true; // true is red, false is yellow
		isGameOver = false;

		createZones();
	}

	/**
	 * Updates game status
	 */
	@Override
	public void render(float delta) {
		endGame();
		isGameOver = checkGameOver();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(connectFourBoard, 0, 0);
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				if (zones[r][c].getTile().equals("empty"))
					continue;
				if (zones[r][c].getTile().equals("red") && zones[r][c].isActive()) {
					spriteRed.setPosition(zones[r][c].getX(), zones[r][c].getY());
					spriteRed.draw(batch);
				}
				if (zones[r][c].getTile().equals("yellow") && zones[r][c].isActive()) {
					spriteYellow.setPosition(zones[r][c].getX(), zones[r][c].getY());
					spriteYellow.draw(batch);
				}
			}
		}
		batch.end();

		if (Gdx.input.justTouched()) {
			mousex = Gdx.input.getX();
			mousey = 480 - Gdx.input.getY();
			for (int r = 0; r < 6; r++) {
				for (int c = 0; c < 7; c++) {
					if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && playerRedorYellow) {
						int[] index = { r, c };
						index = findLowestTile(index);
						zones[index[0]][index[1]].setTile("red");
						zones[index[0]][index[1]].setActive(true);
						playerRedorYellow = false;
						System.out.println(r + " " + c);
					} else if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && !playerRedorYellow) {
						int[] index = { r, c };
						index = findLowestTile(index);
						zones[index[0]][index[1]].setTile("yellow");
						zones[index[0]][index[1]].setActive(true);
						playerRedorYellow = true;
						System.out.println(r + " " + c);
					}
				}
			}
		}
		
		if(isGameOver) {
			System.out.println("game is over, winner is: " + winner);
//			render();
//			try {
//				TimeUnit.SECONDS.sleep(5);
//				game.setScreen(new MainScreen(game));
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}

	/**
	 * returns the lowest row that is not active
	 */
	private int[] findLowestTile(int[] arr) {
		if (arr[0] == 0)
			return arr;
		int row = arr[0];
		int col = arr[1];
		for (int i = row; i > 0; i--) {
			if (!zones[row - 1][col].isActive()) {
				row--;
			}
		}
		arr[0] = row;
		arr[1] = col;
		return arr;
	}

	/**
	 * creates the zones on screen to click
	 */
	private void createZones() {
		int x = 6;
		int y = 0;
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				zones[r][c] = new Zone(x, y, 80, 80);
				x += 91;
			}
			y += 80;
			x = 6;
		}
	}

	/**
	 * checks whether the escape key was pressed to end the game
	 */
	private void endGame() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			game.setScreen(new MainScreen(game));
		}
	}

	/**
	 * returns true if the game is over
	 */
	private boolean checkGameOver() {
		int i;
		int j;
		int row, col;
		if(isGameOver == true) return false;
		
		// check 4 in a row (horz)
		for(row = 0; row < 6; row++) {
			for(col = 0; col < 4; col++) {
				if(zones[row][col].getTile().equals("red") && zones[row][col+1].getTile().equals("red") && zones[row][col+2].getTile().equals("red") && zones[row][col+3].getTile().equals("red")) {
					winner = "red";
					return true;
				}
				if(zones[row][col].getTile().equals("yellow") && zones[row][col+1].getTile().equals("yellow") && zones[row][col+2].getTile().equals("yellow") && zones[row][col+3].getTile().equals("yellow")) {
					winner = "yellow";
					return true;
				}
			}
		}
		
		return false;
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
