package com.mygdx.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import util.Constants;

public class MainScreen extends Game implements Screen{
	private static Skin skin;
	private Stage stage;
	private Game game;
	
	/**
	 * Main screen with play, options and leaderboard buttons
	 * @param game game object, we use Game's setScreen() method to switch between different screens
	 */

	public MainScreen(Game game)
	{
		this.game = game;
		create();
			}
	/**
	 * From screen interface, called when this screen is set
	 */
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage); //Uses the stage, gets input from cursor/clicks
		
	}
	
	/**
	 * Runs every frame, draws updated stage and sets background color
	 */
	public void render(float delta) {
		// TODO Auto-generated method stub
		 Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Don't know why but you need this
	     stage.act(); //Starts button functionality
	     stage.draw(); //Draws buttons

		  Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	     if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
	     {
	    	 Gdx.app.exit();
	     }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		create();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	/**
	 * Clears screen to free memory
	 */
	public void dispose() {
		stage.dispose();
		
	}
	

	
	/**
	 * Describes button functionality and position
	 */
	public void create() {
		// TODO Auto-generated method stub
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        final TextButton playButton = new TextButton("Play", skin, "default"); //Creates button with label "play"
        final TextButton leaderboardButton = new TextButton("Leaderboards", skin, "default"); //Creates button with label "leaderboards"
        final TextButton optionsButton = new TextButton("Options", skin, "default"); //Creates button with label "options"
        final TextButton userInfoButton = new TextButton("User Info", skin, "default");//Creates button with label "User Info"
        final TextButton quitGameButton = new TextButton("Exit Game", skin, "default");
        
      
        //Three lines below this set the widths of buttons using the constant widths
        playButton.setWidth(Gdx.graphics.getWidth() / 3); 
        leaderboardButton.setWidth(playButton.getWidth());
        optionsButton.setWidth(playButton.getWidth());
        userInfoButton.setWidth(playButton.getWidth());
        quitGameButton.setWidth(playButton.getWidth());
        
        //Set the heights using constant height
        playButton.setHeight(Gdx.graphics.getHeight() / 15);
        leaderboardButton.setHeight(playButton.getHeight());
        optionsButton.setHeight(playButton.getHeight());
        userInfoButton.setHeight(playButton.getHeight());
        quitGameButton.setHeight(playButton.getHeight());
        
        //Sets positions for buttons
        playButton.setPosition(Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight()/3);
        leaderboardButton.setPosition(playButton.getX(),playButton.getY() - leaderboardButton.getHeight());
        optionsButton.setPosition(playButton.getX(),leaderboardButton.getY() - optionsButton.getHeight());
        userInfoButton.setPosition(playButton.getX(),optionsButton.getY() - userInfoButton.getHeight());
        quitGameButton.setPosition(playButton.getX(),userInfoButton.getY() - quitGameButton.getHeight());
        
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
        
        playButton.addListener(new ClickListener(){ //This tells button what to do when clicked
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new LobbyScreen(game)); //Switch over to lobby screen
            }
        });
        
        leaderboardButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new LeaderboardScreen(game)); //Switch over to leaderboard screen
            }
        });
        
        optionsButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Do some stuff when clicked
            	dispose();
            	game.setScreen(new OptionsScreen(game));
            	
            }
        });
        
        userInfoButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new UserInfoScreen(game));
            	
            }
        });
        
        quitGameButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	Gdx.app.exit();
            }
        });
        
        //Adds all buttons to stage to be rendered
        stage.addActor(playButton);
        stage.addActor(leaderboardButton);
        stage.addActor(optionsButton);
        stage.addActor(userInfoButton);
        stage.addActor(quitGameButton);
        
        Gdx.input.setInputProcessor(stage);

	}

}
