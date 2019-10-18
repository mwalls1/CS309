package com.example.demo.score;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.userInfo.userInfo;

@RestController
public class scoreController {
	@Autowired
	private scoreRepository scoreRepo;
	
	@GetMapping("getScores")
	public List<score> getScores(){
		return scoreRepo.findAll();
	}
	
	@PostMapping("/newScore")
	public String saveScore(Integer score, String name, Integer userID) {
		score s = new score(score, name, userID);
		scoreRepo.save(s);
		return "New score of " + s.getScore() + " added to user " + s.getName();
	}
	
//	@DeleteMapping("/deleteUser")
//	public String delete(Integer id) {
//		Optional<score> u = scoreRepo.findById(id);
//        scoreRepo.deleteById(id);
//        return "Deleted score with ID: " + id + ".";
//	}
}
