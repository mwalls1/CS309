package com.mygdx.games;

import java.util.Scanner;

import util.JsonParser;
public class serverThread extends Thread{
	private Scanner scan;
	public void run(Player player1, Player player2)
	{
			try {
				String s2 = JsonParser.getHTML("http://coms-309-tc-1.misc.iastate.edu:8080/getPosByID?id=45");
				scan = new Scanner(s2);
				player2.setPos(scan.nextInt(), scan.nextInt());
				scan.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				JsonParser.sendHTML("updatePos", "id=46&xpos=" + player1.getX() + "&ypos=" + player1.getY());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
