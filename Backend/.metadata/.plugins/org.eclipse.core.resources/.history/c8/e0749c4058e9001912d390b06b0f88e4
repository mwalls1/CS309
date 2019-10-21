package com.example.demo.player;

import javax.persistence.*;

@Entity
@Table(name = "player")
public class player {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "xpos")
	private Integer xpos;

	@Column(name = "ypos")
	private Integer ypos;

	public player() {
	}

	public player(Integer xpos, Integer ypos) {
		this.xpos = xpos;
		this.ypos = ypos;
	}

	public Integer getId() {
		return id;
	}

	public Integer getXpos() {
		return xpos;
	}

	public Integer getYpos() {
		return ypos;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setXpos(Integer xpos) {
		this.xpos = xpos;
	}

	public void setYpos(Integer ypos) {
		this.ypos = ypos;
	}
}
