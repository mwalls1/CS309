package com.example.demo.lobby;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface lobbyRepository extends JpaRepository<lobby, Integer>{

	Integer getVote1ById(int id);
	
	lobby getLobbyById(int id);
}

