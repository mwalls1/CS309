package com.mygdx.ColesGames;


public class ConnectFourLogic {
	
	private String winner;
	
	ConnectFourLogic(){
		winner = "empty";
	}
	
	public String getWinner() {
		return winner;
	}
	
	/**
	 * returns true if the game is over
	 * assigns the value of the private variable winner
	 * with the output string for when game is won
	 * @param z
	 * 			used for mocking to check method
	 * @return
	 * 			return true for game over
	 */
	public boolean checkGameOver(Zone[][] z, boolean isGameOver) {
		int row, col;
		if(isGameOver == true) return false;
		
		int sum = 0;
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if(z[i][j].getTile().equals("yellow") || z[i][j].getTile().equals("red")) sum++;
			}
		}
		if(sum == 42) {
			winner = "board is full\nPress R to replay or ESCAPE to return to main menu";
			return true;
		}
		
		// check 4 in a row (horizontal)
		int rowPlus4 = 0;
		int colPlus4 = 0;
		for(row = 0; row < 6; row++) {
			for(col = 0; col < 4; col++) {
				if(z[row][col].getTile().equals("red") && z[row][col+1].getTile().equals("red") && z[row][col+2].getTile().equals("red") && z[row][col+3].getTile().equals("red")) {
					colPlus4 = col+4;
					winner = "Game is over, red won at row:" + row + " col:" + col + "-" + colPlus4 + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(z[row][col].getTile().equals("yellow") && z[row][col+1].getTile().equals("yellow") && z[row][col+2].getTile().equals("yellow") && z[row][col+3].getTile().equals("yellow")) {
					colPlus4 = col+4;
					winner = "Game is over, yellow won at row:" + row + " col:" + col + "-" + colPlus4 + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		// check 4 in a column (vertical)
		for(col = 0; col < 7; col++) {
			for(row = 0; row < 3; row++) {
				if(z[row][col].getTile().equals("red") && z[row+1][col].getTile().equals("red") && z[row+2][col].getTile().equals("red") && z[row+3][col].getTile().equals("red")) {
					rowPlus4 = row+4;
					winner = "Game is over, red won at row:" + row + "-" + rowPlus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(z[row][col].getTile().equals("yellow") && z[row+1][col].getTile().equals("yellow") && z[row+2][col].getTile().equals("yellow") && z[row+3][col].getTile().equals("yellow")) {
					rowPlus4 = row+4;
					winner = "Game is over, yellow won at row:" + row + "-" + rowPlus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		// check 4 diagonal (left to right bottom to top : 45 degrees : positive slope)
		for(row = 0; row < 3; row++) {
			for(col = 0; col < 4; col++) {
				if(z[row][col].getTile().equals("red") && z[row+1][col+1].getTile().equals("red") && z[row+2][col+2].getTile().equals("red") && z[row+3][col+3].getTile().equals("red")) {
					winner = "Game is over, red won diagonally starting at row:" + row + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(z[row][col].getTile().equals("yellow") && z[row+1][col+1].getTile().equals("yellow") && z[row+2][col+2].getTile().equals("yellow") && z[row+3][col+3].getTile().equals("yellow")) {
					winner = "Game is over, yellow won diagonally starting at row:" + row + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		// check 4 diagonal (left to right top to bottom : -45 degrees : negative slope)
		for(row = 0; row < 3; row++) {
			for(col = 0; col < 4; col++) {
				if(z[row+3][col].getTile().equals("red") && z[row+2][col+1].getTile().equals("red") && z[row+1][col+2].getTile().equals("red") && z[row][col+3].getTile().equals("red")) {
					rowPlus4 = row+4;
					colPlus4 = col+4;
					winner = "Game is over, red won diagonally starting at row:" + rowPlus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				} else if(z[row+3][col].getTile().equals("yellow") && z[row+2][col+1].getTile().equals("yellow") && z[row+1][col+2].getTile().equals("yellow") && z[row][col+3].getTile().equals("yellow")) {
					rowPlus4 = row+4;
					colPlus4 = col+4;
					winner = "Game is over, yellow won diagonally starting at row:" + rowPlus4 + " col:" + col + "\nPress R to replay or ESCAPE to return to main menu";
					System.out.println(winner);
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * returns the lowest row that is not active
	 * @param arr
	 * 			index 0 is row, index 1 is column
	 * @return
	 * 			return array is the same format as parameter
	 */
	public int[] findLowestTile(int[] arr, Zone[][] z) {
		if (arr[0] == 0)
			return arr;
		int row = arr[0];
		int col = arr[1];
		for (int i = row; i > 0; i--) {
			if (!z[row - 1][col].isActive()) {
				row--;
			}
		}
		arr[0] = row;
		arr[1] = col;
		return arr;
	}
	
	
	
}
