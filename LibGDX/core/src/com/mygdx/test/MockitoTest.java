package com.mygdx.test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mygdx.ticktacktoe.Player;

/**
 * 
 * @author Mason Walls
 *
 */
public class MockitoTest {
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/*
	 * test userInfo
	 */
	@InjectMocks
	String temp;
	@Mock
	Player play1 = new Player(true, "x");
	
	@InjectMocks
	boolean temp2;
	@Mock
	Player play2 = new Player(true, "x");
	// Test get user by ID
	@Test
	public void testGetLet() {
		when(play1.getLet()).thenReturn("x");
		temp = play1.getLet();
		assertEquals(temp, "x");
	}
	@Test
	public void testBoardCheckWin() {
		when(play2.getTurn()).thenReturn(false);
		temp2 = play2.getTurn();
		assertEquals(temp2, false);
	}
	
	/*
	 * test score
	 */
}
