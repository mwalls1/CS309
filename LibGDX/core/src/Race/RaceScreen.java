package Race;

import java.awt.Point;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RaceScreen implements Screen{
private boolean[][] passable; 
private TiledMap map;
private Game game;
private OrthogonalTiledMapRenderer renderer;
private OrthographicCamera camera;
private Stage stage;
private TiledMapTileLayer road;
private TiledMapTileLayer wall;
private AssetManager manager;
private SpriteBatch batch;
private Car player;
private float angle;
private Point playerPosition;
public RaceScreen(Game game)
{
	this.game = game;
	create();
}




public void create()
{
	
	angle = 90;
	float w = Gdx.graphics.getWidth();
	float h = Gdx.graphics.getHeight();
	initAssetManager();
	batch = new SpriteBatch();
	stage = new Stage();
	camera = new OrthographicCamera();
	camera.setToOrtho(false,w,h);
	camera.position.x = 128;
	camera.position.y = 128;
	batch.setProjectionMatrix(camera.combined);
	player = new Car("Race/car1.png", manager, camera);
	player.setPosition(w/2, h/2);
	map = new TmxMapLoader().load("Race/track1 copy.tmx");
	road = (TiledMapTileLayer)map.getLayers().get("Road");
	renderer = new OrthogonalTiledMapRenderer(map);
	Gdx.input.setInputProcessor(stage);
}



@Override
public void show() {
	// TODO Auto-generated method stub
	
}

@Override
public void render(float delta) {
	Point playerPosition = player.getTilePosition();
	 Gdx.gl.glClearColor(1, 0, 0, 1);
     Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     handleInput();
     renderer.setView(camera);
     
  
    	 
     if (Gdx.input.isKeyPressed(Keys.R)) create();
     renderer.render();
     batch.begin();
     player.draw(batch);
     if (road.getCell(playerPosition.x, playerPosition.y).getTile().getProperties().containsKey("blocked")) {
    	 player.setSpeed(0);
    	 player.moveAfterCollision();
     }
     else {
    	 player.move();
     }
     batch.end();	
     camera.update();
     if (Gdx.input.isKeyPressed(Keys.SPACE)) {
     System.out.println("Player Position: " + playerPosition.toString());
     System.out.println("Slow tile: " + road.getCell(playerPosition.x, playerPosition.y).getTile().getProperties().containsKey("slow"));
     System.out.println("Blocked tile: " + road.getCell(playerPosition.x, playerPosition.y).getTile().getProperties().containsKey("blocked"));
     System.out.println();
     }
     
     
    if (road.getCell(playerPosition.x, playerPosition.y).getTile().getProperties().containsKey("slow")) {
    	player.decSpeed(0.1f);
    	System.out.print("SLOW at " + playerPosition.toString() + "\n");
   
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

public void initAssetManager()
{
	manager = new AssetManager();
	manager.load("Race/car1.png", Texture.class);
	
	manager.finishLoading();
}

public void handleInput() {
	
	
}
}

	