package com.example.demo.lobby;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.userInfo.userInfo;
import com.example.demo.userInfo.userInfoRepository;


@RestController
public class lobbyController {
	@Autowired
	private lobbyRepository lobbyRepo;
	
	@Autowired
	private userInfoRepository userRepo;
	
	@GetMapping("getLobbies")
	public List<lobby> getlobbys(){
		return lobbyRepo.findAll();
	}
	
	@GetMapping("getLobbyByID")
	public String getXbyID(Integer id) {
		try {
			Optional<lobby> l = lobbyRepo.findById(id);
			lobby l1 = l.get();
			return l1.getPlayer1() + " " + l1.getPlayer2() + " " + l1.getPlayer3() + " " + l1.getPlayer4();
		} catch (Exception e) {return e.toString();}
	}
	
	@PostMapping("/newLobby")
	public String newLobby(Integer id) {
		try {
			lobby l = new lobby();
			l.setId(id);
			lobbyRepo.save(l);
			return "New Lobby made with id:" + l.getId();
		} catch (Exception e) {return e.toString();}
	}
	
	@PostMapping("/updatePlayer")
	public String updatePos(Integer id, Integer player, Integer playerId) {
		try {
			removePlayerFromLobbies(playerId);
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
		} catch (Exception e) {return e.toString();}
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
	
	@PostMapping("/removePlayerFromLobbies")
	public String removePlayerFromLobbies(Integer playerId) {
		for (lobby l : lobbyRepo.findAll()) {
			if(l.getReadyStatus() == null) l.setReadyStatus(1);
			try {
				if (l.getPlayer1() == playerId) l.setPlayer1(0);
				if (l.getPlayer2() == playerId) l.setPlayer2(0);
				if (l.getPlayer3() == playerId) l.setPlayer3(0);
				if (l.getPlayer4() == playerId) l.setPlayer4(0);
			}catch (Exception e) {return e.toString();}
			lobbyRepo.save(l);
		}
		return "Player Removed";
	}
	
	/*
	 * take in id xpos ypos return string
	 * 
	 */
	@PostMapping("/sendPosGetPlayers")
	public String sendPosGetPlayers(Integer lobbyId, Integer playerId, Integer xpos, Integer ypos) {
		try {
			String result = "";
			lobby l = lobbyRepo.findById(lobbyId).get();
			userInfo u = null;

			Integer player1 = l.getPlayer1();
			if(player1 == 0) {
				result += "0 0 0 ";
			} else {
				u = userRepo.findById(player1).get();
				if (player1 == playerId){
					u.setXpos(xpos);
					u.setYpos(ypos);
					userRepo.save(u);
				}
				result = result + player1 + " " + u.getXpos() + " " + u.getYpos() + " ";
			}
			
			Integer player2 = l.getPlayer2();
			if(player2 == 0) {
				result += "0 0 0 ";
			} else {
				u = userRepo.findById(player2).get();
				if (player2 == playerId){
					u.setXpos(xpos);
					u.setYpos(ypos);
					userRepo.save(u);
				}
				result = result + player2 + " " + u.getXpos() + " " + u.getYpos() + " ";
			}
			
			Integer player3 = l.getPlayer3();
			if(player3 == 0) {
				result += "0 0 0 ";
			} else {
				u = userRepo.findById(player3).get();
				if (player3 == playerId){
					u.setXpos(xpos);
					u.setYpos(ypos);
					userRepo.save(u);
				}
				result = result + player3 + " " + u.getXpos() + " " + u.getYpos() + " ";
			}
			
			Integer player4 = l.getPlayer4();
			if(player4 == 0) {
				result += "0 0 0 ";
			} else {
				u = userRepo.findById(player4).get();
				if (player4 == playerId){
					u.setXpos(xpos);
					u.setYpos(ypos);
					userRepo.save(u);
				}
				result = result + player4 + " " + u.getXpos() + " " + u.getYpos() + " ";
			}
			return result;
		} catch (Exception e) {return e.toString();}
		
	}
}
