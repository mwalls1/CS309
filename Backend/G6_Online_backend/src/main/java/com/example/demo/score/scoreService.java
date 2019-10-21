package com.example.demo.score;

import org.springframework.beans.factory.annotation.Autowired;

public class scoreService {
	@Autowired
	private scoreRepository repo;

	public score getScoreById(int id) {
		return repo.getScoreById(id);
	}
}
