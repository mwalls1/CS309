package com.mygdx.platformer;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class lvlOne extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Stage stage;
	private BitmapFont font;
	Scanner scan;
	private Character player;
	private float elapsed = 0;
	private TiledMap map;
	private TiledMapTileLayer terrain;
	private TiledMapTileLayer death;
	private OrthogonalTiledMapRenderer renderer;
	public ArrayList<Spike> spikes = new ArrayList<Spike>();
	public ArrayList<Goon> goons = new ArrayList<Goon>();
	public ArrayList<Checkpoint> points = new ArrayList<Checkpoint>();
	public ArrayList<Fairy> fairies = new ArrayList<Fairy>();
	public lvlOne(Game game)
	{
		font = new BitmapFont();
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		camera = new OrthographicCamera(Gdx.graphics.getDisplayMode().width/4, Gdx.graphics.getDisplayMode().height/4);
		map = new TmxMapLoader().load("platformer2.tmx");
		MapLayers mapLayers = map.getLayers();
		fairies.add(new Fairy(494, 920, camera));
		fairies.add(new Fairy(779, 800, camera));
		goons.add(new Goon(394, 911, camera));
		goons.add(new Goon(1216, 527, camera));
		goons.add(new Goon(1725, 975, camera));
		goons.add(new Goon(1953, 975, camera));
		goons.add(new Goon(2264, 1167, camera));
		goons.add(new Goon(1976, 1167, camera));
		terrain = (TiledMapTileLayer) mapLayers.get("blockage");
		death = (TiledMapTileLayer) mapLayers.get("death");
		player = new Character(50, 240);
		points.add(new Checkpoint(50, 718));
		points.add(new Checkpoint(1599, 128));
		points.add(new Checkpoint(1841, 1072));
		spikes.add(new Spike(100,240));
		spikes.add(new Spike(222,240));
		spikes.add(new Spike(242,240));
		spikes.add(new Spike(575,336));
		spikes.add(new Spike(575,144));
		spikes.add(new Spike(839,272));
		spikes.add(new Spike(498,592));
		spikes.add(new Spike(410,912));
		spikes.add(new Spike(626,816));
		spikes.add(new Spike(802,768));
		spikes.add(new Spike(912,688));
		spikes.add(new Spike(1276,240));
		spikes.add(new Spike(1724,400));
		spikes.add(new Spike(1842,688));
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
        elapsed+= Gdx.graphics.getDeltaTime();
		
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
		player.render(terrain, death ,camera,batch);
		font.draw(batch, "X, Y: "+player.getX()+", "+player.getY(), player.x - 40, player.y);
		for (Fairy zomb : fairies) {
			zomb.render(batch, player, terrain, elapsed);
		}
		for (Spike zomb : spikes) {
			zomb.render(player, batch);
		}
		for (Goon zomb : goons) {
			zomb.render(batch, player, terrain, elapsed);
		}
		for (Checkpoint zomb : points) {
			zomb.render(player, batch);
		}
		batch.end();
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
