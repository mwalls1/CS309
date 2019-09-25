package com.mygdx.games;

import com.mygdx.gui.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import util.Constants;

public class GameTest extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Skin skin;
	private Stage stage;
	private Viewport viewport;
	private TextureAtlas atlas;
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Game game;
	private float offset = Gdx.graphics.getHeight() / 10;
	private int daWayx;
	private int daWayy;
	private Texture dawayTexture;
	private Texture left;
	private Texture right;
	private Texture background;
	private CharSequence str;
	private Sound theway;
	private Player player;
	private ShapeRenderer shape;
	private int height;
	private int width;
	private Sprite knuckles;
	private Animation<TextureRegion> runLeft;
	private Animation<TextureRegion> runRight;
	private Animation<TextureRegion> idleLeft;
	private Animation<TextureRegion> idleRight;
	private TextureAtlas rLeft;
	private TextureAtlas rRight;
	private TextureAtlas iLeft;
	private TextureAtlas iRight;
	private float elapsed = 0;
	private TiledMap map;
	private TiledMapTileLayer terrain;
	private TiledMapTileLayer walls;
	private TiledMapTileLayer collision;
	private MapObjects objs;
	private OrthogonalTiledMapRenderer renderer;
	public GameTest(Game game)
	{
		this.game = game;
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(360, 202);
		map = new TmxMapLoader().load("dungeon.tmx");
		MapLayers mapLayers = map.getLayers();
		terrain = (TiledMapTileLayer) mapLayers.get("floor");
		walls = (TiledMapTileLayer) mapLayers.get("walls");
		collision = (TiledMapTileLayer) mapLayers.get("blockage");
		rLeft = new TextureAtlas(Gdx.files.internal("runLeft.atlas"));
		rRight = new TextureAtlas(Gdx.files.internal("runRight.atlas"));
		iRight = new TextureAtlas(Gdx.files.internal("idleRight.atlas"));
		iLeft = new TextureAtlas(Gdx.files.internal("idleLeft.atlas"));
		runLeft = new Animation<TextureRegion>(1/10f, rLeft.getRegions());
		runRight = new Animation<TextureRegion>(1/10f, rRight.getRegions());
		idleLeft = new Animation<TextureRegion>(1/5f, iLeft.getRegions());
		idleRight = new Animation<TextureRegion>(1/5f, iRight.getRegions());
		player = new Player();
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
		 Gdx.gl.glClearColor(0,0,0,0);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	     stage.act();
	     stage.draw();
	     camera.update();
		 renderer.setView(camera);
		 renderer.render();
		 batch.setProjectionMatrix(camera.combined);
		 batch.begin();
		if(!player.isMoving)
		 {
			 if(player.direction==0)
			 {
				 batch.draw(idleLeft.getKeyFrame(elapsed,true), camera.position.x, camera.position.y);
			 }
			 else
			 {
				 batch.draw(idleRight.getKeyFrame(elapsed,true), camera.position.x, camera.position.y);
			 }
		 }
		 if(player.isMoving)
		 {
			 if(player.direction==0)
				 batch.draw(runLeft.getKeyFrame(elapsed,true), camera.position.x, camera.position.y);
			 else
				 batch.draw(runRight.getKeyFrame(elapsed,true), camera.position.x, camera.position.y);
		 }
		 batch.end();
		 elapsed += Gdx.graphics.getDeltaTime();
	     player.update(collision, knuckles);
	     player.render(shape, camera);
	     if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
	     {
	    	 game.setScreen(new MainScreen(game));
	     }
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
		{
			theway.stop();
			theway.play();
		}
	}

	@Override
	public void resize(int width, int height) {
		offset = Gdx.graphics.getHeight() / 10;
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
		renderer = new OrthogonalTiledMapRenderer(map);
		font = new BitmapFont();
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        theway = Gdx.audio.newSound(Gdx.files.internal("douknow.mp3"));
        Gdx.input.setInputProcessor(stage);

	}

}
