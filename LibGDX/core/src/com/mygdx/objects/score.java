package com.mygdx.objects;

/**
 * This is the entity class which holds the properties of each user and
 * the getter and setter methods to modify the user.
 * 
 * @author Cole Weitzel
 * @author Taylor Weil
 *
 */

public class score {
	
	private Integer id;
	private Integer score;
	private String name;
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getScore() {
		return score;
	}
	public void setId(Integer idnum) {
		id = idnum;
	}
	public void setName(String name1) {
		name = name1;
	}
	public void setScore(Integer sc) {
		score = sc;
	}
  

}