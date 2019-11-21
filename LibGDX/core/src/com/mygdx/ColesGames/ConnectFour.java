package com.mygdx.ColesGames;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.gui.MainScreen;

/**
 * Connect Four game
 * Images were downloaded from online for free
 * @author Cole Weitzel
 *
 */
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
	private Stage stage;
	private static Skin skin;

	private int mousex;
	private int mousey;
	private boolean playerRedORYellow; // true is red, false if yellow
	private boolean isGameOver;
	private String winner = "empty";
	private boolean paused;
	private int difficulty;
	private int numOfYellow;
	private String yellowDirection;
	
	public ConnectFour() {
		create();
	}

	public ConnectFour(Game game, int diff) {
		this.game = game;
		difficulty = diff;
		System.out.println(diff);
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

		yellowCircleImage = new Texture("yellowCircle2.png");
		redCircleImage = new Texture("redCircle.png");
		connectFourBoard = new Texture("connectFourBoard.png");

		spriteYellow = new Sprite(yellowCircleImage);
		spriteRed = new Sprite(redCircleImage);
		spriteYellow.setSize(82, 80);
		spriteRed.setSize(80, 80);

		batch = new SpriteBatch();

		playerRedORYellow = true; // true is red, false is yellow
		isGameOver = false;
		paused = false;
		numOfYellow = 0;
		yellowDirection = "none";
		
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
		drawScreen();
		
		// check if the game has been won by either player
		if(!isGameOver) isGameOver = checkGameOver(zones);
		
		// handle the user pressing and place their tile
		if (difficulty > 0 && Gdx.input.justTouched() && !isGameOver) placeUserTile();
		
		// for difficulty = 0 is PvP
		if(difficulty == 0) difficulty0();
		
		// for difficulty = 1 easy (random AI)
		else if(difficulty == 1) difficulty1();
		
		// for difficulty = 2 (AI tries to win)
		else if(difficulty == 2) difficulty2();
	}
	
	/**
	 * place user tile
	 */
	private void placeUserTile() {
		if(playerRedORYellow) {
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
					}
				}
			}
		}
	}
	
	/**
	 * PvP play
	 */
	private void difficulty0() {
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
	}
	
	/**
	 * logic for the AI to place a random tile
	 */
	private void difficulty1() {
		if(!playerRedORYellow) {
			boolean tileFound = false;
			while(!tileFound) {
				int r = new Random().nextInt(6);
				int c = new Random().nextInt(7);
				if(!zones[r][c].isActive()) {
					int[] index = { r, c };
					index = findLowestTile(index);
					zones[index[0]][index[1]].setTile("yellow");
					zones[index[0]][index[1]].setActive(true);
					playerRedORYellow = true;
					tileFound = true;
				}
			}
		}
	}
	
	/**
	 * logic for difficulty 2
	 */
	private void difficulty2() {
		if(!playerRedORYellow) {
			numOfYellow = 0;
			int[] arr = new int[43];
			for(int r = 0; r < 6; r++) {
				for(int c = 0; c < 7; c++) {
					if(zones[r][c].getTile().equals("yellow")) {
						arr[numOfYellow] = r;
						arr[numOfYellow+1] = c;
						numOfYellow+=2;
					}
				}
			}
			boolean isTilePlaced = false;
			for(int i = 0; i < numOfYellow; i+=2) {
				// check to the left, col > 0, row == 0
				if(arr[i+1] > 0 && arr[i] == 0 && !zones[arr[i]][arr[i+1]-1].isActive()) {
					zones[arr[i]][arr[i+1]-1].setTile("yellow");
					zones[arr[i]][arr[i+1]-1].setActive(true);
					playerRedORYellow = true;
					isTilePlaced = true;
					break;
				}
				// check to the left, col > 0, row > 0
				else if(arr[i] > 0 && arr[i+1] > 0 && zones[arr[i]-1][arr[i+1]-1].isActive() && !zones[arr[i]][arr[i+1]-1].isActive()) {
					zones[arr[i]][arr[i+1]-1].setTile("yellow");
					zones[arr[i]][arr[i+1]-1].setActive(true);
					playerRedORYellow = true;
					isTilePlaced = true;
					break;
				}
				// check left diagonal, col > 0, row < 5
				else if(arr[i+1] > 0 && arr[i] < 5 && !zones[arr[i]+1][arr[i+1]-1].isActive() && zones[arr[i]][arr[i+1]-1].isActive()) {
					zones[arr[i]+1][arr[i+1]-1].setTile("yellow");
					zones[arr[i]+1][arr[i+1]-1].setActive(true);
					playerRedORYellow = true;
					isTilePlaced = true;
					break;
				}
				// check up, row < 5
				else if(arr[i] < 5 && !zones[arr[i]+1][arr[i+1]].isActive()) {
					zones[arr[i]+1][arr[i+1]].setTile("yellow");
					zones[arr[i]+1][arr[i+1]].setActive(true);
					playerRedORYellow = true;
					isTilePlaced = true;
					break;
				}
				// check right diagonal, col < 6, row < 5
				else if(arr[i] < 5 && arr[i+1] < 6 && !zones[arr[i]+1][arr[i+1]+1].isActive() && zones[arr[i]][arr[i+1]+1].isActive()) {
					zones[arr[i]+1][arr[i+1]+1].setTile("yellow");
					zones[arr[i]+1][arr[i+1]+1].setActive(true);
					playerRedORYellow = true;
					isTilePlaced = true;
					break;
				}
				// check to the right, col < 6, row == 0
				else if(arr[i+1] < 6 && arr[i] == 0 && !zones[arr[i]][arr[i+1]+1].isActive()) {
					zones[arr[i]][arr[i+1]+1].setTile("yellow");
					zones[arr[i]][arr[i+1]+1].setActive(true);
					playerRedORYellow = true;
					isTilePlaced = true;
					break;
				}
				// check to the right, col < 6, row > 0
				else if(arr[i] > 0 && arr[i+1] < 6 && zones[arr[i]-1][arr[i+1]+1].isActive() && !zones[arr[i]][arr[i+1]+1].isActive()) {
					zones[arr[i]][arr[i+1]+1].setTile("yellow");
					zones[arr[i]][arr[i+1]+1].setActive(true);
					playerRedORYellow = true;
					isTilePlaced = true;
					break;
				}
			}
			if(!isTilePlaced) {
				numOfYellow = 0;
			}
			// if 0 yellow tiles, place at random spot
			if(numOfYellow == 0) {
				difficulty1();
			}
		}
	}
	
	/**
	 * draw the screen from render
	 */
	private void drawScreen() {
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
	}
	
	
	/**
	 * returns the lowest row that is not active
	 */
	public int[] findLowestTile(int[] arr) {
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
	 * with the output string for when game is won
	 */
	public boolean checkGameOver(Zone[][] z) {
		int row, col;
		if(isGameOver == true) return false;
		
		// check 4 in a row (horizontal)
		int rowPlus4 = 0;
		int colPlus4 = 0;
		for(row = 0; row < 6; row++) {
			for(col = 0; col < 4; col++) {
				if(zones[row][col].getTile().equals("red") && zones[row][col+1].getTile().equals("red") && zones[row][col+2].getTile().equals("red") && zones[row][col+3].getTile().equals("red")) {
					colPlus4 = col+4;
					winner = "Game is over, red won at row:" + row + " col:" + col + "-" + colPlus4 + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(zones[row][col].getTile().equals("yellow") && zones[row][col+1].getTile().equals("yellow") && zones[row][col+2].getTile().equals("yellow") && zones[row][col+3].getTile().equals("yellow")) {
					colPlus4 = col+4;
					winner = "Game is over, yellow won at row:" + row + " col:" + col + "-" + colPlus4 + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		// check 4 in a column (vertical)
		for(col = 0; col < 7; col++) {
			for(row = 0; row < 3; row++) {
				if(zones[row][col].getTile().equals("red") && zones[row+1][col].getTile().equals("red") && zones[row+2][col].getTile().equals("red") && zones[row+3][col].getTile().equals("red")) {
					rowPlus4 = row+4;
					winner = "Game is over, red won at row:" + row + "-" + rowPlus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(zones[row][col].getTile().equals("yellow") && zones[row+1][col].getTile().equals("yellow") && zones[row+2][col].getTile().equals("yellow") && zones[row+3][col].getTile().equals("yellow")) {
					rowPlus4 = row+4;
					winner = "Game is over, yellow won at row:" + row + "-" + rowPlus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		// check 4 diagonal (left to right bottom to top : 45 degrees : positive slope)
		for(row = 0; row < 3; row++) {
			for(col = 0; col < 4; col++) {
				if(zones[row][col].getTile().equals("red") && zones[row+1][col+1].getTile().equals("red") && zones[row+2][col+2].getTile().equals("red") && zones[row+3][col+3].getTile().equals("red")) {
					winner = "Game is over, red won diagonally starting at row:" + row + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(zones[row][col].getTile().equals("yellow") && zones[row+1][col+1].getTile().equals("yellow") && zones[row+2][col+2].getTile().equals("yellow") && zones[row+3][col+3].getTile().equals("yellow")) {
					winner = "Game is over, yellow won diagonally starting at row:" + row + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		// check 4 diagonal (left to right top to bottom : -45 degrees : negative slope)
		for(row = 0; row < 3; row++) {
			for(col = 0; col < 4; col++) {
				if(zones[row+3][col].getTile().equals("red") && zones[row+2][col+1].getTile().equals("red") && zones[row+1][col+2].getTile().equals("red") && zones[row][col+3].getTile().equals("red")) {
					rowPlus4 = row+4;
					colPlus4 = col+4;
					winner = "Game is over, red won diagonally starting at row:" + rowPlus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(zones[row+3][col].getTile().equals("yellow") && zones[row+2][col+1].getTile().equals("yellow") && zones[row+1][col+2].getTile().equals("yellow") && zones[row][col+3].getTile().equals("yellow")) {
					rowPlus4 = row+4;
					colPlus4 = col+4;
					winner = "Game is over, yellow won diagonally starting at row:" + rowPlus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * pause the game (doesn't really do anything though)
	 */
	public void pause() {
		paused = !paused;
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
