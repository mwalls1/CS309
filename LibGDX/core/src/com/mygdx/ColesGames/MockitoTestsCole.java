package com.mygdx.ColesGames;

import org.junit.Test;
import com.mygdx.ColesGames.Zone;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * 
 * @author Cole Weitzel
 *
 */
public class MockitoTestsCole {
	
	/**
	 * Mockito tests
	 */
	@Test
	public void mockCheckGameWonVerify() {
		Zone[][] zones = new Zone[6][7];
		ConnectFourLogic game = mock(ConnectFourLogic.class);
		
		// assume the method doesn't work yet and mock it
		when(game.checkGameOver(zones, false)).thenReturn(false);
		
		assertFalse(game.checkGameOver(zones, false));
		
		game.checkGameOver(zones, false);
		game.checkGameOver(zones, false);
		
		// verify there were only 3 interactions with this method
		verify(game, times(3)).checkGameOver(zones, false);
		verifyNoMoreInteractions(game);
	}
	
	// horizontal testing
	@Test
	public void testCheckGameWonHorz() {
		Zone[][] z = new Zone[6][7];
		ConnectFourLogic game = mock(ConnectFourLogic.class);
		when(game.checkGameOver(z, false)).thenReturn(true);
		
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
		
		assertTrue(game.checkGameOver(z, false));
	}
	
	// vertical testing
	@Test
	public void testCheckGameWonVert() {
		Zone[][] z = new Zone[6][7];
		ConnectFourLogic game = mock(ConnectFourLogic.class);
		when(game.checkGameOver(z, false)).thenReturn(true);
		
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
		z[0][0].setTile("yellow");
		z[1][0].setTile("yellow");
		z[2][0].setTile("yellow");
		z[3][0].setTile("yellow");
		
		assertTrue(game.checkGameOver(z, false));
	}
	
	@Test
	public void findLowestTileTest() {
		int[] arr1 = { 4, 3 };
		int[] arr2 = { 0, 3 };
		ConnectFourLogic logic = mock(ConnectFourLogic.class);
		Zone[][] z = new Zone[6][7];
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
		when(logic.findLowestTile(arr1, z)).thenReturn(arr2);
		when(logic.findLowestTile(arr2, z)).thenReturn(arr2);
		
		assertEquals(arr2, logic.findLowestTile(arr1, z));
		assertEquals(arr2, logic.findLowestTile(arr2, z));
	}
}
