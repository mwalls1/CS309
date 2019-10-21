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

public class OptionsScreen extends Game implements Screen{
	private Skin skin;
	private Stage stage;
	private Game game;
	
	
	public OptionsScreen(Game game)
	{
		this.game = game;
		create();
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		 Gdx.gl.glClearColor(Constants.red, Constants.blue, Constants.green, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
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
        skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
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
        final TextButton colorButton = new TextButton("Change Background", skin, "default");
        final TextButton screenMode = new TextButton("Change Screenmode", skin, "default");
        
        backButton.setWidth(Constants.BUTTON_WIDTH);
        colorButton.setWidth(Constants.BUTTON_WIDTH);
        backButton.setHeight(Constants.BUTTON_HEIGHT);
        colorButton.setHeight(Constants.BUTTON_HEIGHT);
        backButton.setPosition(Gdx.graphics.getWidth() /2 - backButton.getWidth()/2, Gdx.graphics.getHeight()/2);
       colorButton.setPosition(Gdx.graphics.getWidth() /2 - backButton.getWidth()/2, Gdx.graphics.getHeight()/2 - Constants.BUTTON_OFFSET);
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	//Do some stuff when clicked
            	dispose();
            	game.setScreen(new MainScreen(game));
            }
        });
        colorButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	Constants.red = (float)Math.random();
            	Constants.blue = (float)Math.random();
            	Constants.green = (float)Math.random();
            	Constants.alpha = (float)Math.random();
            	create();
            }
        });
        screenMode.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	
            }
        });        
       
        
        stage.addActor(backButton);
        stage.addActor(colorButton);

        
        Gdx.input.setInputProcessor(stage);

	}

}
