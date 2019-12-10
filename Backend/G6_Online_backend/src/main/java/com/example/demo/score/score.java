package com.example.demo.score;

import javax.persistence.*;

@Entity
@Table(name = "score")
public class score {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "score")
	private Integer score;

	@Column(name = "name")
	private String name;

	@Column(name = "userID")
	private Integer userID;

	public score() {
	}

	public score(Integer score, String name, Integer userID) {
		this.score = score;
		this.name = name;
		this.userID = userID;
	}
	
	public score(Integer id, Integer score, String name, Integer userID) {
		this.setId(id);
		this.setScore(score);
		this.setName(name);
		this.setUserID(userID);
	}

	public Integer getId() {
		return id;
	}

	public Integer getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
}
