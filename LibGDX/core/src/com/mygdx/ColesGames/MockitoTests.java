package com.mygdx.ColesGames;

import org.junit.Test;
import com.mygdx.ColesGames.Zone;
import com.mygdx.ColesGames.ConnectFour;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * 
 * @author Cole Weitzel
 *
 */
public class MockitoTests {
	
	/** --------------------------
	 * Mockito tests
	 -------------------------- */
	@Test
	public void mockCheckGameWon() {
		Zone[][] zones = new Zone[6][7];
		ConnectFour game = mock(ConnectFour.class);
		
		// assume the method doesn't work yet and mock it, return true
		when(game.checkGameOver(zones)).thenReturn(false);
		
		assertFalse(game.checkGameOver(zones));
		
		game.checkGameOver(zones);
		game.checkGameOver(zones);
		
		// verify there were only 3 interactions with this method
		verify(game, times(3)).checkGameOver(zones);
		verifyNoMoreInteractions(game);
	}
	
	// horizontal testing
	@Test
	public void testCheckGameWon() {
		Zone[][] z = new Zone[6][7];
		ConnectFour game = mock(ConnectFour.class);
		when(game.checkGameOver(z)).thenReturn(true);
		
		// populate zone array
		int x = 6;
		int y = 0;
		for (int r = 0; r < 6; r++) {
			for (int c = 0; c < 7; c++) {
				z[r][c] = new Zone(x, y, 80, 80);
				x += 91;
			}
			y += 80;
			x = 6;
		}
		
		// insert winning condition
		z[0][0].setTile("red");
		z[0][1].setTile("red");
		z[0][2].setTile("red");
		z[0][3].setTile("red");
		
		assertTrue(game.checkGameOver(z));
	}
}
