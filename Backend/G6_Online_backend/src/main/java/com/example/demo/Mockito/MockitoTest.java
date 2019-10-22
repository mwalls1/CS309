package com.example.demo.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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

import com.example.demo.score.*;
import com.example.demo.userInfo.*;

/**
 * 
 * @author Cole Weitzel
 *
 */
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
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/*
	 * test userInfo
	 */
//	@Test
//	public void testGetAllUsers() {
//		List<userInfo> list = new ArrayList<userInfo>();
//		
//		Integer id1 = new Integer(1001);
//		Integer id2 = new Integer(1002);
//		Integer id3 = new Integer(1003);
//		
//		userInfo u1 = new userInfo(id1, "user1", "password1");
//		userInfo u2 = new userInfo(id2, "user2", "password2");
//		userInfo u3 = new userInfo(id3, "user3", "password3");
//		
//		list.add(u1);
//		list.add(u2);
//		list.add(u3);
//		
//		// mock instruction
//		when(userRepo.getUserInfoList()).thenReturn(list);
//		
//		List<userInfo> l = userService.getUserInfoList();
//		
//		// test ID's
//		assertEquals(id1, l.get(0).getId());
//		assertEquals(id2, l.get(1).getId());
//		assertEquals(id3, l.get(2).getId());
//		
//		// test name
//		assertEquals("user1", l.get(0).getName());
//		assertEquals("user2", l.get(1).getName());
//		assertEquals("user3", l.get(2).getName());
//		
//		// test password
//		assertEquals("password1", l.get(0).getPassword());
//		assertEquals("password2", l.get(1).getPassword());
//		assertEquals("password3", l.get(2).getPassword());
//	}
	
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
	
	
	

}
