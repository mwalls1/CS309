package com.example.demo.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.lobby.lobby;
import com.example.demo.lobby.lobbyRepository;
import com.example.demo.lobby.lobbyService;
import com.example.demo.score.score;
import com.example.demo.score.scoreRepository;
import com.example.demo.score.scoreService;
import com.example.demo.userInfo.userInfo;
import com.example.demo.userInfo.userInfoRepository;
import com.example.demo.userInfo.userInfoService;

public class MockitoTest {
	
	/*
	 * Set up userInfo stuff
	 */
	@InjectMocks
	userInfoService userService;
	
	@Mock
	userInfoRepository userRepo;
	
	/*
	 * Set up score stuff
	 */
	@InjectMocks
	scoreService scoreService;
	
	@Mock
	scoreRepository scoreRepo;
	
	/*
	 *  Set up lobby stuff
	 */
	@InjectMocks
	lobbyService lobbyService;
	
	@Mock
	lobbyRepository lobbyRepo;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * @author Cole Weitzel
	 */
	// Test get user by ID
	@Test
	public void testGetUserById() {
		// mock instruction
		when(userRepo.getUserInfoById(new Integer(1000))).thenReturn(new userInfo(new Integer(1000), "Mockito", "test"));
		
		userInfo u = userService.getUserInfoById(1000);
		
		// tests
		assertEquals(new Integer(1000), u.getId());
		assertEquals("Mockito", u.getName());
		assertEquals("test", u.getPassword());
	}
	
	/*
	 * test score
	 */
	@Test
	public void testScore() {
		Integer id = new Integer(500);
		Integer score = new Integer(1000000);
		String name = "Mockito";
		Integer userID = new Integer(1000);
		
		// mock instruction
		when(scoreRepo.getScoreById(id)).thenReturn(new score(id, score, name, userID));
		
		score s = scoreService.getScoreById(id);
		
		// tests
		assertEquals(id, s.getId());
		assertEquals(score, s.getScore());
		assertEquals(name, s.getName());
		assertEquals(userID, s.getUserID());
	}
	
	/**
	 * @author Taylor Weil
	 */
	@Test
	public void testLobby() {
		Integer id = new Integer(5);
		when(lobbyRepo.getVote1ById(id)).thenReturn(5);
		
		assertEquals(id,lobbyService.getVote1ById(5));
	}
	
	@Test
	public void testLobbyById() {
		lobby l = new lobby();
		l.setId(100);
		when(lobbyRepo.getLobbyById(100)).thenReturn(l);
		
		assertEquals(new Integer(100), lobbyRepo.getLobbyById(100).getId());
		assertEquals(new Integer(0), lobbyRepo.getLobbyById(100).getPlayer(1));
	}
	

}
