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
	
	private ConnectFourLogic logic;
	
	/**
	 * Creates a connect four game by calling the create method
	 */
	public ConnectFour() {
		create();
	}

	/**
	 * Creates a connect four game by calling the create method
	 * @param game
	 * 			given game to use
	 * @param diff
	 * 			-1 for multiplayer, 0 for PvP, 1 for random AI, 2 for medium AI, 3 for hard AI
	 */
	public ConnectFour(Game game, int diff) {
		this.game = game;
		difficulty = diff;
		System.out.println("new game " + diff + " as player number " + Constants.playerNumber);
		create();
		if (diff == -1) connect();
	}

	/**
	 * Initializes all necessary components for the game
	 * Set screen size, creates the zones, sprites and other private variables
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
		
		logic = new ConnectFourLogic();

		createZones();
	}

	/**
	 * Updates game status each frame
	 * Handles wait time for the AI, and user input from the mouse and keyboard
	 * Calls appropriate methods for each difficulty
	 */
	@Override
	public void render(float delta) {
		
		handleKeys();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawScreen();
		
		if(wait < WAITTIME) wait++;
		
		// check if the game has been won by either player
		if(!isGameOver) {
			isGameOver = logic.checkGameOver(zones, isGameOver);
			winner = logic.getWinner();
//			System.out.println(logic.getWinner());
		}
		
		// handle the user pressing and place their tile
		if (difficulty > 0 && Gdx.input.justTouched() && !isGameOver && playerRedORYellow && !animate) placeUserTile();
		
		
		// for difficulty = -1 is online PvP
		if(difficulty == -1 && !isGameOver /*&& !animate*/) difficulty0nline();
		
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
	 * Place a tile for the red player for AI modes
	 */
	private void placeUserTile() {
		if(playerRedORYellow) {
			mousex = Gdx.input.getX();
			mousey = 520 - Gdx.input.getY();
			for (int r = 0; r < 6; r++) {
				for (int c = 0; c < 7; c++) {
					if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && playerRedORYellow) {
						this.lowest = logic.findLowestTile(new int[] {r, c}, zones);
						spriteMoveRed.setPosition(zones[5][c].getX(), 400);
						animate = true;
					}
				}
			}
		}
	}
	
	/**
	 * connect to the web socket for online games
	 */
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
	 * Uses the already connected web socket to send data back and forth from player to player
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
						this.lowest = logic.findLowestTile(new int[] {r, c}, zones);
						spriteMoveRed.setPosition(zones[5][c].getX(), 400);
						animate = true;
					} else if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && !playerRedORYellow && Constants.playerNumber == 2) {
						receiveMousex = 0;
						receiveMousey = 0;
						cc.send("UPDATEPOS:"+Constants.lobby+" "+mousex+" "+mousey);
						this.lowest = logic.findLowestTile(new int[] {r, c}, zones);
						spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
						animate = true;
					}
					else if (zones[r][c].contains(receiveMousex, receiveMousey) && !zones[r][c].isActive() && !playerRedORYellow && Constants.playerNumber == 1) {
						this.lowest = logic.findLowestTile(new int[] {r, c}, zones);
						spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
						animate = true;
						receiveMousex = 0;
						receiveMousey = 0;
					}
					else if (zones[r][c].contains(receiveMousex, receiveMousey) && !zones[r][c].isActive() && playerRedORYellow && Constants.playerNumber == 2) {
						this.lowest = logic.findLowestTile(new int[] {r, c}, zones);
						spriteMoveRed.setPosition(zones[5][c].getX(), 400);
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
	 * used to handle input from the screen for player versus player mode
	 */
	private void difficulty0() {
		if (Gdx.input.justTouched() && !isGameOver) {
			mousex = Gdx.input.getX();
			mousey = 520 - Gdx.input.getY();
			for (int r = 0; r < 6; r++) {
				for (int c = 0; c < 7; c++) {
					if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && playerRedORYellow) {
						this.lowest = logic.findLowestTile(new int[] {r, c}, zones);
						spriteMoveRed.setPosition(zones[5][c].getX(), 400);
						animate = true;
					} else if (zones[r][c].contains(mousex, mousey) && !zones[r][c].isActive() && !playerRedORYellow) {
						this.lowest = logic.findLowestTile(new int[] {r, c}, zones);
						spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
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
				this.lowest = logic.findLowestTile(new int[] {5, c}, zones);
				spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
				tileFound = true;
				animate = true;
			}
		}
	}
	
	/**
	 * AI tries to place a tile off of an already placed tile to create a line
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
		findPossibleMoves();
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
			// check up, row < 5
			else if (arr[i] < 5 && !zones[arr[i] + 1][arr[i + 1]].isActive()) {
				setPlayerYellow(arr[i] + 1, arr[i + 1]);
				isTilePlaced = true;
				break;
			}
		}
		if (!isTilePlaced) {
			numOfYellow = 0;
		}
		// if 0 yellow tiles, place next to the red tile
		if(numOfYellow == 0) {
			// temp r and c
			int row = -1, col = -1;
			for(int r = 0; r < 6; r++) {
				for(int c = 0; c < 7; c++) {
					if(zones[r][c].getTile().equals("red")) {
						row = r;
						col = c;
						break;
					}
				}
				if(row != -1 && col != -1) break;
			}
			setPlayerYellow(row, col);
		}
	}
	
	private void findPossibleMoves() {
		int countOfTiles = 1;
		for(int r = 0; r < 6; r++) {
			for(int c = 0; c < 7; c++) {
				if(zones[r][c].getTile().equals("yellow")) {
					System.out.println("-----");
					System.out.println("--" + countOfTiles + "--");
					System.out.println("-----");
					// diagonal down left
					if(r == 1 && c > 0 && !zones[r-1][c-1].isActive()) System.out.println((r-1) + " " + (c-1));
					// diagonal down left case 2
					if(r > 1 && c > 0 && !zones[r-1][c-1].isActive() && zones[r-2][c-1].isActive()) System.out.println((r-1) + " " + (c-1));
					// left
					if(r == 0 && c > 0 && !zones[r][c-1].isActive()) System.out.println(r + " " + (c-1));
					// left case 2
					if(r > 0 && c > 0 && !zones[r][c-1].isActive() && zones[r-1][c-1].isActive()) System.out.println(r + " " + (c-1));
					// diagonal up left
					if(r < 5 && c > 0 && !zones[r+1][c-1].isActive() && zones[r][c-1].isActive()) System.out.println((r+1) + " " + (c-1));
					// up
					if(r < 5 && c > 0 && !zones[r+1][c].isActive()) System.out.println((r+1) + " " + c);
					// diagonal up right
					if(r < 5 && c < 6 && !zones[r+1][c+1].isActive() && zones[r][c+1].isActive()) System.out.println((r+1) + " " + (c+1));
					// right
					if(r == 0 && c < 6 && !zones[r][c+1].isActive() && zones[r][c+1].isActive()) System.out.println(r + " " + (c+1));
					// right case 2
					if(r > 0 && c < 6 && !zones[r][c+1].isActive() && zones[r-1][c+1].isActive()) System.out.println(r + " " + (c+1));
					// diagonal down right
					if(r == 1 && c < 6 && !zones[r-1][c+1].isActive()) System.out.println((r-1) + " " + (c+1));
					// diagonal down right case 2
					if(r > 1 && c < 6 && !zones[r-1][c+1].isActive() && zones[r-2][c+1].isActive()) System.out.println((r-1) + " " + (c+1));
					countOfTiles++;
				}
			}
		}
	}
	
	/**
	 * Not currently implemented
	 * The AI would be able to keep track of previous moves and place a tile off of that to make a line to win
	 */
	private void difficulty3() {
		difficulty2();
	}
	
	/**
	 * used to set the status of the AI tile, finds the lowest tile in the given column
	 * @param r
	 * @param c
	 */
	private void setPlayerYellow(int r, int c) {
		lowest = logic.findLowestTile(new int[] {5, c}, zones);
		spriteMoveYellow.setPosition(zones[5][c].getX(), 400);
		animate = true;
	}
	
	/**
	 * draw the screen from render
	 */
	private void drawScreen() {
		batch.begin();
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
		}
		batch.draw(connectFourBoard, 0, 0);
		batch.end();
	}
	
	/**
	 * actually animate the tile down the screen
	 * @param color
	 * 			given color of the tile to be moved down the screen, red or yellow
	 */
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
	 * creates the zones on screen to click
	 */
	public void createZones() {
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
	 * Handles keyboard input to change game status
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
