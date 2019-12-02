package com.mygdx.ColesGames;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

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

import util.Constants;
import util.JsonParser;

/**
 * Connect Four game
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
	private Sprite spriteMoveYellow;
	private Sprite spriteMoveRed;
	private WebSocketClient cc;
	private int mousex;
	private int receiveMousex;
	private int mousey;
	private int receiveMousey;
	private boolean playerRedORYellow; // true is red, false if yellow
	private boolean isGameOver;
	private String winner = "empty";
	private boolean paused;
	private int difficulty;
	private int wait;					// used to stall the placement of the AI tile
	private final int WAITTIME = 30; 	// fraction of a second ex: 45/60 = .75 seconds or 60/60 = 1 second or 120/60 = 2 seconds
	
	private boolean animate;
	private int[] lowest;
	
	public ConnectFour() {
		create();
	}

	public ConnectFour(Game game, int diff) {
		this.game = game;
		difficulty = diff;
		System.out.println("new game " + diff + " as player number " + Constants.playerNumber);
		create();
		if (diff == -1) connect();
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
		spriteMoveYellow = new Sprite(yellowCircleImage);
		spriteMoveRed = new Sprite(redCircleImage);
		spriteYellow.setSize(82, 80);
		spriteRed.setSize(80, 80);
		spriteMoveYellow.setSize(82, 80);
		spriteMoveRed.setSize(80, 80);

		batch = new SpriteBatch();

		playerRedORYellow = true; // true is red, false is yellow
		isGameOver = false;
		paused = false;
		wait = 10000;
		animate = false;
		lowest = new int[2];
		
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
		
		if(wait < WAITTIME) wait++;
//		if(animate) playerRedORYellow = false;
//		else playerRedORYellow = true;
		
		// check if the game has been won by either player
		if(!isGameOver) isGameOver = checkGameOver(zones);
		
		// handle the user pressing and place their tile
		if (difficulty > 0 && Gdx.input.justTouched() && !isGameOver && playerRedORYellow && !animate) placeUserTile();
		
		
		// for difficulty = -1 is online PvP
		if(difficulty == -1 && !isGameOver && !animate) difficulty0nline();
		
		// for difficulty = 0 is PvP
		else if(difficulty == 0 && !isGameOver && !animate) difficulty0();
		
		// for difficulty = 1 easy (random AI)
		else if(difficulty == 1 && !playerRedORYellow && wait == WAITTIME && !isGameOver && !animate) difficulty1();
		
		// for difficulty = 2 (AI tries to win)
		else if(difficulty == 2 && !playerRedORYellow && wait == WAITTIME && !isGameOver && !animate) difficulty2();
		
		// for difficulty = 3 (AI tries to block player)
		else if(difficulty == 3 && !playerRedORYellow && wait == WAITTIME && !isGameOver && !animate) difficulty3();
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
						this.lowest = findLowestTile(new int[] {r, c});
						spriteMoveRed.setPosition(zones[5][c].getX(), 400);
//						System.out.println(spriteMoveRed.getX() + " " + spriteMoveRed.getY());
						animate = true;
//						System.out.println(r + " " + c);
					}
				}
			}
		}
	}
	
	private void connect() {
    	try {
    		
    		Draft[] drafts = { new Draft_6455() };
    		String w = "ws://coms-309-tc-1.misc.iastate.edu:8080/websocket/" + Constants.userID; // coms-309-tc-1.misc.iastate.edu
			cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
				@Override
				public void onMessage(String message) {
					System.out.println("NewMessage:" + message);
					if (!message.contains("User:") && (!playerRedORYellow && Constants.playerNumber == 1 || playerRedORYellow && Constants.playerNumber == 2)) {
						receiveMousex = Integer.parseInt(message.split(" ")[7-(3*Constants.playerNumber)]);
						receiveMousey = Integer.parseInt(message.split(" ")[8-(3*Constants.playerNumber)]);
					}
				}

				@Override
				public void onOpen(ServerHandshake handshake) {
					System.out.println("opOpen");
				}

				@Override
				public void onClose(int code, String reason, boolean remote) {
					System.out.println("onClose");
					try {
						JsonParser.sendHTML("removePlayerFromLobbies", "id=" + Constants.userID);
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}

				@Override
				public void onError(Exception e) {
//					cc.close();
//					connect();
					e.printStackTrace();
					System.out.println("onError: "+e.getMessage());
				}
			};
		} catch (URISyntaxException e) {
			System.out.println("fail");
			e.printStackTrace();
		}
		cc.connect();
    }
	
	
	/**
	 * OnlinePvP
	 */
	private void difficulty0nline(){
		if ((Gdx.input.justTouched() || receiveMousex > 0) && !isGameOver) {
			mousex = Gdx.input.getX();
			mousey = 520 - Gdx.input.getY();
			for (int r = 0; r < 6; r++) {
				for (int c = 0; c < 7; c++) {
					if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && playerRedORYellow && Constants.playerNumber == 1) {
						receiveMousex = 0;
						receiveMousey = 0;
						cc.send("UPDATEPOS:"+Constants.lobby+" "+mousex+" "+mousey);
						this.lowest = findLowestTile(new int[] {r, c});
						spriteMoveRed.setPosition(zones[5][c].getX(), 400);
//						System.out.println(spriteMoveRed.getX() + " " + spriteMoveRed.getY());
						animate = true;
					} else if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && !playerRedORYellow && Constants.playerNumber == 2) {
						receiveMousex = 0;
						receiveMousey = 0;
						cc.send("UPDATEPOS:"+Constants.lobby+" "+mousex+" "+mousey);
						this.lowest = findLowestTile(new int[] {r, c});
						spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
//						System.out.println(spriteMoveYellow.getX() + " " + spriteMoveYellow.getY());
						animate = true;
					}
					else if (zones[r][c].contains(receiveMousex, receiveMousey) && !zones[r][c].isActive() && !playerRedORYellow && Constants.playerNumber == 1) {
						this.lowest = findLowestTile(new int[] {r, c});
						spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
//						System.out.println(spriteMoveYellow.getX() + " " + spriteMoveYellow.getY());
						animate = true;
						receiveMousex = 0;
						receiveMousey = 0;
					}
					else if (zones[r][c].contains(receiveMousex, receiveMousey) && !zones[r][c].isActive() && playerRedORYellow && Constants.playerNumber == 2) {
						this.lowest = findLowestTile(new int[] {r, c});
						spriteMoveRed.setPosition(zones[5][c].getX(), 400);
//						System.out.println(spriteMoveRed.getX() + " " + spriteMoveRed.getY());
						animate = true;
						receiveMousex = 0;
						receiveMousey = 0;
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
						this.lowest = findLowestTile(new int[] {r, c});
						spriteMoveRed.setPosition(zones[5][c].getX(), 400);
//						System.out.println(spriteMoveRed.getX() + " " + spriteMoveRed.getY());
						animate = true;
					} else if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && !playerRedORYellow) {
						this.lowest = findLowestTile(new int[] {r, c});
						spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
//						System.out.println(spriteMoveYellow.getX() + " " + spriteMoveYellow.getY());
						animate = true;
					}
				}
			}
		}
	}
	
	/**
	 * logic for the AI to place a random tile
	 */
	private void difficulty1() {
		boolean tileFound = false;
		while(!tileFound) {
			int c = new Random().nextInt(7);
			if(!zones[5][c].isActive()) {
				lowest = findLowestTile(new int[] {5, c});
//				System.out.println("index: 5 " + c + " lowest: " + this.lowest[0] + " " + this.lowest[1]);
				spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
//				System.out.println(spriteMoveYellow.getX() + " " + spriteMoveYellow.getY());
				tileFound = true;
				animate = true;
			}
		}
	}
	
	/**
	 * logic for difficulty 2
	 */
	private void difficulty2() {
		int numOfYellow = 0;
		int[] arr = new int[43];
		// find the row and col of each yellow piece already placed
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
				setPlayerYellow(arr[i], arr[i+1]-1);
				isTilePlaced = true;
				break;
			}
			// check row -1 col -1
			else if(arr[i] > 0 && arr[i+1] > 0 && !zones[arr[i]-1][arr[i+1]-1].isActive()) {
				setPlayerYellow(arr[i]-1, arr[i+1]-1);
				isTilePlaced = true;
				break;
			}
			// check row -1, col + 1
			else if(arr[i] > 0 && arr[i+1] < 6 && !zones[arr[i]-1][arr[i+1]+1].isActive()) {
				setPlayerYellow(arr[i]-1, arr[i+1]+1);
				isTilePlaced = true;
				break;
			}
			// check to the left, col > 0, row > 0
			else if(arr[i] > 0 && arr[i+1] > 0 && zones[arr[i]-1][arr[i+1]-1].isActive() && !zones[arr[i]][arr[i+1]-1].isActive()) {
				setPlayerYellow(arr[i], arr[i+1]-1);
				isTilePlaced = true;
				break;
			}
			// check left diagonal, col > 0, row < 5
			else if(arr[i+1] > 0 && arr[i] < 5 && !zones[arr[i]+1][arr[i+1]-1].isActive() && zones[arr[i]][arr[i+1]-1].isActive()) {
				setPlayerYellow(arr[i]+1, arr[i+1]-1);
				isTilePlaced = true;
				break;
			}
			// check up, row < 5
			else if(arr[i] < 5 && !zones[arr[i]+1][arr[i+1]].isActive()) {
				setPlayerYellow(arr[i]+1, arr[i+1]);
				isTilePlaced = true;
				break;
			}
			// check right diagonal, col < 6, row < 5
			else if(arr[i] < 5 && arr[i+1] < 6 && !zones[arr[i]+1][arr[i+1]+1].isActive() && zones[arr[i]][arr[i+1]+1].isActive()) {
				setPlayerYellow(arr[i]+1, arr[i+1]+1);
				isTilePlaced = true;
				break;
			}
			// check to the right, col < 6, row == 0
			else if(arr[i+1] < 6 && arr[i] == 0 && !zones[arr[i]][arr[i+1]+1].isActive()) {
				setPlayerYellow(arr[i], arr[i+1]+1);
				isTilePlaced = true;
				break;
			}
			// check to the right, col < 6, row > 0
			else if(arr[i] > 0 && arr[i+1] < 6 && zones[arr[i]-1][arr[i+1]+1].isActive() && !zones[arr[i]][arr[i+1]+1].isActive()) {
				setPlayerYellow(arr[i], arr[i+1]+1);
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
	
	/**
	 * logic for difficulty 3
	 */
	private void difficulty3() {
		difficulty2();
	}
	
//	private String findDirectionToGo(int[] arr) {
////		int max = 0;
////		int rmax = -1;
////		int cmax = -1;
//		for(int i = 0; i < arr.length; i++) {
//			
//		}
//		
////		for(int i = 0; i < 6; i++) {
////			for(int j = 0; j < 7; j++) {
////				if(j > 0) {
////					
////				}
////			}
////		}
//		
//		return "";
//	}
	
	private void setPlayerYellow(int r, int c) {
		lowest = findLowestTile(new int[] {5, c});
//		System.out.println("index: 5 " + c + " lowest: " + this.lowest[0] + " " + this.lowest[1]);
		spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
//		System.out.println(spriteMoveYellow.getX() + " " + spriteMoveYellow.getY());
		animate = true;
	}
	
	/**
	 * draw the screen from render
	 */
	private void drawScreen() {
		batch.begin();
//		batch.draw(connectFourBoard, 0, 0);
		if(playerRedORYellow && !isGameOver) text.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond() + "\nPlayer: red", 20, 515);
		else if(!playerRedORYellow && !isGameOver) text.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " \nPlayer: yellow", 20, 515);
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
		if(animate) {
			if(playerRedORYellow) animateTile("red");
			else animateTile("yellow");
//			animateTile("yellow");
		}
		batch.draw(connectFourBoard, 0, 0);
		batch.end();
	}
	
	private void animateTile(String color) {
		if(color.equals("yellow")) {
			if(spriteMoveYellow.getY() < zones[lowest[0]][lowest[1]].getY()) {
				zones[lowest[0]][lowest[1]].setTile(color);
				zones[lowest[0]][lowest[1]].setActive(true);
				playerRedORYellow = true;
				animate = false;
			} else {
				spriteMoveYellow.setPosition(spriteMoveYellow.getX(), spriteMoveYellow.getY()-350*Gdx.graphics.getDeltaTime());
				spriteMoveYellow.draw(batch);
			}
		} else {
			if(spriteMoveRed.getY() < zones[lowest[0]][lowest[1]].getY()) {
				zones[lowest[0]][lowest[1]].setTile(color);
				zones[lowest[0]][lowest[1]].setActive(true);
				playerRedORYellow = false;
				animate = false;
				wait = 0;
			} else {
				spriteMoveRed.setPosition(spriteMoveRed.getX(), spriteMoveRed.getY()-350*Gdx.graphics.getDeltaTime());
				spriteMoveRed.draw(batch);
			}
		}
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
			game.setScreen(new SelectScreen(game));
		}
		if(Gdx.input.isKeyJustPressed(Keys.P)) {
			pause();
		}
		if(Gdx.input.isKeyJustPressed(Keys.R)) {
			System.out.println("restarted");
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
		
		int sum = 0;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if(zones[i][j].getTile().equals("yellow") || zones[i][j].getTile().equals("red")) sum++;
			}
		}
		if(sum == 42) {
			winner = "board is full\nPress R to replay or ESCAPE to return to main menu";
			return true;
		}
		
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
