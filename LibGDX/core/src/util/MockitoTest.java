package util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.mygdx.Cards.Card;

public class MockitoTest{
	
	
	@Test
	public void shouldReturnCorrectSuit()
	{
		
		//Create mocked Player object
		Card cardMocked = mock(Card.class);
		
		//Return the test hand when getHand() is called
		when(cardMocked.getSuit()).thenReturn("spades");
		
		//Create hand to compare to return
	
		assertEquals(cardMocked.getSuit(), "spades");
		
	}
	
	
	//when(listMock.add(anyString())).thenReturn(false);

	
	

}