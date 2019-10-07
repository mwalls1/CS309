package com.mygdx.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
<<<<<<< LibGDX/core/src/com/mygdx/gui/MainScreen.java

=======
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.games.*;
>>>>>>> LibGDX/core/src/com/mygdx/gui/MainScreen.java
import util.Constants;

public class MainScreen extends Game implements Screen{
	private Skin skin;
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
<<<<<<< LibGDX/core/src/com/mygdx/gui/MainScreen.java
		 Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Don't know why but you need this
	     stage.act(); //Starts button functionality
	     stage.draw(); //Draws buttons
=======
		  Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	     if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
	     {
	    	 Gdx.app.exit();
	     }
>>>>>>> LibGDX/core/src/com/mygdx/gui/MainScreen.java
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
      
        //Three lines below this set the widths of buttons using the constant widths
        playButton.setWidth(Constants.BUTTON_WIDTH); 
        leaderboardButton.setWidth(Constants.BUTTON_WIDTH);
        optionsButton.setWidth(Constants.BUTTON_WIDTH);
        userInfoButton.setWidth(Constants.BUTTON_WIDTH);
        
        //Set the heights using constant height
        playButton.setHeight(Constants.BUTTON_HEIGHT);
        leaderboardButton.setHeight(Constants.BUTTON_HEIGHT);
        optionsButton.setHeight(Constants.BUTTON_HEIGHT);
        userInfoButton.setHeight(Constants.BUTTON_HEIGHT);
        
        //Sets positions for buttons
        playButton.setPosition(Gdx.graphics.getWidth() /2 - playButton.getWidth()/2, Gdx.graphics.getHeight()/2);
        leaderboardButton.setPosition(Gdx.graphics.getWidth() /2 - leaderboardButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET);
        optionsButton.setPosition(Gdx.graphics.getWidth() /2 - optionsButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET*2);
        userInfoButton.setPosition(Gdx.graphics.getWidth() /2 - userInfoButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET*3);
        
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
            	//Do some stuff when clicked
            	dispose();
            	game.setScreen(new UserInfoScreen(game));
            	
            }
        });
        
        //Adds all buttons to stage to be rendered
        stage.addActor(playButton);
        stage.addActor(leaderboardButton);
        stage.addActor(optionsButton);
        stage.addActor(userInfoButton);
        
        Gdx.input.setInputProcessor(stage);

	}

}
