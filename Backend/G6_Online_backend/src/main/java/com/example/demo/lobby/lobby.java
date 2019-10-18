package com.example.demo.lobby;

import javax.persistence.*;

@Entity
@Table(name = "lobby")
public class lobby {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "player1")
	private Integer player1;

	@Column(name = "player2")
	private Integer player2;
	
	@Column(name = "player3")
	private Integer player3;

	@Column(name = "player4")
	private Integer player4;

	public lobby() {
		this.player1 = 0;
		this.player2 = 0;
		this.player3 = 0;
		this.player4 = 0;
	}
	
	public Integer getId() {
		return this.id;
	}

	public Integer getPlayer1() {
		return player1;
	}

	public Integer getPlayer2() {
		return player2;
	}
	
	public Integer getPlayer3() {
		return player3;
	}
	
	public Integer getPlayer4() {
		return player4;
	}

	public void setPlayer1(Integer id) {
		this.player1 = id;
	}

	public void setPlayer2(Integer id) {
		this.player2 = id;
	}
	
	public void setPlayer3(Integer id) {
		this.player3 = id;
	}
	
	public void setPlayer4(Integer id) {
		this.player4 = id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
}
