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
	
	@Column(name = "seed")
	private Integer seed;
	
	@Column(name = "p1game")
	private Integer p1game;
	
	@Column(name = "p2game")
	private Integer p2game;
	
	@Column(name = "p3game")
	private Integer p3game;
	
	@Column(name = "p4game")
	private Integer p4game;

	@Column(name = "playerReadyStatus")
	private Integer playerReadyStatus;
	public lobby() {
		this.player1 = 0;
		this.player2 = 0;
		this.player3 = 0;
		this.player4 = 0;
		this.seed = 0;
		this.p1game = 0;
		this.p1game = 0;
		this.p1game = 0;
		this.p1game = 0;
		this.playerReadyStatus = 0;
	}
	
	public Integer getId() {
		return this.id;
	}

	public Integer getPlayer(int playerNum) {
		switch (playerNum){
		case 1 : return player1;
		case 2 : return player2;
		case 3 : return player3;
		case 4 : return player4;
		}
		return 0;
	}

	public String getPlayerGameVote() {
		String val = "";
		if (p1game != null) val += p1game + " ";
		else val += "0 ";
		if (p2game != null) val += p2game + " ";
		else val += "0 ";
		if (p3game != null) val += p3game + " ";
		else val += "0 ";
		if (p4game != null) val += p4game + " ";
		else val += "0";
		return val;
	}
	
	public Integer getReadyStatus() {
		return playerReadyStatus;
	}
	
	public void setPlayer(Integer id, int playerNum) {
		switch (playerNum){
			case 1 : this.player1 = id;
				break;
			case 2 : this.player2 = id;
				break;
			case 3 : this.player3 = id;
				break;
			case 4 : this.player4 = id;
				break;
		}
		
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setPlayerOneVote(Integer vote) {
		this.p1game = vote;
	}
	public void setPlayerTwoVote(Integer vote) {
		this.p2game = vote;
	}
	public void setPlayerThreeVote(Integer vote) {
		this.p3game = vote;
	}
	public void setPlayerFourVote(Integer vote) {
		this.p4game = vote;
	}
	
	public void setReadyStatus(Integer ready) {
		this.playerReadyStatus = ready;
	}
}
