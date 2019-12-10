package com.mygdx.ColesGames;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.badlogic.gdx.Game;
import com.mygdx.game.MyGdxGame;

/**
 * 
 * @author Taylor Weil
 *
 */
public class MockitoTestsTaylor {
	
	/**
	 * Mockito Tests for DEMO 5
	 */
	
	@Mock
	Game myGame;
	@Mock
	Checkers checkersGame;
	@Mock
	CheckerPiece ch;
	
	@Before
	public void setupGame() {
		myGame = mock(MyGdxGame.class);
		checkersGame = mock(Checkers.class);
		checkersGame.render();
		ch = mock(CheckerPiece.class, Mockito.CALLS_REAL_METHODS);  //new CheckerPiece(1, new int[] {0,0});

	}
	
	@Test
	public void testGetPossibleMoves() {
		ArrayList<int[]> list = new ArrayList<int[]>();
		Mockito.when(checkersGame.getPossibleMoves(0, 0)).thenReturn(list);
		ArrayList<int[]> moves = checkersGame.getPossibleMoves(0, 0);
		assertEquals(0, moves.size());
	}
	
	@Test
	public void testKingMe() {
		assertFalse(ch.isKing());
		ch.kingMe();
		assertTrue(ch.isKing());
	}
	
	@Test(expected=NullPointerException.class)
	public void testPossibleMoves() {
		assertEquals(null, ch.getPosibbleMoves());
		ArrayList<int[]> list = new ArrayList<>();
		list.add(new int[] {0,0});
		ch.setPossibleMoves(list);
		assertEquals(1, ch.getPosibbleMoves().size());
		assertEquals(0, ch.getCol());
	}
}
