package com.mygdx.games;

import java.util.ArrayList;
import java.util.Random;
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

import util.JsonParser;

public class GameTest extends Game implements Screen{
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Stage stage;
	private BitmapFont font;
	Scanner scan;
	private Game game;
	private Texture blade;
	private Player player;
	private int score = 0;
	private Player player2;
	private ShapeRenderer shape;
	private boolean gameOver = false;
	private float elapsed = 0;
	private TiledMap map;
	private TiledMapTileLayer terrain;
	private TiledMapTileLayer walls;
	private TiledMapTileLayer collision;
	private OrthogonalTiledMapRenderer renderer;
	public ArrayList<Assassin> assassins = new ArrayList<Assassin>();
	private ArrayList<Hazard> hazards = new ArrayList<Hazard>();
	public ArrayList<Knife> shots = new ArrayList<Knife>();
	public ArrayList<Bolt> enemyShots = new ArrayList<Bolt>();
	public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public ArrayList<Dragon> dragons = new ArrayList<Dragon>();
	public ArrayList<Coin> coins = new ArrayList<Coin>();
	public serverThread thread = new serverThread();
	Random randX = new Random(); //866
	Random ranY = new Random(); //893
    public  float randomX;
    public float randomY;
	public GameTest(Game game)
	{
		font = new BitmapFont();
		this.game = game;
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		camera = new OrthographicCamera(Gdx.graphics.getDisplayMode().width/4, Gdx.graphics.getDisplayMode().height/4);
		map = new TmxMapLoader().load("dungeon3.tmx");
		MapLayers mapLayers = map.getLayers();
		terrain = (TiledMapTileLayer) mapLayers.get("floor");
		walls = (TiledMapTileLayer) mapLayers.get("walls");
		collision = (TiledMapTileLayer) mapLayers.get("blockage");
		player = new Player(847, 54);
		player2 = new Player(847,54);
		blade = new Texture(Gdx.files.internal("blade.png"));
		Texture zom = new Texture(Gdx.files.internal("zombie_idle_anim_f0.png"));
		Texture wiz = new Texture(Gdx.files.internal("wizard.png"));
		Texture asn = new Texture(Gdx.files.internal("daway.png"));
		hazards.add(new Hazard(blade,872,240, camera));
		hazards.add(new Hazard(blade,869,718, camera));
		hazards.add(new Hazard(blade,639,737, camera));
		hazards.add(new Hazard(blade,856,890, camera));
		hazards.add(new Hazard(blade,737,868, camera));
		hazards.add(new Hazard(blade,662,895, camera));
		hazards.add(new Hazard(blade,401, 893, camera));
		shape = new ShapeRenderer();
		for(int i = 0; i < 15; i ++)
		{
			randomX = randX.nextInt(866)+16;
			randomY = randX.nextInt(893)+21;
			while(walls.getCell((int)(((randomX))/16), (int)(randomY/16))!=null||walls.getCell((int)((randomX+16)/16), (int)(randomY/16))!=null||walls.getCell((int)((randomX+16)/16), (int)((randomY+16)/16))!=null||walls.getCell((int)((randomX)/16), (int)((randomY+16)/16))!=null)
			{
				randomX = randX.nextInt(866)+16;
				randomY = randX.nextInt(893)+21;
			}
			assassins.add(new Assassin(zom, (int)randomX, (int)randomY, camera));
		}
		for(int i = 0; i < 15; i ++)
		{
			randomX = randX.nextInt(866)+16;
			randomY = randX.nextInt(893)+21;
			while(walls.getCell((int)(((randomX))/16), (int)(randomY/16))!=null||walls.getCell((int)((randomX+16)/16), (int)(randomY/16))!=null||walls.getCell((int)((randomX+16)/16), (int)((randomY+16)/16))!=null||walls.getCell((int)((randomX)/16), (int)((randomY+16)/16))!=null)
			{
				randomX = randX.nextInt(866)+16;
				randomY = randX.nextInt(893)+21;
			}
			zombies.add(new Zombie(zom, (int)randomX, (int)randomY, camera));
		}
		for(int i = 0; i < 15; i ++)
		{
			randomX = randX.nextInt(866)+16;
			randomY = randX.nextInt(893)+21;
			while(walls.getCell((int)(((randomX))/16), (int)(randomY/16))!=null||walls.getCell((int)((randomX+16)/16), (int)(randomY/16))!=null||walls.getCell((int)((randomX+16)/16), (int)((randomY+16)/16))!=null||walls.getCell((int)((randomX)/16), (int)((randomY+16)/16))!=null)
			{
				randomX = randX.nextInt(866)+16;
				randomY = randX.nextInt(893)+21;
			}
			dragons.add(new Dragon(wiz, (int)randomX, (int)randomY, camera));
		}
		for(int i = 0; i < 50; i ++)
		{
			randomX = randX.nextInt(866)+16;
			randomY = randX.nextInt(893)+21;
			while(walls.getCell((int)(((randomX))/16), (int)(randomY/16))!=null||walls.getCell((int)((randomX+6)/16), (int)(randomY/16))!=null||walls.getCell((int)((randomX+6)/16), (int)((randomY+6)/16))!=null||walls.getCell((int)((randomX)/16), (int)((randomY+6)/16))!=null)
			{
				randomX = randX.nextInt(866)+16;
				randomY = randX.nextInt(893)+21;
			}
			coins.add(new Coin(randomX, randomY));
		}
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
		if(elapsed<3)
		{
			batch.begin();
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			font.draw(batch, "Kill all enemies and collect all coins", player.x-40, player.y);
			font.draw(batch, "Good Luck", player.x-40, player.y-20);
			batch.end();
			elapsed+=Gdx.graphics.getDeltaTime();
		}
	else if (player.hp > 0 && !gameOver) {
		if(player.numCoins==50 && player.numEnemies == 0)
			gameOver = true;
			//thread.run(player, player2);
			/*try {
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
			}*/
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			stage.act();
			stage.draw();
			camera.update();
			renderer.setView(camera);
			renderer.render();
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			for (Hazard haz : hazards) {
				haz.render(batch);
				haz.checkCollision(player, batch);
				haz.update();
			}
			for (Zombie zomb : zombies) {
				zomb.render(batch, player, collision);
				zomb.checkCollision(player);
			}
			for (Dragon drag : dragons) {
				drag.render(batch, player, collision, enemyShots, elapsed);
			}
			for(Coin coins : coins)
			{
				coins.render(shape, camera, batch);
				coins.checkCollision(player);
			}
			for (Assassin asns : assassins) {
				asns.render(batch, player, collision, enemyShots, elapsed);
			}
			font.getData().setScale(.5f);
			font.draw(batch, "Health: " + player.hp, player.getX() - 235, player.getY() + 130);
			font.draw(batch, ""+Gdx.graphics.getFramesPerSecond(), player.getX() + 229, player.getY() + 133);
			font.draw(batch, "Coins: "+player.numCoins, player.getX()-235, player.getY()+120);
			font.draw(batch, "Enemies Left: "+player.numEnemies, player.getX()-235, player.getY() +110);
			for (int i = 0; i < Player.numBullets; i++) {
				if (shots.get(i).active)
					shots.get(i).render(batch, collision, zombies, dragons, assassins);
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
			player.render(shape, camera);
			player2.render();

		}
		else
		{
			batch.begin();
			Gdx.gl.glClearColor(0, 0, 0, 0);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			if(player.hp<1)
			{
				font.draw(batch, "Game Over", player.x-40, player.y);
				font.draw(batch, "Press Esc to Exit", player.x-40, player.y-20);
			}
			else
			{
				score = (int)(player.hp/elapsed * 1000);
				font.draw(batch, "You Win!", player.x-40, player.y);
				font.draw(batch, "Press Esc to Exit", player.x-40, player.y-20);
				font.draw(batch, "Score: "+score, player.x-40, player.y-40);
				batch.end();
			}
			
		}
	     if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
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
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

	}
}
