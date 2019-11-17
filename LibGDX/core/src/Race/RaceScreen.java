package Race;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RaceScreen implements Screen{
private TiledMap map;
private Game game;
private OrthogonalTiledMapRenderer renderer;
private OrthographicCamera camera;
private Stage stage;
private MapLayer road;
private MapLayer wall;
public RaceScreen(Game game)
{
	this.game = game;
	create();
}




public void create()
{
	  float w = Gdx.graphics.getWidth();
      float h = Gdx.graphics.getHeight();
      stage = new Stage();
      camera = new OrthographicCamera();
      camera.setToOrtho(false,w,h);
      camera.update();
      map = new TmxMapLoader().load("Race/track1.tmx");
      road = map.getLayers().get("Road");
      wall = map.getLayers().get("wall");
      renderer = new OrthogonalTiledMapRenderer(map);
      Gdx.input.setInputProcessor(stage);
}



@Override
public void show() {
	// TODO Auto-generated method stub
	
}

@Override
public void render(float delta) {
	// TODO Auto-generated method stub
	 Gdx.gl.glClearColor(1, 0, 0, 1);
     Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
     camera.update();
     renderer.setView(camera);
     renderer.render();
}

@Override
public void resize(int width, int height) {
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
}

	