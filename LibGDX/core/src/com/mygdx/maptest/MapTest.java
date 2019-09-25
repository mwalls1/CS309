package com.mygdx.maptest;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.Viewport;

import util.Constants;

public class MapTest extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Skin skin;
	private Stage stage;
	private Viewport viewport;
	private TextureAtlas atlas;
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Game game;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	private AtlasTmxMapLoader test;
	private TiledMapTileLayer terrain;
	private TiledMapTileLayer walls;
	private MapLayers mapLayers;
	
	public MapTest(Game game)
	{
		this.game = game;
		camera = new OrthographicCamera(1920/2,1080/2);
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
		 Gdx.gl.glClearColor(0, 0, 0, 0);
	     Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     camera.update();

	     renderer.setView(camera);
	     renderer.render();
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
		Gdx.graphics.setResizable(false);
		font = new BitmapFont();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        map = new TmxMapLoader().load("testMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        mapLayers = map.getLayers();
        terrain = (TiledMapTileLayer)mapLayers.get("terrain");
        walls = (TiledMapTileLayer)mapLayers.get("walls");
        Gdx.input.setInputProcessor(stage);

	}

}