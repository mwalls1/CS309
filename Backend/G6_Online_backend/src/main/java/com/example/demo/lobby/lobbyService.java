package com.example.demo.lobby;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author Taylor Weil
 *
 */
public class lobbyService {
	@Autowired
	private lobbyRepository repo;

	public Integer getVote1ById(int id) {
		return repo.getVote1ById(id);
	}
	
	public lobby getLobbyById(int id) {
		return repo.getLobbyById(id);
		
	}
	
}
