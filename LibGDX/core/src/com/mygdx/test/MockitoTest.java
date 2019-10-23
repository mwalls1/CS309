package com.mygdx.test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.ticktacktoe.*;

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
