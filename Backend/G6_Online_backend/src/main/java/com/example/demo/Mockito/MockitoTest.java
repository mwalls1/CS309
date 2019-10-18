package com.example.demo.Mockito;

//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//
//import com.example.demo.userInfo.*;
//
///**
// * 
// * @author Cole Weitzel
// *
// */
//public class MockitoTest {
//	@InjectMocks
//	userInfoService userService;

//	@Mock
//	userInfoRepository repo;
//	
//	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
//	
//	@Test
//	public void testQuery()  {
//        userInfo t  = new userInfo(); 
//        boolean check = t.query("* from t");
//        assertTrue(check); 
//        verify(databaseMock).query("* from t");
//    }

//	@Before
//	public void init() {
//		MockitoAnnotations.initMocks(this);
//	}

//	@Test
//	public void getuserInfoByIdTest() {
//		when(repo.getUserInfoById(1000)).thenReturn(new userInfo("mockito", "test"));
//
//		userInfo acct = userService.getUserInfoById(1);
////		acct.setId(1000);
//
//		assertEquals("mockito", acct.getName());
//		assertEquals("test", acct.getPassword());
//	}

//	@Test
//	public void getAlluserInfoTest() {
//		List<userInfo> list = new ArrayList<userInfo>();
//		userInfo acctOne = new userInfo("test1", "pw");
//		acctOne.setId(1001);
//		userInfo acctTwo = new userInfo("test2", "pw");
//		acctTwo.setId(1002);
//		userInfo acctThree = new userInfo("test3","pw");
//		acctThree.setId(1003);
//
//		list.add(acctOne);
//		list.add(acctTwo);
//		list.add(acctThree);
//
//		when(repo.getUserInfoList()).thenReturn(list);
//
//		List<userInfo> acctList = userService.getUserInfoList();
//
//		assertEquals(3, acctList.size());
//		verify(repo, times(1)).getUserInfoList();
//	}
//
//}
