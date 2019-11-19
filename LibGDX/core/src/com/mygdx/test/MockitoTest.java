package com.mygdx.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.*;

import com.mygdx.ticktacktoe.Player;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.platformer.*;
import com.mygdx.platformer.Character;
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
	
	@InjectMocks
	String temp;
	@Mock
	Player play1 = new Player(true, "x");
	
	@InjectMocks
	boolean temp2;
	@Mock
	Player play2 = new Player(true, "x");
	
	@InjectMocks
	Character char1 = new Character();
	@Mock
	Goon goon;
	@Mock
	Map map;
	@Mock
	Checkpoint check1;
	
	@Test
	public void testGetLet() {
		Mockito.when(play1.getLet()).thenReturn("x");
		temp = play1.getLet();
		assertEquals(temp, "x");
	}
	@Test
	public void testBoardCheckWin() {
		Mockito.when(play2.getTurn()).thenReturn(false);
		temp2 = play2.getTurn();
	assertEquals(temp2, false);
	}
	@Test
	public void testCharacterReset()
	{
		Mockito.when(goon.checkCollision(char1)).thenReturn(true);
		if(goon.checkCollision(char1))
		{
			char1.setSpawn(char1.getsX(), char1.getsY());
		}
		assertEquals(char1.getX(), char1.getsX());
		assertEquals(char1.getY(), char1.getsY());
	}
	@Test
	public void testCheckpointSet()
	{
		Mockito.when(check1.checkCollision(char1)).thenReturn(true);
		if(check1.checkCollision(char1))
		{
			char1.setSpawn(check1.getX(), check1.getY());
		}
		assertEquals(char1.getsX(), check1.getX());
		assertEquals(char1.getsY(), check1.getY());
	}
	
	
}
