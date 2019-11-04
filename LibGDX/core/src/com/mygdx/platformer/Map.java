package com.mygdx.platformer;

import java.util.Scanner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.gui.MainScreen;

public class Map extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Stage stage;
	private BitmapFont font;
	Scanner scan;
	private Game game;
	private Player player;
	private int score = 0;
	private ShapeRenderer shape;
	private boolean gameOver = false;
	private float elapsed = 0;
	private TiledMap map;
	private TiledMapTileLayer terrain;
	private OrthogonalTiledMapRenderer renderer;
	public Map(Game game)
	{
		font = new BitmapFont();
		this.game = game;
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		camera = new OrthographicCamera(Gdx.graphics.getDisplayMode().width/4, Gdx.graphics.getDisplayMode().height/4);
		map = new TmxMapLoader().load("platformer.tmx");
		MapLayers mapLayers = map.getLayers();
		terrain = (TiledMapTileLayer) mapLayers.get("blockage");
		player = new Player(100, 100);
		create();
	}
	@Override
	public void create() {
		Gdx.graphics.setResizable(false);
		renderer = new OrthogonalTiledMapRenderer(map);
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.update(terrain,camera,batch);
		player.render(shape, camera);
		batch.end();
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
	     {
	    	 dispose();
	    	 game.setScreen(new MainScreen(game));
	     }
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		map.dispose();
		renderer.dispose();
		stage.dispose();
		
	}

}
