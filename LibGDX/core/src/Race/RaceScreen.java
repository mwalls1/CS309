package Race;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RaceScreen implements Screen{
private TiledMap map;
private Game game;
private OrthogonalTiledMapRenderer renderer;
private Stage stage;
private OrthographicCamera camera;
private SpriteBatch batch;
private TiledMapTileLayer road;
private TiledMapTileLayer wall;

public RaceScreen(Game game)
{
	this.game = game;
	create();
}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor((float)1/255, (float)1/255, (float)1/255, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		renderer.setView(camera);
		renderer.render();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
	
	public void create()
	{
		map = new TmxMapLoader().load("Race/testTrack.tmx");
	
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		camera = new OrthographicCamera(Gdx.graphics.getWidth()*4, Gdx.graphics.getHeight()*4);
		
		batch = new SpriteBatch();
		
		MapLayers mapLayers = map.getLayers();
		wall = (TiledMapTileLayer) mapLayers.get("Wall");
		road = (TiledMapTileLayer) mapLayers.get("Road");
		
		renderer = new OrthogonalTiledMapRenderer(map);
		
		stage = new Stage();
		
        Gdx.input.setInputProcessor(stage);
	}

}
