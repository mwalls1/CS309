package com.example.demo.userInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is an empty public interface that connects to user_info table
 * in our database
 * 
 * @author Cole Weitzel
 * @author Taylor Weil
 *
 */
@Repository
public interface userInfoRepository extends JpaRepository<userInfo, Integer> {

	userInfo getUserInfoById(int id);

//	List<userInfo> getUserInfoList();

}
