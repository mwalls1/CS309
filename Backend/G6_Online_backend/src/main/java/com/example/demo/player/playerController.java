//package com.example.demo.player;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//public class playerController {
//	@Autowired
//	private playerRepository playerRepo;
//	
//	@GetMapping("getPlayers")
//	public List<player> getPlayers(){
//		return playerRepo.findAll();
//	}
//	
//	@GetMapping("getPosByID")
//	public String getXbyID(Integer id) {
//		Optional<player> p = playerRepo.findById(id);
//		player p1 = p.get();
//		return p1.getXpos().toString() + " " + p1.getYpos().toString();
//	}
//	
//	@PostMapping("/newPlayer")
//	public String savePlayer(Integer xpos, Integer ypos) {
//		player p = new player(xpos, ypos);
//		playerRepo.save(p);
//		return "New player made at " + p.getXpos() + " added to user " + p.getYpos();
//	}
//	
//	@PostMapping("/updatePos")
//	public String updatePos(Integer id, Integer xpos, Integer ypos) {
//		Optional<player> p = playerRepo.findById(id);
//		player p1 = p.get();
//		p1.setXpos(xpos);
//		p1.setYpos(ypos);
//		playerRepo.save(p1);
//		return p1.getXpos().toString() + " " + p1.getYpos().toString();
//	}
//}
