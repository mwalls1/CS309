package com.example.demo.lobby;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class lobbyController {
	@Autowired
	private lobbyRepository lobbyRepo;
	
	@GetMapping("getLobbies")
	public List<lobby> getlobbys(){
		return lobbyRepo.findAll();
	}
	
	@GetMapping("getLobbyByID")
	public String getXbyID(Integer id) {
		Optional<lobby> l = lobbyRepo.findById(id);
		lobby l1 = l.get();
		return l1.getPlayer1() + " " + l1.getPlayer2() + " " + l1.getPlayer3() + " " + l1.getPlayer4();
	}
	
	@PostMapping("/newLobby")
	public String newLobby(Integer id) {
		lobby l = new lobby();
		l.setId(id);
		lobbyRepo.save(l);
		return "New Lobby made with id:" + l.getId();
	}
	
	@PostMapping("/updatePlayer")
	public String updatePos(Integer id, Integer player, Integer playerId) {
		Optional<lobby> l = lobbyRepo.findById(id);
		lobby l1 = l.get();
		switch(player) {
		case(1):
			l1.setPlayer1(playerId);
			break;
		case(2):
			l1.setPlayer2(playerId);
			break;
		case(3):
			l1.setPlayer3(playerId);
			break;
		case(4):
			l1.setPlayer4(playerId);
		 	break;
		}
		lobbyRepo.save(l1);
		return "Updated player " + player + " to id: " + playerId;
	}
	
	@PostMapping("/vote")
	public String vote(Integer id, Integer playerId, Integer vote) {
		lobby l = lobbyRepo.findById(id).get();
		if (l.getPlayer1() == playerId) l.setPlayerOneVote(vote);
		else if (l.getPlayer2() == playerId) l.setPlayerTwoVote(vote);
		else if (l.getPlayer3() == playerId) l.setPlayerThreeVote(vote);
		else if (l.getPlayer4() == playerId) l.setPlayerFourVote(vote);
		else return "fail";
		lobbyRepo.save(l);
		return "Vote casted";
	}
	
	@PostMapping("/readyUp")
	public String readyUp(Integer id, Integer playerId) {
		lobby l = lobbyRepo.findById(id).get();
		if(l.getReadyStatus() == null) l.setReadyStatus(1);
		try {
		if (l.getPlayer1() == playerId) {
			if (l.getReadyStatus()%2 == 0) l.setReadyStatus(l.getReadyStatus()/2);
			else if (l.getReadyStatus()%2 != 0) l.setReadyStatus(l.getReadyStatus()*2);
			lobbyRepo.save(l);
			return "Set player 1 ready status";}
		if (l.getPlayer2() == playerId) {
			if (l.getReadyStatus()%3 == 0) l.setReadyStatus(l.getReadyStatus()/3);
			else if (l.getReadyStatus()%3 != 0) l.setReadyStatus(l.getReadyStatus()*3);
			lobbyRepo.save(l);
			return "Set player 2 ready status";}
		if (l.getPlayer3() == playerId) {
			if (l.getReadyStatus()%5 == 0) l.setReadyStatus(l.getReadyStatus()/5);
			else if (l.getReadyStatus()%5 != 0) l.setReadyStatus(l.getReadyStatus()*5);
			lobbyRepo.save(l);
			return "Set player 3 ready status";}
		if (l.getPlayer4() == playerId) {
			if (l.getReadyStatus()%7 == 0) l.setReadyStatus(l.getReadyStatus()/7);
			else if (l.getReadyStatus()%7 != 0) l.setReadyStatus(l.getReadyStatus()*7);
			lobbyRepo.save(l);
			return "Set player 4 ready status";}
		}catch (Exception e) {return e.toString();}
		lobbyRepo.save(l);
		return "No player updated";
	}
	
	@PostMapping("/readyDown")
	public String readDown(Integer id) {
		lobby l = lobbyRepo.findById(id).get();
		l.setReadyStatus(1);
		lobbyRepo.save(l);
		return "Lobby ready cleared";
	}
	
	@PutMapping("/cleanLobbies")
	public String cleanLobbies() {
		List<lobby> lobbies = lobbyRepo.findAll();
		for (lobby l : lobbies) {
			if ((l.getPlayer1() == null) || (l.getPlayer1() == 0)) {
				lobbyRepo.deleteById(l.getId());
			}
		}
		
		return "Lobbies Cleaned";
	}
	
	@GetMapping("/getReadyStatus")
	public String getReadyStatus(Integer id) {
		try {
		return lobbyRepo.findById(id).get().getReadyStatus() + "";
		}catch (Exception e) {return e.toString();}
	}
	
	@GetMapping("/getPlayerVotes")
	public String getPlayerVotes(Integer id) {
		return lobbyRepo.findById(id).get().getPlayerGameVote();
	}
}
