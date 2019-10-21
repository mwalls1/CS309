package com.mygdx.gui;

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

public class LobbyScreen extends Game implements Screen{
	private Skin skin;
	private Stage stage;
	private Game game;
	
	
	public LobbyScreen(Game game)
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
        final TextButton privateGameButton = new TextButton("Private Game", skin, "default");
        final TextButton publicGameButton = new TextButton("Public Game", skin, "default");
        final TextButton singlePlayerButton = new TextButton("Single Player", skin, "default");
        
        
        
        backButton.setWidth(Gdx.graphics.getWidth() / 4);
        privateGameButton.setWidth(Gdx.graphics.getWidth() / 3); 
        publicGameButton.setWidth(privateGameButton.getWidth());
        singlePlayerButton.setWidth(privateGameButton.getWidth());
        
        backButton.setHeight(Gdx.graphics.getHeight() / 20);
        privateGameButton.setHeight(Gdx.graphics.getHeight() / 15);
        publicGameButton.setHeight(privateGameButton.getHeight());
        singlePlayerButton.setHeight(privateGameButton.getHeight());
        
        backButton.setPosition(0, Gdx.graphics.getHeight()-backButton.getHeight());
        privateGameButton.setPosition(Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/3);
        publicGameButton.setPosition(privateGameButton.getX(),privateGameButton.getY() - publicGameButton.getHeight());
        singlePlayerButton.setPosition(publicGameButton.getX(),publicGameButton.getY() - singlePlayerButton.getHeight());
        
        //When back button is clicked
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new MainScreen(game));
            }});
        
        privateGameButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	privateGameButton.setText("b");
            	
            }
        });
        
        publicGameButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	game.setScreen(new MultiplayerLobby(game));
            }
        });
        
        singlePlayerButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new  SinglePlayerGameSelectScreen(game));
            }
        });
        
        stage.addActor(backButton);
        stage.addActor(privateGameButton);
        stage.addActor(publicGameButton);
        stage.addActor(singlePlayerButton);

        Gdx.input.setInputProcessor(stage);

	}

}
