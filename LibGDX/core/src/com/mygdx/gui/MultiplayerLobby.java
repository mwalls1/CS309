package com.mygdx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import util.Constants;
import util.JsonParser;

public class MultiplayerLobby extends Game implements Screen{
	private Skin skin;
	private Stage stage;
//	private Stage tableStage;
	private Game game;
    
    Integer lobbyNumber = 0;
    double gameCountDown = -1;
	long time = 0;
    ArrayList<Label> players;
    Label lobbyLabel;
    Label topVoteLabel;
    Label secondVoteLabel;
    Label thirdVoteLabel;
    Label fourthVoteLabel;  
    Label gameStartCD;
	
	public MultiplayerLobby(Game game)
	{
		this.game = game;
		create();
	}
	@Override
	public void show() {
	}
	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	     refreshNames();
	     startGame();
	}
	@Override
	public void resize(int width, int height) {
		create();
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}
	@Override
	public void create() {
        skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
        stage = new Stage();
        
    	Table table = new Table();
    	table.setWidth(Gdx.graphics.getWidth()/2);
        table.setHeight(Gdx.graphics.getHeight()*3/5);
        table.setPosition(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight()/5+5);
        Pixmap labelColor = new Pixmap((int)table.getWidth(), (int)table.getHeight(), Pixmap.Format.RGB888);
        labelColor.setColor(Color.DARK_GRAY);
        labelColor.fill();
        table.setBackground(new Image(new Texture(labelColor)).getDrawable());
        //Creates new players inside of table
        players = new ArrayList<Label>();
        for (int i = 0; i < 4; i++) {
        	Color white = new Color(255,255,0,1);
        	Label p = new Label("", skin, "default");
        	TextButton checkPlayer = new TextButton("i",skin,"default");
        	checkPlayer.setWidth(10);
        	checkPlayer.setHeight(5);
        	p.setColor(white);
        	p.setWidth(Gdx.graphics.getWidth()/2-checkPlayer.getWidth());
            players.add(p);
            table.row().colspan(3).expandX();
        	table.add(p).height(100).width(table.getWidth()/2-checkPlayer.getWidth()).align(Align.right);
        	table.add(checkPlayer).height(20).width(20).align(Align.right);
        }
        table.pad(10);
        stage.addActor(table);
        
        Table voteTable = new Table();
        voteTable.setWidth(Gdx.graphics.getWidth()/4);
        voteTable.setHeight(Gdx.graphics.getHeight()*3/5);
        voteTable.setPosition(Gdx.graphics.getWidth()*3/4, Gdx.graphics.getHeight()/5+5);
        //Adds labels for votes
        topVoteLabel = new Label("Top Vote",skin,"default");
        topVoteLabel.setFontScale(1.3f);
        secondVoteLabel = new Label("Second Vote",skin,"default");
        thirdVoteLabel = new Label("Third Vote",skin,"default");
        fourthVoteLabel = new Label("Fourth Vote",skin,"default");
        voteTable.add(topVoteLabel).row();
        voteTable.add(secondVoteLabel).row();
        voteTable.add(thirdVoteLabel).row();
        voteTable.add(fourthVoteLabel).row();
        voteTable.pad(10);
        stage.addActor(voteTable);
        
        
        lobbyLabel = new Label("", skin, "default");
        lobbyLabel.setAlignment(Align.center);
        lobbyLabel.setWidth(table.getWidth());
        lobbyLabel.setFontScale(2);
        lobbyLabel.setPosition(table.getX(), table.getY()+table.getHeight()-lobbyLabel.getHeight()*lobbyLabel.getFontScaleY());
        stage.addActor(lobbyLabel);
        
        ArrayList<TextButton> lobbies = new ArrayList<TextButton>();
        for (int i = 0; i < 18; i++) {
        	final Integer num = i+1;
        	TextButton newLobby = new TextButton("Lobby " + (i+1), skin, "default");
        	newLobby.setWidth(Gdx.graphics.getWidth() / 4);
            newLobby.setHeight(Gdx.graphics.getHeight() / 20);
            newLobby.setPosition(0, Gdx.graphics.getHeight()-Gdx.graphics.getHeight() / 20-(int)(newLobby.getHeight()*(i+2)));
            //When a lobby is clicked
            newLobby.addListener(new ClickListener(){
                @Override 
                public void clicked(InputEvent event, float x, float y){
                	MultiplayerLobby.this.lobbyNumber = num;
                	try {JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getLobbyByID?id="+MultiplayerLobby.this.lobbyNumber);} 
                	catch (Exception e1) {
            			try {JsonParser.sendHTML("newLobby", "id="+MultiplayerLobby.this.lobbyNumber);}catch (Exception e) {e.printStackTrace();}}
                	refreshNames();
                }});
            lobbies.add(newLobby);
            stage.addActor(lobbies.get(i));
        }
        
        //User Info Name and sign in/ sign out button at the top of the screen
        final Label userInfoLabel = new Label("HI GUYS: " + Constants.user, skin, "default");
        final TextButton userSignButton= new TextButton("singin/out",skin,"default");
        userSignButton.setHeight(Gdx.graphics.getHeight() / 40);
        userSignButton.setPosition(Gdx.graphics.getWidth()-userSignButton.getWidth(), Gdx.graphics.getHeight()-userSignButton.getHeight());
        if (Constants.userID == 0) userSignButton.setText("Sign In");
        else userSignButton.setText("Sign Out");
        userSignButton.addListener(new ClickListener(){ //When Sign in/Sing out is pressed in the top right
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	if(Constants.userID == 0) { //If no one is signed in
            		dispose();
                	game.setScreen(new UserInfoScreen(game));
            	}
            	else { //If a user is currently signed in
            		Constants.userID = 0;
            		Constants.user = "Temporary User";
            		create();
            	}
            }});
		userInfoLabel.setHeight(userSignButton.getHeight());
		userInfoLabel.setPosition(Gdx.graphics.getWidth()-userInfoLabel.getWidth()-userSignButton.getWidth(), Gdx.graphics.getHeight()-userInfoLabel.getHeight());
        stage.addActor(userInfoLabel);
        stage.addActor(userSignButton);
        
        final TextButton backButton = new TextButton("Back", skin, "default");
        backButton.setWidth(Gdx.graphics.getWidth() / 4);
        backButton.setHeight(Gdx.graphics.getHeight() / 20);
        backButton.setPosition(0, Gdx.graphics.getHeight()-backButton.getHeight());
        //When back button is clicked
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new LobbyScreen(game));
            }});
       
        stage.addActor(backButton);
        
        final TextButton joinLobby = new TextButton("Join Lobby", skin, "default");
        joinLobby.setWidth(Gdx.graphics.getWidth() / 4);
        joinLobby.setHeight(Gdx.graphics.getHeight() / 10);
        joinLobby.setPosition(Gdx.graphics.getWidth() / 4,Gdx.graphics.getHeight()/40);
        stage.addActor(joinLobby);
        final TextButton readyButton = new TextButton("Ready", skin, "default");
        readyButton.setWidth(Gdx.graphics.getWidth() / 4);
        readyButton.setHeight(joinLobby.getHeight());
        readyButton.setPosition(joinLobby.getX()+joinLobby.getWidth(), joinLobby.getY());
        stage.addActor(readyButton);
        //When the join lobby button is clicked
        joinLobby.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	String playerString = "0 0 0 0";
            	try { playerString = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getLobbyByID?id="+MultiplayerLobby.this.lobbyNumber);} catch (Exception e1) {e1.printStackTrace();}
            	String[] playerIds = playerString.split(" ");
            	for (int i = 0; i < 4; i++) {
            		if (playerIds[i].equals("0")){
            			try {JsonParser.sendHTML("updatePlayer", "id="+MultiplayerLobby.this.lobbyNumber+"&player="+(i+1)+"&playerId="+Constants.userID);} catch (Exception e) {e.printStackTrace();}
            			refreshNames();
            			break;
            		}
            	}
            }});
        readyButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
    			try {JsonParser.sendHTML("readyUp", "id="+MultiplayerLobby.this.lobbyNumber+"&playerId="+(Constants.userID));} catch (Exception e) {e.printStackTrace();}
            	refreshNames();
            }});
        
		final Table playerTable = new Table();
		int width = Gdx.graphics.getWidth()*8/10;
		int height = Gdx.graphics.getHeight()*8/10;
		playerTable.setHeight(height);
		playerTable.setWidth(width);
		playerTable.setPosition(width/10, height/10);
        int radius = 8;
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(50, 50, 50, 100);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(width - radius, radius, radius);
        pixmap.fillCircle(width - radius, height - radius, radius);
        pixmap.fillCircle(radius, height - radius, radius);
        pixmap.fillRectangle(0, radius, width, height - (radius * 2));
        pixmap.fillRectangle(radius, 0, width - (radius * 2), height);
		playerTable.setBackground(new Image(new Texture(pixmap)).getDrawable());
		playerTable.setVisible(false);
		for (int rowNum = 1; rowNum <= 5; rowNum++) {
			playerTable.row().colspan(5).expand();
			for (int colNum = 1; colNum <= 5; colNum++) {
				final TextButton g = new TextButton("Game "+colNum*rowNum,skin,"default"); //Will refrence database to see if game is valid and display name
				g.setColor(Color.CYAN);
				g.addListener(new ClickListener(){
		            @Override 
		            public void clicked(InputEvent event, float x, float y){
		            	playerTable.setVisible(false);
		            	for (Actor a : stage.getActors()) {
		        			a.setTouchable(Touchable.enabled);
		        		}
            			try {JsonParser.sendHTML("vote", "id="+MultiplayerLobby.this.lobbyNumber+"&playerId="+(Constants.userID)+"&vote="+g.getText().toString().split(" ")[1]);} catch (Exception e) {e.printStackTrace();}
            			refreshNames();
		            }});
				playerTable.add(g);
			}
		}
		playerTable.row();	
		TextButton closeGameSelect = new TextButton("close",skin,"default");
		closeGameSelect.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	playerTable.setVisible(false);
            	for (Actor a : stage.getActors()) {
        			a.setTouchable(Touchable.enabled);
        		}
            }});
		playerTable.add(closeGameSelect).height(25).width(100).align(Align.center).bottom();
        
        final TextButton selectGame = new TextButton("Select Game", skin, "default");
        selectGame.setWidth(Gdx.graphics.getWidth() / 4);
        selectGame.setHeight(Gdx.graphics.getHeight() / 20);
        selectGame.setPosition(Gdx.graphics.getWidth()*3/4, joinLobby.getY());
        selectGame.addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		for (Actor a : stage.getActors()) {
        			a.setTouchable(Touchable.disabled);
        		}
        		playerTable.setVisible(true);
        		playerTable.setTouchable(Touchable.enabled);
        	}});
        stage.addActor(selectGame);
        stage.addActor(playerTable);
        gameStartCD = new Label("Game Starting", skin,"default");
        gameStartCD.setVisible(false);
        gameStartCD.setColor(Color.GOLD);
        gameStartCD.setPosition(table.getX()+75, Gdx.graphics.getHeight()/2);
        gameStartCD.setFontScale(3);
        stage.addActor(gameStartCD);

        Gdx.input.setInputProcessor(stage);
	}
	
	private void refreshNames(){
		String playerString = "X X X X";
	    try { playerString = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getLobbyByID?id="+MultiplayerLobby.this.lobbyNumber);}catch (Exception e1) {e1.printStackTrace();}
    	String[] playerIds = playerString.split(" ");
	     for (int i =  0; i < MultiplayerLobby.this.players.size(); i++) {
    		String name = "";
    		MultiplayerLobby.this.players.get(i).setColor(255, 255, 255, 1);
    		if (!playerIds[i].equals("0")) {
    			try { name = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getUserById?id="+playerIds[i]);} catch (Exception e1) {e1.printStackTrace();}
    			MultiplayerLobby.this.players.get(i).setColor(0, 255, 0, 1);
    			if (name.contentEquals(Constants.user)) MultiplayerLobby.this.players.get(i).setColor(Color.TEAL);
    			Integer readyInt = -1;
    			try { readyInt = Integer.parseInt(JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getReadyStatus?id="+MultiplayerLobby.this.lobbyNumber));} catch (Exception e1) {e1.printStackTrace();}
    			if (readyInt%2 == 0) MultiplayerLobby.this.players.get(0).setColor(Color.GOLD);
    			if (readyInt%3 == 0) MultiplayerLobby.this.players.get(1).setColor(Color.GOLD);
    			if (readyInt%5 == 0) MultiplayerLobby.this.players.get(2).setColor(Color.GOLD);
    			if (readyInt%7 == 0) MultiplayerLobby.this.players.get(3).setColor(Color.GOLD);
    		} else name = "[NONE]";
    		MultiplayerLobby.this.players.get(i).setText(name);
	     }
	     lobbyLabel.setText("Lobby " + lobbyNumber);
	     if (lobbyNumber == 0) lobbyLabel.setText("Click on a lobby to view");
	   
	     String voteString = "0 0 0 0";
	     int[][] votes = {{0,0},{0,0},{0,0},{0,0}};
	     try { voteString = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getPlayerVotes?id="+MultiplayerLobby.this.lobbyNumber);}catch (Exception e1) {e1.printStackTrace();}
	     String[] voteStringArr = voteString.split(" ");
	     for (String vote : voteStringArr) {
		     for (int[] voteList : votes) { 
			    	 if (!vote.equals("0")) {
		    			 if (vote.equals(voteList[0]+"")) {
		    				 voteList[1]++;
		    				 break;
		    			 }
	    			 else if (voteList[0] == 0) {
	    				 voteList[0] = (int)Integer.parseInt(vote);
	    				 voteList[1]++;
	    				 break;
	    			 }
	    		 }
	    	 }
	     }
	     int[][] sortedVotes = new int[4][2];
	     for (int i = 0; i < 4; i++) {
	    	 int[] largest = {0,0};
	    	 for (int j = 0; j < 4; j++) {
	    		 if (votes[j][1] > largest[1]) {
	    			 largest = votes[j].clone();
	    			 votes[j][1] = 0;
	    		 }
	    	 }
	    	 sortedVotes[i] = largest.clone();
	     }
	     topVoteLabel.setText("Game "+sortedVotes[0][0]+" ("+sortedVotes[0][1]+")");
	     secondVoteLabel.setText("Game "+sortedVotes[1][0]+" ("+sortedVotes[1][1]+")");
	     thirdVoteLabel.setText("Game "+sortedVotes[2][0]+" ("+sortedVotes[2][1]+")");
	     fourthVoteLabel.setText("Game "+sortedVotes[3][0]+" ("+sortedVotes[3][1]+")");
	}
	
	private void startGame() {
		if (MultiplayerLobby.this.players.get(0).getColor().equals(Color.GOLD) && (!topVoteLabel.getText().contains("(0)"))) {
			gameStartCD.setVisible(true);
			if (gameCountDown == -1){
				gameCountDown = 5;
				time = System.currentTimeMillis();
			}
			if ((gameCountDown*10-(System.currentTimeMillis()-time)/100)/10 <= 0) try {JsonParser.sendHTML("readyDown", "id="+MultiplayerLobby.this.lobbyNumber);} catch (Exception e) {e.printStackTrace();}
			gameStartCD.setText("Game Starting in " + (gameCountDown*10-(System.currentTimeMillis()-time)/100)/10);
		}
		else {
			gameCountDown = -1;
			gameStartCD.setVisible(false);
		}
	}
}