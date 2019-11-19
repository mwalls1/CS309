package com.mygdx.gui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.ColesGames.ConnectFour;
import com.mygdx.ColesGames.Drop;
import com.mygdx.games.GameTest;
import com.mygdx.platformer.Map;
import com.mygdx.space.Space;
import com.mygdx.ticktacktoe.Runner;

import util.Constants;

public class SinglePlayerGameSelectScreen extends Game implements Screen{
	private Skin skin;
	private Stage stage;
	private Game game;
	
	
	public SinglePlayerGameSelectScreen(Game game)
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
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        final TextButton[] games = new TextButton[16];
        final String[] gameNames = new String[16];
        for (int i = 0; i<games.length; i++) gameNames[i] = "Coming soon!";
        gameNames[0] = "Dungeon Mayhem";
        gameNames[1] = "Space!";
        gameNames[2] = "Tic Tac Toe";
        gameNames[3] = "Drop";
        gameNames[4] = "Connect Four";
        gameNames[5] = "Platformer";
      
     
        for (int i = 0; i < games.length; i++)
        {
        	final TextButton button = new TextButton(gameNames[i], skin, "default");
        	button.setWidth(Gdx.graphics.getWidth()/7);
        	button.setHeight(Gdx.graphics.getWidth()/7);
        	button.setOrigin(0, 0);
        	button.setPosition(10, 400);
        	games[i] = button;
        }
        
        final TextButton backButton = new TextButton("Back", skin, "default");
        backButton.setWidth(Constants.BUTTON_WIDTH/4f);
        backButton.setHeight(Constants.BUTTON_HEIGHT);
        backButton.setPosition(Gdx.graphics.getWidth()/1.2f, Gdx.graphics.getHeight()/4f);
        stage.addActor(backButton);
        
        float x1 = Gdx.graphics.getWidth()/75f;
        float x2 = Gdx.graphics.getWidth()/5f;
        float x3 = Gdx.graphics.getWidth()/2.586f;
        float x4 = Gdx.graphics.getWidth()/1.744f;
        float y1 = Gdx.graphics.getHeight()/1.315f;
        float y2 = Gdx.graphics.getHeight()/1.9f;
        float y3 = Gdx.graphics.getHeight()/3.5f;
        float y4 = Gdx.graphics.getHeight()/3.5f;
        
        games[0].setPosition(x1, y1);
        games[1].setPosition(x2, y1);
        games[2].setPosition(x3, y1);
        games[3].setPosition(x4, y1);
        games[4].setPosition(x1, y2);
        games[5].setPosition(x2, y2);
        games[6].setPosition(x3, y2);
        games[7].setPosition(x4, y2);
        games[8].setPosition(x1, y3);
        games[9].setPosition(x2, y3);
        games[10].setPosition(x3, y3);
        games[11].setPosition(x4, y3);
        games[12].setPosition(x1, y4);
        games[13].setPosition(x2, y4);
        games[14].setPosition(x3, y4);
        games[15].setPosition(x4, y4);

        System.out.println(Gdx.graphics.getHeight());
        System.out.println(Gdx.graphics.getHeight()/1.315f);
        //125.01898
       for (int i = 0; i < 16; i++)
       {
    	   stage.addActor(games[i]);
       }
       games[0].addListener(new ClickListener(){
           @Override 
           public void clicked(InputEvent event, float x, float y){
           	//Do some stuff when clicked
           	backButton.setText("b");
           	game.setScreen(new GameTest(game));
           }
       });
       
        backButton.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
            	dispose();
            	game.setScreen(new LobbyScreen(game));
            }
        });
        
        games[1].addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		dispose();
        		game.setScreen(new Space(game));
        		
        	}
        });
        games[2].addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		dispose();
        		game.setScreen(new Runner(game));
        		
        	}
        });
        
        games[3].addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		dispose();
        		game.setScreen(new Drop(game));
        		
        	}
        });
        
        games[4].addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		dispose();
        		game.setScreen(new ConnectFour(game));
        		
        	}
        });
        
        games[5].addListener(new ClickListener(){
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		dispose();
        		game.setScreen(new Map(game));
        		
        	}
        });
      
        
        Gdx.input.setInputProcessor(stage);

	}

}
