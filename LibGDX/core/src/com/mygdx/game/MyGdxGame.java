package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.gui.*;

public class MyGdxGame extends ApplicationAdapter {
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;
	
	@Override 
	public void create()
	{
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		paused = false;
		
	}
	
	@Override
	public void render()
	{
		if(!paused)
		{
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		
			Gdx.gl.glClearColor(0.392f, 0.584f, 0.92f, 1.0f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			worldRenderer.render();
	
	}
	
	@Override
	public void resize(int width, int height)
	{
		worldRenderer.resisze(width, height);
	}
	
	@Override
	public void pause()
	{
		paused = true;
	}
	
	@Override
	public void resume()
	{
		paused = false;
	}
	
	@Override
	public void dispose()
	{
		worldRenderer.dispose();
	}
	
	

}
