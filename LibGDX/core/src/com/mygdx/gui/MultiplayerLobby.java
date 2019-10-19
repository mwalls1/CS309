package com.mygdx.gui;

import java.util.ArrayList;
import java.util.Arrays;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import util.Constants;
import util.JsonParser;

public class MultiplayerLobby extends Game implements Screen{
	private Skin skin;
	private Stage stage;
	private Game game;
    
    Integer lobbyNumber = 0;
    ArrayList<Label> players;
	
	
	public MultiplayerLobby(Game game)
	{
		this.game = game;
		create();
	}
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		 Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		create();
		System.out.println(Gdx.graphics.getWidth());
		System.out.println(Gdx.graphics.getWidth() / 3);
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
                
        Label lobbyLabel = new Label("", skin, "default");
        lobbyLabel.setAlignment(Align.center);
    	Table table = new Table();
    	table.setWidth(Gdx.graphics.getWidth()/2);
        table.setHeight(Gdx.graphics.getHeight()*3/5);
        table.setPosition(Gdx.graphics.getWidth() / 4 +3, Gdx.graphics.getHeight()/5+5);
        Pixmap labelColor = new Pixmap((int)table.getWidth(), (int)table.getHeight(), Pixmap.Format.RGB888);
        labelColor.setColor(Color.DARK_GRAY);
        labelColor.fill();
        table.setBackground(new Image(new Texture(labelColor)).getDrawable());
        //Creates new players inside of table
        players = new ArrayList<Label>();
        for (int i = 0; i < 4; i++) {
        	Label.LabelStyle style = new Label.LabelStyle();
        	Color white = new Color(255,255,0,1);
        	Label p = new Label("Player " + i, skin, "default");
//        	p.setAlignment(Align.center);
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
                		System.out.println("Making new lobby "+MultiplayerLobby.this.lobbyNumber);
            			try {JsonParser.sendHTML("newLobby", "id="+MultiplayerLobby.this.lobbyNumber);}catch (Exception e) {e.printStackTrace();}}
                	refreshNames();
                }});
            lobbies.add(newLobby);
            stage.addActor(lobbies.get(i));
        }

        final Label userInfoLabel = new Label("HI GUYS: " + Constants.user, skin, "default");
        userInfoLabel.setWidth(Gdx.graphics.getWidth() / 3);
		userInfoLabel.setHeight(Gdx.graphics.getHeight() / 20);
		userInfoLabel.setPosition(Gdx.graphics.getWidth()-userInfoLabel.getWidth(), Gdx.graphics.getHeight()-userInfoLabel.getHeight());
		
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
        joinLobby.setWidth(Gdx.graphics.getWidth() / 3);
        joinLobby.setHeight(Gdx.graphics.getHeight() / 10);
        joinLobby.setPosition(Gdx.graphics.getWidth() / 4+3,Gdx.graphics.getHeight()/40);
        //When the join lobby button is clicked
        joinLobby.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	String playerString = "0 0 0 0";
            	System.out.println(MultiplayerLobby.this.lobbyNumber);
            	try { playerString = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getLobbyByID?id="+MultiplayerLobby.this.lobbyNumber);} catch (Exception e1) {e1.printStackTrace();}
            	String[] playerIds = playerString.split(" ");
            	for (int i = 0; i < 4; i++) {
            		System.out.println(Arrays.toString(playerIds));
            		System.out.println(playerIds[i]);
            		if (playerIds[i].equals("0")){
            			try {JsonParser.sendHTML("updatePlayer", "id="+MultiplayerLobby.this.lobbyNumber+"&player="+(i+1)+"&playerId="+Constants.userID);} catch (Exception e) {e.printStackTrace();}
            			refreshNames();
            			break;
            		}
            	}
            }});
        stage.addActor(joinLobby);
        stage.addActor(userInfoLabel);

        
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
    			if (name.contentEquals(Constants.user)) MultiplayerLobby.this.players.get(i).setColor(0, 0, 255, 1);
    		} else name = "[NONE]";
    		MultiplayerLobby.this.players.get(i).setText(name);
	     }
	}
}
