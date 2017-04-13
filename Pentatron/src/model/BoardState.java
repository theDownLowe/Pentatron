package model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Holds a single board state of a 6 x 6 Pentago game.
 *
 * @author Trevor N. Lowe
 * @version 1
 */
public class BoardState implements Comparable<BoardState>{
	
	private char[][] board;
	private char winningColor;
	private int heuristic;
	
	/** Initialize blank board state. **/
	public BoardState() {
		board = new char[4][9]; // 4 Blocks of 9 tiles each
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				board[i][j] = '.';
			}
		}
		winningColor = '.';
		heuristic = 0;
	}
	
	/**
	 * Checks whether the user has entered an illegal input.
	 * 	Whether or not the want to place a tile on am occupied space.
	 * 
	 * @return true if move is legal
	 */
	public boolean isLegal(int block, int pos, int rotBlock, char rot) {
		
		if (block < 0 || block > 3 || pos < 0 || pos > 8
				|| rotBlock < 0 || rotBlock > 3 || (rot != 'r' && rot != 'l')) {
			return false;
		}
		
		return board[block][pos] == '.';
	}
	
	/**
	 * Places a single tile onto the game board.
	 * 
	 * @param block location of position
	 * @param pos position inside block
	 * @param color color of piece being played
	 */
	public void placeTile(int block, int pos, char color) {
		board[block][pos] = color;
	}
	
	/**
	 * Rotates the given block in the given direction.
	 * 
	 * @param block Block to be rotated
	 * @param rotation Direction of rotation
	 */
	public void rotateBlock(int block, char rotation) {
		
		char p0 = board[block][0];
		char p1 = board[block][1];
		char p2 = board[block][2];
		char p3 = board[block][3];
		char p5 = board[block][5];
		char p6 = board[block][6]; 
		char p7 = board[block][7];
		char p8 = board[block][8];
		
		if (rotation == 'r') { // Rotate Clockwise
			board[block][0] = p6;
			board[block][1] = p3;
			board[block][2] = p0;
			board[block][3] = p7;
			board[block][5] = p1;
			board[block][6] = p8;
			board[block][7] = p5;
			board[block][8] = p2;
		} else { // Rotate Counter-Clockwise
			board[block][0] = p2;
			board[block][1] = p5;
			board[block][2] = p8;
			board[block][3] = p1;
			board[block][5] = p7;
			board[block][6] = p0;
			board[block][7] = p3;
			board[block][8] = p6;
		}
	}
	
	/**
	 * Returns true if game is over.
	 * 
	 * @return true if game over
	 */
	public boolean isGameOver() {
		
		boolean gameover = false;
		
		if (this.isFull()) {
			gameover = true;
		}
		
		boolean whiteWins = false;
		boolean blackWins = false;
		
		// Checks if there is a winner
		// check row 1
		if (board[0][1] != '.' && ((board[0][0] == board[0][1] 
				&& board[0][0] == board[0][2] && board[0][0] == board[1][0] 
				&& board[0][0] == board[1][1]) 
				|| (board[0][1] == board[0][2] && board[0][1] == board[1][0]
				&& board[0][1] == board[1][1] && board[0][1] == board[1][2]))) {
			
			gameover = true;
			if (board[0][1] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check row 2
		if (board[0][4] != '.' && ((board[0][3] == board[0][4] 
				&& board[0][3] == board[0][5] && board[0][3] == board[1][3] 
				&& board[0][3] == board[1][4]) 
				|| (board[0][4] == board[0][5] && board[0][4] == board[1][3]
				&& board[0][4] == board[1][4] && board[0][4] == board[1][5]))) {
			
			gameover = true;
			if (board[0][4] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check row 3
		if (board[0][7] != '.' && ((board[0][6] == board[0][7] 
				&& board[0][6] == board[0][8] && board[0][6] == board[1][6] 
				&& board[0][6] == board[1][7]) 
				|| (board[0][7] == board[0][8] && board[0][7] == board[1][6]
				&& board[0][7] == board[1][7] && board[0][7] == board[1][8]))) {

			gameover = true;
			if (board[0][7] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check row 4
		if (board[2][1] != '.' && ((board[2][0] == board[2][1] 
				&& board[2][0] == board[2][2] && board[2][0] == board[3][0] 
				&& board[2][0] == board[3][1]) 
				|| (board[2][1] == board[2][2] && board[2][1] == board[3][0]
				&& board[2][1] == board[3][1] && board[2][1] == board[3][2]))) {

			gameover = true;
			if (board[2][1] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check row 5
		if (board[2][4] != '.' && ((board[2][3] == board[2][4] 
				&& board[2][3] == board[2][5] && board[2][3] == board[3][3] 
				&& board[2][3] == board[3][4]) 
				|| (board[2][4] == board[2][5] && board[2][4] == board[3][3]
				&& board[2][4] == board[3][4] && board[2][4] == board[3][5]))) {

			gameover = true;
			if (board[2][4] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check row 6
		if (board[2][7] != '.' && ((board[2][6] == board[2][7] 
				&& board[2][6] == board[2][8] && board[2][6] == board[3][6] 
				&& board[2][6] == board[3][7]) 
				|| (board[2][7] == board[2][8] && board[2][7] == board[3][6]
				&& board[2][7] == board[3][7] && board[2][7] == board[3][8]))) {

			gameover = true;
			if (board[2][7] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check col 1
		if (board[0][3] != '.' && ((board[0][0] == board[0][3] 
				&& board[0][0] == board[0][6] && board[0][0] == board[2][0] 
				&& board[0][0] == board[2][3]) 
				|| (board[0][3] == board[0][6] && board[0][3] == board[2][0]
				&& board[0][3] == board[2][3] && board[0][3] == board[2][6]))) {

			gameover = true;
			if (board[0][3] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check col 2
		if (board[0][4] != '.' && ((board[0][1] == board[0][4] 
				&& board[0][1] == board[0][7] && board[0][1] == board[2][1] 
				&& board[0][1] == board[2][4]) 
				|| (board[0][4] == board[0][7] && board[0][4] == board[2][1]
				&& board[0][4] == board[2][4] && board[0][4] == board[2][7]))) {

			gameover = true;
			if (board[0][4] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check col 3
		if (board[0][5] != '.' && ((board[0][2] == board[0][5] 
				&& board[0][2] == board[0][8] && board[0][2] == board[2][2] 
				&& board[0][2] == board[2][5]) 
				|| (board[0][5] == board[0][8] && board[0][5] == board[2][2]
				&& board[0][5] == board[2][5] && board[0][5] == board[2][8]))) {

			gameover = true;
			if (board[0][5] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check col 4
		if (board[1][3] != '.' && ((board[1][0] == board[1][3] 
				&& board[1][0] == board[1][6] && board[1][0] == board[3][0] 
				&& board[1][0] == board[3][3]) 
				|| (board[1][3] == board[1][6] && board[1][3] == board[3][0]
				&& board[1][3] == board[3][3] && board[1][3] == board[3][6]))) {

			gameover = true;
			if (board[1][3] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check col 5
		if (board[1][4] != '.' && ((board[1][1] == board[1][4] 
				&& board[1][1] == board[1][7] && board[1][1] == board[3][1] 
				&& board[1][1] == board[3][4]) 
				|| (board[1][4] == board[1][7] && board[1][4] == board[3][1]
				&& board[1][4] == board[3][4] && board[1][4] == board[3][7]))) {

			gameover = true;
			if (board[1][4] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check col 6
		if (board[1][5] != '.' && ((board[1][2] == board[1][5] 
				&& board[1][2] == board[1][8] && board[1][2] == board[3][2] 
				&& board[1][2] == board[3][5]) 
				|| (board[1][5] == board[1][8] && board[1][5] == board[3][2]
				&& board[1][5] == board[3][5] && board[1][5] == board[3][8]))) {
			
			gameover = true;
			if (board[1][5] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check down diag
		if (board[0][4] != '.' && ((board[0][0] == board[0][4] 
				&& board[0][0] == board[0][8] && board[0][0] == board[3][0] 
				&& board[0][0] == board[3][4]) 
				|| (board[0][4] == board[0][8] && board[0][4] == board[3][0]
				&& board[0][4] == board[3][4] && board[0][4] == board[3][8]))) {
			
			gameover = true;
			if (board[0][4] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		// check up diag
		if (board[2][4] != '.' && ((board[2][6] == board[2][4] 
				&& board[2][6] == board[2][2] && board[2][6] == board[1][6] 
				&& board[2][6] == board[1][4]) 
				|| (board[2][4] == board[2][2] && board[2][4] == board[1][6]
				&& board[2][4] == board[1][4] && board[2][4] == board[1][2]))) {
			
			gameover = true;
			if (board[2][4] == 'W') {
				whiteWins = true;
			} else {
				blackWins = true;
			}
		}
		
		
		if (whiteWins == blackWins) {
			winningColor = '.'; 	// Tie
		} else if (whiteWins) {
			winningColor = 'W';
		} else {
			winningColor = 'B';
		}
		
		return gameover;
	}
	
	/**
	 * Returns true if board is full.
	 * 
	 * @return board is full
	 */
	public boolean isFull() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 9; j++) {
				if (board[i][j] == '.') {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the winning color of the current game state.
	 * 	Returns '.' if a tie occurred.
	 * 
	 * @return winning color
	 */
	public char getWinner() {
		return winningColor;
	}
	
	/**
	 * Calculates and returns the heuristic for the AI.
	 * 
	 * @param pentaColor Pentatron's color
	 * @return The heuristic of the board state 
	 */
	public int calculateHeuristic(char pentaColor) {
		heuristic = 0;
		heuristic += count2inRow(pentaColor); 
		
		return heuristic;
	}
	
	/**
	 * Counts the number of 2-in-a-rows of pentatron's color.
	 * 
	 * @param pentaColor Pentatron's color
	 * @return number of 2-in-a-rows
	 */
	private int count2inRow(char pentaColor) {
		int sum = 0;
		
		// Check horizontal 2-in-a-rows
		for (int i = 0; i < 4; i++) {
			for (int row1 = 0; row1 < 2; row1++) {
				if (board[i][row1] == board[i][row1 + 1] &&
						board[i][row1] == pentaColor) {
					sum++;
				}
			}
			for (int row2 = 3; row2 < 5; row2++) {
				if (board[i][row2] == board[i][row2 + 1] &&
						board[i][row2] == pentaColor) {
					sum++;
				}
			}
			for (int row3 = 6; row3 < 8; row3++) {
				if (board[i][row3] == board[i][row3 + 1] &&
						board[i][row3] == pentaColor) {
					sum++;
				}
			}
		}
		if (board[0][2] == board[1][0] &&
				board[0][2] == pentaColor) {
			sum++;
		}
		if (board[0][5] == board[1][3] &&
				board[0][5] == pentaColor) {
			sum++;
		}
		if (board[0][8] == board[1][6] &&
				board[0][8] == pentaColor) {
			sum++;
		}
		if (board[2][2] == board[3][0] &&
				board[2][2] == pentaColor) {
			sum++;
		}
		if (board[2][5] == board[3][3] &&
				board[2][5] == pentaColor) {
			sum++;
		}
		if (board[2][8] == board[3][6] &&
				board[2][8] == pentaColor) {
			sum++;
		}
		
		// Check vertical 2-in-a-rows
		for (int i = 0; i < 4; i++) {
			for (int col1 = 0; col1 < 6; col1++) {
				if (board[i][col1] == board[i][col1 + 3] &&
						board[i][col1] == pentaColor) {
					sum++;
				}
			}
		}
		if (board[0][6] == board[2][0] &&
				board[0][6] == pentaColor) {
			sum++;
		}
		if (board[0][7] == board[2][1] &&
				board[0][7] == pentaColor) {
			sum++;
		}
		if (board[0][8] == board[2][2] &&
				board[0][8] == pentaColor) {
			sum++;
		}
		if (board[1][6] == board[3][0] &&
				board[1][6] == pentaColor) {
			sum++;
		}
		if (board[1][7] == board[3][1] &&
				board[1][7] == pentaColor) {
			sum++;
		}
		if (board[1][8] == board[3][2] &&
				board[1][8] == pentaColor) {
			sum++;
		}
		
		// Count diagonals
		for (int i = 0; i < 4; i++) {
			if (board[i][0] == board[i][4] &&
					board[i][0] == pentaColor) {
				sum++;
			}
			if (board[i][1] == board[i][3] &&
					board[i][1] == pentaColor) {
				sum++;
			}
			if (board[i][1] == board[i][5] &&
					board[i][1] == pentaColor) {
				sum++;
			}
			if (board[i][2] == board[i][4] &&
					board[i][2] == pentaColor) {
				sum++;
			}
			if (board[i][6] == board[i][4] &&
					board[i][6] == pentaColor) {
				sum++;
			}
			if (board[i][7] == board[i][3] &&
					board[i][7] == pentaColor) {
				sum++;
			}
			if (board[i][7] == board[i][5] &&
					board[i][7] == pentaColor) {
				sum++;
			}
			if (board[i][8] == board[i][4] &&
					board[i][8] == pentaColor) {
				sum++;
			}
		}

		if (board[2][2] == board[1][6] &&
				board[2][2] == pentaColor) {
			sum++;
		}
		if (board[0][8] == board[3][0] &&
				board[0][8] == pentaColor) {
			sum++;
		}

			
		return sum;
	}
	
	public Queue<BoardState> getChildren(char color) {
		
		Queue<BoardState> childQueue = new LinkedList<BoardState>();
		
		for (int block = 0; block < 4; block++) {
			for (int pos = 0; pos < 9; pos++) {
				for (int rB = 0; rB < 4; rB++) {
					for (int rot = 0; rot < 2; rot++) {
						// Copy parent to child
						BoardState newChild1 = new BoardState();
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 9; j++) {
								if (board[i][j] == 'W') {
									newChild1.placeTile(i, j, 'W');
								} else if (board[i][j] == 'B'){
									newChild1.placeTile(i, j, 'B');
								}
							}
						}
						BoardState newChild2 = new BoardState();
						for (int i = 0; i < 4; i++) {
							for (int j = 0; j < 9; j++) {
								if (board[i][j] == 'W') {
									newChild2.placeTile(i, j, 'W');
								} else if (board[i][j] == 'B'){
									newChild2.placeTile(i, j, 'B');
								}
							}
						}
						if(board[block][pos] == '.') {	
							if (rot == 0) {
								newChild1.placeTile(block, pos, color);
								childQueue.add(newChild1);
								newChild2.rotateBlock(block, 'l');
								childQueue.add(newChild2);
							} else {
								newChild1.placeTile(block, pos, color);
								childQueue.add(newChild1);
								newChild2.rotateBlock(block, 'r');
								childQueue.add(newChild2);
							}
						}
					}
				}
			}
		}
		
		return childQueue;
	}
	
	/**
	 * Returns the heuristic of the board.
	 * 
	 * @return heuristic of the board
	 */
	public int getHeuristic() {
		return heuristic;
	}
	
	public int compareTo(BoardState other) {
		if (this.heuristic > other.heuristic) {
			return 1;
		} else if (this.heuristic < other.heuristic) {
			return -1;
		}
		
		return 0;
	}
	
	/** Prints this boards state. */
	public void printBoard() {
		System.out.println("+-------+-------+");
		System.out.println("| " + board[0][0] + " " + board[0][1] + " " + board[0][2] +
				           " | " + board[1][0] + " " + board[1][1] + " " + board[1][2] + " |"); 
		System.out.println("| " + board[0][3] + " " + board[0][4] + " " + board[0][5] +
						   " | " + board[1][3] + " " + board[1][4] + " " + board[1][5] + " |");
		System.out.println("| " + board[0][6] + " " + board[0][7] + " " + board[0][8] +
				           " | " + board[1][6] + " " + board[1][7] + " " + board[1][8] + " |");
		System.out.println("+-------+-------+");
		System.out.println("| " + board[2][0] + " " + board[2][1] + " " + board[2][2] +
				           " | " + board[3][0] + " " + board[3][1] + " " + board[3][2] + " |");
		System.out.println("| " + board[2][3] + " " + board[2][4] + " " + board[2][5] +
				           " | " + board[3][3] + " " + board[3][4] + " " + board[3][5] + " |");
		System.out.println("| " + board[2][6] + " " + board[2][7] + " " + board[2][8] +
				           " | " + board[3][6] + " " + board[3][7] + " " + board[3][8] + " |");
		System.out.println("+-------+-------+");
	}
}
