package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Game;
import com.mygdx.Cards.Card;
import com.mygdx.goFish.GoFishScreen;

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
	
	
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}
	
	
	//when(listMock.add(anyString())).thenReturn(false);

	
	

}