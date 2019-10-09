package com.mygdx.games;

import com.mygdx.gui.*;
import com.mygdx.objects.score;

import java.util.ArrayList;
import java.util.Scanner;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import util.Constants;
import util.JsonParser;

public class GameTest extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Skin skin;
	private Stage stage;
	private Viewport viewport;
	private TextureAtlas atlas;
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	Scanner scan;
	private Game game;
	private float offset = Gdx.graphics.getHeight() / 10;
	private int daWayx;
	private int daWayy;
	private Texture blade;
	private Texture left;
	private Texture right;
	private Texture background;
	private CharSequence str;
	private Sound theway;
	private Player player;
	private Player player2;
	private ShapeRenderer shape;
	private int height;
	private int width;
	private Sprite knuckles;
	private float elapsed = 0;
	private TiledMap map;
	private TiledMapTileLayer terrain;
	private TiledMapTileLayer walls;
	private TiledMapTileLayer collision;
	private OrthogonalTiledMapRenderer renderer;
	private Hazard hazard;
	public ArrayList<Knife> shots = new ArrayList<Knife>();
	public ArrayList<Bolt> enemyShots = new ArrayList<Bolt>();
	public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public ArrayList<Wizard> wizards = new ArrayList<Wizard>();
	public serverThread thread = new serverThread();
	public GameTest(Game game)
	{
		this.game = game;
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera = new OrthographicCamera(Gdx.graphics.getDisplayMode().width/4, Gdx.graphics.getDisplayMode().height/4);
		map = new TmxMapLoader().load("assets/dungeon3.tmx");
		MapLayers mapLayers = map.getLayers();
		terrain = (TiledMapTileLayer) mapLayers.get("floor");
		walls = (TiledMapTileLayer) mapLayers.get("walls");
		collision = (TiledMapTileLayer) mapLayers.get("blockage");
		player = new Player(22*16,50);
		player2 = new Player(22*16,100);
		blade = new Texture(Gdx.files.internal("assets/blade.png"));
		Texture zom = new Texture(Gdx.files.internal("assets/zombie_idle_anim_f0.png"));
		Texture wiz = new Texture(Gdx.files.internal("assets/wizard.png"));
		hazard = new Hazard(blade,14*16-5,12*16, camera);
		zombies.add(new Zombie(zom, 30, 30, camera));
		wizards.add(new Wizard(wiz, 300, 300, camera));
		shape = new ShapeRenderer();
		thread.start();
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
		if (player.hp > 0) {
<<<<<<< HEAD
			try {
				String s2 = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getPosByID?id=46");
				scan = new Scanner(s2);
				player2.setPos(scan.nextInt(), scan.nextInt());
				scan.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				JsonParser.sendHTML("updatePos", "id=45&xpos="+player.getX()+"&ypos="+player.getY());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
=======
			thread.run(player, player2);
>>>>>>> 57a4fd15136f32ccc9f5c1f20ff92698ee3c7354
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act();
			stage.draw();
			camera.update();
			renderer.setView(camera);
			renderer.render();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			hazard.render(batch);
			for (Zombie zomb : zombies) {
				zomb.render(batch, player, collision);
				zomb.checkCollision(player);
			}
			for (Wizard wiz : wizards) {
				wiz.render(batch, player, collision, enemyShots, elapsed);
			}
			font.draw(batch, "Health: " + player.hp, player.getX() - 200, player.getY() - 100);
			font.draw(batch, "Mouse X, Y: " + Gdx.input.getX() + ", " + Gdx.input.getY(), player.getX() - 200,
					player.getY() - 120);
			font.draw(batch, "Player X, Y: " + player.getX() + ", " + player.getY(), player.getX() - 200,
					player.getY() - 80);
			font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), player.getX() - 200, player.getY() - 60);
			for (int i = 0; i < Player.numBullets; i++) {
				if (shots.get(i).active)
					shots.get(i).render(batch, collision, zombies, wizards);
			}
			for(Bolt bolts: enemyShots)
			{
				if(bolts.active)
					bolts.render(batch, collision, player);
			}
			player.update(collision, shots, camera,batch);
			player2.update(batch);
			batch.end();
			elapsed += Gdx.graphics.getDeltaTime();
			hazard.checkCollision(player);
			hazard.drawRect();
			player.render(shape, camera);
			player2.render();
			hazard.update();
		}
		else
		{
			batch.begin();
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			font.draw(batch, "Game Over", player.x-40, player.y);
			font.draw(batch, "Press Esc to Exit", player.x-40, player.y-20);
			batch.end();
			
		}
	     if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
	     {
	    	 thread.stop();
	    	 dispose();
	    	 game.setScreen(new MainScreen(game));
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
		batch.dispose();
		map.dispose();
		renderer.dispose();
		stage.dispose();
		
	}
	

	
	@Override
	public void create() {
		Gdx.graphics.setResizable(false);
		renderer = new OrthogonalTiledMapRenderer(map);
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		batch = new SpriteBatch();
		skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

	}
}
