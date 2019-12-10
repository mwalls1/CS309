package com.mygdx.objects;

/**
 * This is the entity class which holds the properties of each user and the
 * getter and setter methods to modify the user.
 * 
 * @author Cole Weitzel
 * @author Taylor Weil
 *
 */

public class userInfo {

	private Integer id;
	private String name;
	private String password;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setId(Integer idnum) {
		id = idnum;
	}

	public void setName(String name1) {
		name = name1;
	}

	public void setPassword(String pass) {
		password = pass;
	}

}