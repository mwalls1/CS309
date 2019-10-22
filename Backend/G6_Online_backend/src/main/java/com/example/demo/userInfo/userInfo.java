package com.example.demo.userInfo;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * This is the entity class which holds the properties of each user and
 * the getter and setter methods to modify the user.
 * 
 * @author Cole Weitzel
 * @author Taylor Weil
 *
 */

@Entity
@Table(name = "userInfo")
public class userInfo {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotEmpty
	@Column(name = "name")
	private String name;
	
	@NotEmpty
	@Column(name = "password")
	private String password;
	
	@Column(name = "xpos")
	private Integer xpos;

	@Column(name = "ypos")
	private Integer ypos;
	
	public userInfo() {
		this.setName("default");
		this.setPassword("default");
	}
	
	public userInfo(String name, String password) {
		this.setName(name);
		this.setPassword(password);
	}
	
	public userInfo(Integer xpos, Integer ypos) {
		this.setXpos(xpos);
		this.setYpos(ypos);
	}
	
	// used for testing Mockito
	public userInfo(Integer id, String name, String password) {
		this.setId(id);
		this.setName(name);
		this.setPassword(password);
	}
	
	public Integer getId() { return id; }
	
	public String getName() { return name;}
	
	public String getPassword() { return password; }
	
	public Integer getXpos() { return xpos; }

	public Integer getYpos() { return ypos; }
	
	public void setId(Integer idnum) { id = idnum;}
	
	public void setName(String name1) { name = name1; }
	
	public void setPassword(String pass) { password = pass; }
	
	public void setXpos(Integer xpos) { this.xpos = xpos; }

	public void setYpos(Integer ypos) { this.ypos = ypos; }

}

