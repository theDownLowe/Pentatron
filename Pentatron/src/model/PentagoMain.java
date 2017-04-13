package model;

import java.util.Random;
import java.util.Scanner;

/**
 * Plays a game of Pentago against the user.
 * 
 * @author Trevor N. Lowe
 * @version 1
 */
public class PentagoMain {
	/**
	 * Main method for the Pentago game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Repeats until player wants to quit
		Scanner console = new Scanner(System.in);
		
		System.out.println("Welcome to PENTAGO!!!\n");
		System.out.println("My name is Pentatron, what's yours?");
		String pName = console.next();
		
		boolean playAgain = true;
		while (playAgain) {
			playAgain = printMenu(console, pName);
			System.out.println();
		};
		console.close();
		System.out.println("Thanks for Playing! Goodbye!");
	}
	
	/** Basically starts the game, picking who goes first and who gets what color.
	 * 
	 * 	@param Scanner the console input
	 *  @return true if user wants to play again
	 *  **/
	public static boolean printMenu(Scanner console, String pName) {
		Random rand = new Random();
		
		char pentaColor; 
		char playerColor;
		
		if (rand.nextInt(2) == 0) {
			pentaColor = 'B'; // Black
			playerColor = 'W'; // White
		} else {
			pentaColor = 'W'; // White
			playerColor = 'B'; // Black
		}
		

		int starting = rand.nextInt(2);
		if (starting == 0) {
			System.out.println("Pentatron\n" + pName);
			System.out.println(pentaColor + "\n" + playerColor);
		} else {
			System.out.println(pName + "\nPentatron");	
			System.out.println(playerColor + "\n" + pentaColor);
		}
		
		// Player to move next
		System.out.println("1");
		
		
		System.out.println("+-------+-------+");
		System.out.println("| . . . | . . . |"); 
		System.out.println("| . . . | . . . |");
		System.out.println("| . . . | . . . |");
		System.out.println("+-------+-------+");
		System.out.println("| . . . | . . . |");
		System.out.println("| . . . | . . . |");
		System.out.println("| . . . | . . . |");
		System.out.println("+-------+-------+");
			
		PentagoGame game = new PentagoGame(starting, pentaColor, playerColor, console);
		
		char winner = game.getWinner();
		if (playerColor == winner) {		// Player Wins
			System.out.println("Congratulations! You Won!");
		} else if (pentaColor == winner) { 	// Pentatron Wins
			System.out.println("Sorry, Pentatron Won. Better Luck Next Time.");
		} else {							// Tie
			System.out.println("You and Pentatron Tied.");
		}
		
		System.out.print("Would you like to play again? (y/n): ");
		String s = console.next();
		
		return Character.toLowerCase(s.charAt(0)) == 'y';
	}
}
