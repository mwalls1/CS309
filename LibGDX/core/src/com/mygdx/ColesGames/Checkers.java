package com.mygdx.ColesGames;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.NoSuchElementException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

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
import util.JsonParser;

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
//	private boolean isGameOver;
	private int mousex;
	private int mousey;
	boolean doubleJump;	
	boolean received;
	private WebSocketClient cc;

	
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
		if (Constants.playerNumber == 0) isTurn = true; //Constants.playerNumber = 1;
		else {
			connect();
			isTurn = Constants.playerNumber == 1;
		}
		doubleJump = false;
		received = false;
		possibleMoves = new ArrayList<Sprite>();
		currentPiece = null;
		//Initialize Colors
		for (String color : colors) pieces.put(color, new ArrayList<CheckerPiece>());
		setBoard();
		
		batch = new SpriteBatch();
	}
	
	private void connect() {
    	try {
    		
    		Draft[] drafts = { new Draft_6455() };
    		String w = "ws://coms-309-tc-1.misc.iastate.edu:8080/websocket/" + Constants.userID; // coms-309-tc-1.misc.iastate.edu
			cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
				@Override
				public void onMessage(String message) {
					System.out.println("NewMessage:" + message);
					if (!message.contains("User:") && (!isTurn) && !message.contains("UPDATEPOS")) {
						mousex = Integer.parseInt(message.split(" ")[7-(3*Constants.playerNumber)]);
						mousey = Integer.parseInt(message.split(" ")[8-(3*Constants.playerNumber)]);
						received = true;
						System.out.println("Received: "+mousex + ". " + mousey);
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
		int moveAbility = 0;
//		if (Constants.playerNumber == 0) {
			if (isTurn && Constants.playerNumber == 0 || isTurn && Constants.playerNumber == 1 || !isTurn && Constants.playerNumber == 2) moveAbility = checkCanMove(pieces.get(colors[0]));
			else moveAbility = checkCanMove(pieces.get(colors[1]));
		
		if (moveAbility == 0) //If it is your turn (online) and there are no moves left
			System.out.println("Game is over");
		if (moveAbility == 2) doubleJump = true;
		
		if (Gdx.input.justTouched() && (isTurn || Constants.playerNumber == 0)) {
			mousex = (int) (((Gdx.input.getX()-3)/75)/2);
			mousey = (int) 7-(Gdx.input.getY()/75);
			System.out.println(mousex + " " + mousey );
		}
		if (mousex >= 0 && mousey >= 0 && mousex <= 3 & mousey <= 7) {
			//Check to see if piece is clicked on by its owner
			if((received && (gameBoardArr[mousey][mousex] == 1 || gameBoardArr[mousey][mousex] == 2))  || gameBoardArr[mousey][mousex] == Constants.playerNumber && Constants.playerNumber != 0 || (Constants.playerNumber == 0 && (gameBoardArr[mousey][mousex] == (isTurn ? 1: 2)))) {
				if (!received && Constants.playerNumber != 0) cc.send("UPDATEPOS:"+Constants.lobby+" "+mousex+" "+mousey);
				pieces.get("posMove").clear();
				System.out.println(isTurn ? 1: 2);
				currentPiece = getPieceByPosition(mousey, mousex).split(" ");
				CheckerPiece piece = pieces.get(currentPiece[0]).get(Integer.parseInt(currentPiece[1]));
				possibleMoves = piece.getPossibleMoveSprites();
				for (int[] arr : piece.getPosibbleMoves()) {
					pieces.get("posMove").add(new CheckerPiece(3,new int[] {arr[1]*2+arr[0]%2,arr[0]}));
				}
				System.out.println(possibleMoves.toString());
			}
			//Check to see if possible move is clicked on and moves piece
			else if(gameBoardArr[mousey][mousex] == 3) {
				if (!received && Constants.playerNumber != 0) cc.send("UPDATEPOS:"+Constants.lobby+" "+mousex+" "+mousey);
				//Checks to see if move was a jump
				CheckerPiece currentChecker = pieces.get(currentPiece[0]).get(Integer.parseInt(currentPiece[1]));
				if(Math.abs(currentChecker.getRow() - mousey) == 2) { //Case for if a piece will jump another
					boolean leftOrRight = ((mousex)-currentChecker.getCol()) == 1;
					int jumpedCol = 0;
					if (!leftOrRight && currentChecker.getRow()%2 == 0) jumpedCol = -1 + currentChecker.getCol();
					else if (leftOrRight && currentChecker.getRow()%2 == 1) jumpedCol = 1 + currentChecker.getCol();
					else jumpedCol = currentChecker.getCol();
					int jumpedRow = (mousey+currentChecker.getRow())/2;
					System.out.println("JumpedCol: "+jumpedCol+",JumpedRow: " + jumpedRow);
					String[] jumpedPiece = getPieceByPosition(jumpedRow,jumpedCol).split(" ");
					pieces.get(jumpedPiece[0]).remove(Integer.parseInt(jumpedPiece[1]));
					doubleJump = true;
				}
				currentChecker.updateLocaiton(new int[]{mousex*2+mousey%2,mousey});
				if (currentChecker.getRow() == 0 || currentChecker.getRow() == 7) currentChecker.kingMe();
				pieces.get("posMove").clear();
				if (!doubleJump || checkCanMove(new ArrayList<CheckerPiece>(Arrays.asList(currentChecker))) != 2) {
					isTurn = !isTurn;
					doubleJump = false;
				}
				else doubleJump = true;
			}
			mousex = -1;
			mousey = -1;
			received = false;
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
			cc.close();
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
		this.dispose();
		this.setScreen(new Checkers(game));
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
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
	
	public ArrayList<int[]> getPossibleMoves(int col, int row){
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		if (gameBoardArr[row][col] == 0) throw new NoSuchElementException();
		String[] pieceString = getPieceByPosition(row, col).split(" ");
		CheckerPiece curPiece = pieces.get(pieceString[0]).get(Integer.parseInt(pieceString[1]));
		if(curPiece.isKing() || curPiece.getValue() == 1) {
			try { //JUMP UP LEFT
				if(gameBoardArr[row+2][col-1] %3 == 0 && gameBoardArr[row+1][col-1+row%2] != gameBoardArr[row][col] && gameBoardArr[row+1][col-1+row%2] %3 != 0) possibleMoves.add(new int[] {row+2,col-1}); //If: up2&left is empty, up&left is not empty, up&left is not same
			} catch(IndexOutOfBoundsException e) {};
			try { //JUMP UP RIGHT
				if(gameBoardArr[row+2][col+1] %3 == 0 && gameBoardArr[row+1][col+row%2] != gameBoardArr[row][col] &&  gameBoardArr[row+1][col+row%2] %3 != 0) possibleMoves.add(new int[] {row+2,col+1});//If: up2&right is empty, up&right is not empty, up&right is not same
			} catch(IndexOutOfBoundsException e) {};
			try { //MOVE UP LEFT
				if(!doubleJump && gameBoardArr[row+1][col-1+row%2] %3 == 0) possibleMoves.add(new int[] {row+1,col-1+row%2}); //If: up&left is empty
			} catch(IndexOutOfBoundsException e) {};
			try { //MOVE UP RIGHT
				if(!doubleJump && gameBoardArr[row+1][col+row%2] %3 == 0) possibleMoves.add(new int[] {row+1,col+row%2});//If: up&right is empty
			} catch(IndexOutOfBoundsException e) {};
		} if(curPiece.isKing() || curPiece.getValue() == 2) {
			try { //JUMP DOWN LEFT
				if(gameBoardArr[row-2][col-1] %3 == 0 && gameBoardArr[row-1][col-1+row%2] != gameBoardArr[row][col] && gameBoardArr[row-1][col-1+row%2] %3 != 0) possibleMoves.add(new int[] {row-2,col-1}); //If: down2&left is empty, up&left is not empty, up&left is not same
			} catch(IndexOutOfBoundsException e) {};
			try { //JUMP DOWN RIGHT
				if(gameBoardArr[row-2][col+1] %3 == 0 && gameBoardArr[row-1][col+row%2] != gameBoardArr[row][col] &&  gameBoardArr[row-1][col+row%2] %3 != 0) possibleMoves.add(new int[] {row-2,col+1});//If: down2&right is empty, up&right is not empty, up&right is not same
			} catch(IndexOutOfBoundsException e) {};
			try { //MOVE DOWN LEFT
				if(!doubleJump && gameBoardArr[row-1][col-1+row%2] %3 == 0) possibleMoves.add(new int[] {row-1,col-1+row%2}); //If:downp&left is empty
			} catch(IndexOutOfBoundsException e) {};
			try { //MOVE DOWN RIGHT
				if(!doubleJump && gameBoardArr[row-1][col+row%2] %3 == 0) possibleMoves.add(new int[] {row-1,col+row%2});//If: down&right is empty
			} catch(IndexOutOfBoundsException e) {};
		}
		return possibleMoves;
	}
	
	private String getPieceByPosition(int row, int col) {
		for (String color : colors) {
			for (int i = 0; i < pieces.get(color).size(); i++) {
				if(pieces.get(color).get(i).getCol() == col && pieces.get(color).get(i).getRow() == row) return color + " " + i;
			}
		}
		throw new NullPointerException();
	}
	
	private int checkCanMove(ArrayList<CheckerPiece> checkers) {
		ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
		for (CheckerPiece c : checkers) {
			ArrayList<int[]> newMoves = getPossibleMoves(c.getCol(), c.getRow());
			possibleMoves.addAll(newMoves);
			for (int[] move : newMoves) if (Math.abs(move[0] - c.getRow()) == 2) return 2;	
		}
		if (possibleMoves.size() > 0) return 1;
		return 0;
	}
	
	
}
