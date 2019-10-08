package com.mygdx.gui;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
		// TODO Auto-generated method stub
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        
        
        
        players = new ArrayList<Label>();
        for (int i = 0; i < 4; i++) {
        	Label p = new Label("Player " + i, skin, "default");
        	p.setWidth(Gdx.graphics.getWidth()-Constants.BUTTON_WIDTH-3);
            p.setHeight(Constants.BUTTON_HEIGHT);
            p.setPosition(Constants.BUTTON_WIDTH+3, Gdx.graphics.getHeight()-p.getHeight()*(i+1));
            players.add(p);
            stage.addActor(players.get(i));
        }
        
        ArrayList<TextButton> lobbies = new ArrayList<TextButton>();
        for (int i = 0; i < 5; i++) {
        	final Integer num = i;
        	TextButton newLobby = new TextButton("Lobby " + i, skin, "default");
        	newLobby.setWidth(Constants.BUTTON_WIDTH);
            newLobby.setHeight(Constants.BUTTON_HEIGHT);
            newLobby.setPosition(0, Gdx.graphics.getHeight()-Constants.BUTTON_HEIGHT-(int)(newLobby.getHeight()*(i+2)*1.2));
            newLobby.addListener(new ClickListener(){
                @Override 
                public void clicked(InputEvent event, float x, float y){
                	MultiplayerLobby.this.lobbyNumber = num;
                	String playerString = "";
                	try { playerString = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getLobbyByID?id=1");} catch (Exception e1) {e1.printStackTrace();}
                	String[] playerIds = playerString.split(" ");
                	for (int i =  0; i < MultiplayerLobby.this.players.size(); i++) {
                		MultiplayerLobby.this.players.get(i).setText(playerIds[i]);
                	}
                }});
            lobbies.add(newLobby);
            stage.addActor(lobbies.get(i));
        }
        
        final TextButton backButton = new TextButton("Back", skin, "default");

        backButton.setWidth(Constants.BUTTON_WIDTH);
        
        backButton.setHeight(Constants.BUTTON_HEIGHT);
        
        backButton.setPosition(0, Gdx.graphics.getHeight()-backButton.getHeight());
        
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new LobbyScreen(game));
            }
        });
               
        stage.addActor(backButton);
        
        Gdx.input.setInputProcessor(stage);

	}
	
}
