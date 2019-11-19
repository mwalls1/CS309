package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.Cards.Card;
import com.mygdx.goFish.GoFishScreen;

import Race.Car;

public class MockitoTest extends Game{
	private Game game = this;
	private GoFishScreen mockedGame;
	
	
	@Before
	public void init()
	{
		mockedGame = mock(GoFishScreen.class);
	}
	@Test
	public void testCardSuit()
	{
		
		//Create mocked Player object
		Card cardMocked = mock(Card.class);
		
		//Return the test hand when getHand() is called
		when(cardMocked.getSuit()).thenReturn("spades");
		
		when(cardMocked.getRank()).thenReturn("Ace");
		//Create hand to compare to return
	
		assertEquals(cardMocked.getSuit(), "spades");
		assertEquals(cardMocked.getRank(), "Ace");
		
	}
	


	@Test
	public void testGetMoveHandling1()
	{
		when(mockedGame.getMove()).thenReturn("temporaryUser asks p2 for 2");
		
		
		assertFalse(mockedGame.handleMove(mockedGame.getMove()));
	}
	
	@Test
	public void testGetMoveHandling2()
	{
		GoFishScreen newGame = new GoFishScreen(game);
		newGame.create();
		when(mockedGame.getMove()).thenReturn("TemporaryUser asks p2 for 2");
		
		
		assertFalse(newGame.handleMove(mockedGame.getMove()));
	}
	
	@Test
	public void testRace()
	{
		AssetManager manager = new AssetManager();
		manager.load("Race/car1.png", Texture.class);
		manager.finishLoading();
		game = this;
		Cell mockedCell = mock(Cell.class);
		Car car = new Car("Race/car1.png", manager, new OrthographicCamera());
		TiledMap map = new TmxMapLoader().load("Race/track1 copy.tmx");
		TiledMapTileLayer road = (TiledMapTileLayer)map.getLayers().get("Road");
		Point pt = car.getTilePosition();
		Cell cell = road.getCell(pt.x, pt.y);
		TiledMapTile tile = cell.getTile();
		MapProperties prop = tile.getProperties();
		assertTrue(prop.containsKey("slow"));
	}
	
	@Test
	public void testRace2()
	{
		AssetManager manager = new AssetManager();
		manager.load("Race/car1.png", Texture.class);
		manager.finishLoading();
		game = this;
		Cell mockedCell = mock(Cell.class);
		Car car = new Car("Race/car1.png", manager, new OrthographicCamera());
		TiledMap map = new TmxMapLoader().load("Race/track1 copy.tmx");
		TiledMapTileLayer road = (TiledMapTileLayer)map.getLayers().get("Road");
		Point pt = car.getTilePosition();
		Cell cell = road.getCell(7, 13);
		TiledMapTile tile = cell.getTile();
		MapProperties prop = tile.getProperties();
		assertTrue(prop.containsKey("blocked"));
	}
	
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}
	
	
	//when(listMock.add(anyString())).thenReturn(false);

	
	

}