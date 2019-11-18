package com.mygdx.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gui.MainScreen;
import java.util.concurrent.TimeUnit;

public class ConnectFour extends Game implements Screen {
	private Game game;
	private BitmapFont text;
	private SpriteBatch batch;
	private Texture yellowCircleImage;
	private Texture redCircleImage;
	private Texture connectFourBoard;
	private Zone[][] zones;
	private Sprite spriteYellow;
	private Sprite spriteRed;

	private int mousex;
	private int mousey;
	private boolean playerRedORYellow;
	private boolean isGameOver;
	private String winner = "empty";
	private boolean paused;

	public ConnectFour(Game game) {
		this.game = game;
		create();
	}

	/**
	 * initializes all necessary components for the game
	 */
	@Override
	public void create() {
		Gdx.graphics.setResizable(false);
		Gdx.graphics.setWindowedMode(640, 520);
		
		zones = new Zone[6][7];

		yellowCircleImage = new Texture("yellowCircle.png");
		redCircleImage = new Texture("redCircle.png");
		connectFourBoard = new Texture("connectFourBoard.png");

		spriteYellow = new Sprite(yellowCircleImage);
		spriteRed = new Sprite(redCircleImage);
		spriteYellow.setSize(80, 80);
		spriteRed.setSize(80, 80);

		batch = new SpriteBatch();

		playerRedORYellow = true; // true is red, false is yellow
		isGameOver = false;
		paused = false;
		
		text = new BitmapFont();
		text.setColor(new Color(0,0,0,1));

		createZones();
	}

	/**
	 * Updates game status
	 */
	@Override
	public void render(float delta) {
		handleKeys();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(connectFourBoard, 0, 0);
		if(playerRedORYellow && !isGameOver) text.draw(batch, "Player: red", 20, 500);
		else if(!playerRedORYellow && !isGameOver) text.draw(batch, "Player: yellow", 20, 500);
		if(isGameOver) {
			text.draw(batch, winner, 20, 515);
		}
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

		if (Gdx.input.justTouched() && !isGameOver) {
			mousex = Gdx.input.getX();
			mousey = 520 - Gdx.input.getY();
//			System.out.println(mousex + " " + mousey);
			for (int r = 0; r < 6; r++) {
				for (int c = 0; c < 7; c++) {
					if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && playerRedORYellow) {
						int[] index = { r, c };
						index = findLowestTile(index);
						zones[index[0]][index[1]].setTile("red");
						zones[index[0]][index[1]].setActive(true);
						playerRedORYellow = false;
//						System.out.println(r + " " + c);
					} else if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && !playerRedORYellow) {
						int[] index = { r, c };
						index = findLowestTile(index);
						zones[index[0]][index[1]].setTile("yellow");
						zones[index[0]][index[1]].setActive(true);
						playerRedORYellow = true;
//						System.out.println(r + " " + c);
					}
				}
			}
		}
		
		if(!isGameOver) {
			isGameOver = checkGameOver();
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
	 * returns true if the game is over
	 * assigns the value of the private variable winner
	 * with the output string for when game won
	 */
	private boolean checkGameOver() {
		int row, col;
		if(isGameOver == true) return false;
		
		// check 4 in a row (horizontal)
		int plus4 = 0;
		for(row = 0; row < 6; row++) {
			for(col = 0; col < 4; col++) {
				if(zones[row][col].getTile().equals("red") && zones[row][col+1].getTile().equals("red") && zones[row][col+2].getTile().equals("red") && zones[row][col+3].getTile().equals("red")) {
					plus4 = col+4;
					winner = "Game is over, red won at row:" + row + " col:" + col + "-" + plus4 + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(zones[row][col].getTile().equals("yellow") && zones[row][col+1].getTile().equals("yellow") && zones[row][col+2].getTile().equals("yellow") && zones[row][col+3].getTile().equals("yellow")) {
					plus4 = col+4;
					winner = "Game is over, yellow won at row:" + row + " col:" + col + "-" + plus4 + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		// check 4 in a column (vertical)
		for(col = 0; col < 7; col++) {
			for(row = 0; row < 3; row++) {
				if(zones[row][col].getTile().equals("red") && zones[row+1][col].getTile().equals("red") && zones[row+2][col].getTile().equals("red") && zones[row+3][col].getTile().equals("red")) {
					plus4 = row+4;
					winner = "Game is over, red won at row:" + row + "-" + plus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(zones[row][col].getTile().equals("yellow") && zones[row+1][col].getTile().equals("yellow") && zones[row+2][col].getTile().equals("yellow") && zones[row+3][col].getTile().equals("yellow")) {
					plus4 = row+4;
					winner = "Game is over, yellow won at row:" + row + "-" + plus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		// check 4 diagonal (left to right bottom to top - 45 degrees)
//		for(row = 0; row < 3; row++) {
//			for(col = 0; col < 4; col++) {
//				
//			}
//		}
		
		return false;
	}
	
	public void pause() {
		paused = !paused;
	}

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
