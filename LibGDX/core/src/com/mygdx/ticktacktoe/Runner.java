package com.mygdx.ticktacktoe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.gui.MainScreen;

public class Runner extends Game implements Screen{
    
	private Game game;
	private Stage stage;
	private SpriteBatch batch;
	private Board board;
	private Player play1;
	private Player play2;
	private BitmapFont font;
	private int mouseX;
	private int mouseY;
	public Runner(Game game)
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
		if(!board.checkWin().equals("x") && !board.checkWin().equals("o"))//The game continues while no one has won.
		{
			mouseX = Gdx.input.getX();//gets the location of the click for zone assignment
			mouseY = Gdx.input.getY();
			Gdx.gl.glClearColor(1, 1, 1, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			board.render(batch, play1, play2, mouseX, mouseY);//renders the board with player booleans
			font.draw(batch, "X: "+mouseX + " Y: " + mouseY, 0, 650);//shows mouse x and y for test purposes
			batch.end();
		}
		else
		{
			Gdx.gl.glClearColor(1, 1, 1, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			batch.begin();
			board.render(batch, play1, play2, mouseX, mouseY);
			font.setColor(Color.BLACK);
			if(board.checkWin().equals("x"))//checks if x has won, and prints it
				font.draw(batch,"X's Win!", 0, 650);
			else
				font.draw(batch,"O's Win!", 0, 650);//checks if o has won and prints it
			batch.end();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.R))
		{
			System.out.println("Restart");//restarts the game
			create();
		}
	    if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))//closes the game when the user hits escape
	    {
	   	 dispose();
	     game.setScreen(new MainScreen(game));
	    }
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
		
	}
	

	
	@Override
	public void create() {
		board = new Board();
		font = new BitmapFont();
		Gdx.graphics.setWindowedMode(600, 700);
		Gdx.graphics.setResizable(false);
		batch = new SpriteBatch();
        stage = new Stage();
        play1 = new Player(true, "x");
        play2 = new Player(false, "o");
        Gdx.input.setInputProcessor(stage);
	}
}
