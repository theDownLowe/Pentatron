package model;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * Runs a Pentago game.
 * 
 * @author Trevor N. Lowe
 * @version 1
 */
public class PentagoGame {

	private char pentaColor;
	private char playerColor;
	private BoardState board;
	private char winner;
	private Scanner console;
	
	// Pentatrons next move
	private PriorityQueue<BoardState> bestMove;
	private int pBlock;
	private int pPos;
	private int pRBlock;
	private char pRot;
	
	/**
	 * Creates a new instance of a Pentago game.
	 * 
	 * @param starting
	 * @param pentaColor
	 * @param playerColor
	 */
	public PentagoGame(int starting, char pentaColor, char playerColor, Scanner console) {
		this.pentaColor = pentaColor;
		this.playerColor = playerColor;
		board = new BoardState();
		winner = '.';
		this.console = console;
		bestMove = new PriorityQueue<BoardState>();
		pBlock = 0;
		pPos = 0;
		pRBlock = 0;
		pRot = 'l';
		
		if (starting == 0) { // Pentatron starts
			pentasMove();
		} else { // Player starts
			playersMove();
		}
	}
	
	/** Method for pentas turn. **/
	private void pentasMove() {
		
		System.out.println("Pentatron's turn");
		
		// Use minimax with several-move look-ahead and alpha-beta pruning
		alphabeta(board, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		
		// Place tile
		board.placeTile(pBlock, pPos, pentaColor);
		

		if (board.isGameOver()) {
			board.printBoard();
			winner = board.getWinner();
		} else {	// Rotate block
			board.rotateBlock(pRBlock, pRot);
			board.printBoard();
			if (board.isGameOver()) {
				winner = board.getWinner();
			} else {
				playersMove();
			}
		}
	}
	
	public int alphabeta(BoardState bs, int depth, int alpha, int beta, boolean maxPlayer) { 
	    if  (depth == 0 || bs.isGameOver()) {
	    	if (bs.isGameOver() && board.getWinner() == pentaColor) {
	    		return 500;
	    	} else if (bs.isGameOver() && board.getWinner() == playerColor) {
	    		return -500;
	    	} else if (bs.isGameOver()) {
	    		return 0;
	    	}
	    	
	        return bs.calculateHeuristic(pentaColor);
	    }
	    

	    
	    if  (maxPlayer) {	// Maximizing Player
	    	
	    	int min = Integer.MIN_VALUE;
		    Queue<BoardState> children = bs.getChildren(pentaColor);
	    	
	        while (!children.isEmpty()) {
	        	BoardState child = children.poll();
	            min = Math.max(min, alphabeta(child, depth-1, alpha, beta, false));
	            alpha = Math.max(alpha, min);
	            
	            if (beta <= alpha) {
	                return min; // Pruned
	            }
	            
	            bestMove.add(child);
	        }
	        
	        return min;
	        		
	    } else {			// Minimizing Player
	    	
	    	int max = Integer.MAX_VALUE;
		    Queue<BoardState> children = bs.getChildren(playerColor);
	    	
	        while (!children.isEmpty()) {
	        	BoardState child = children.poll();
	            max = Math.min(max, alphabeta(child, depth-1, alpha, beta, true)) ;
	            beta = Math.min(beta, max);
	            if (beta <= alpha) {
	                return max; // Pruned
	            }
	            
	            bestMove.add(child);
	        }
	        
	        return max;
	    }
	}
	
	/** Method for players turn. **/
	private void playersMove() {
		int block = 0;
		int position = 0;
		int rotBlock = 0;
		char rot = 'r';
		
		// Checks legality of input
		boolean isLegal = false;
		while (!isLegal) {
			String s1 = console.next();
			String s2 = console.next();
			
			block = Integer.parseInt("" + s1.charAt(0)) - 1; 
			position = Integer.parseInt("" + s1.charAt(2)) - 1;
			rotBlock = Integer.parseInt("" + s2.charAt(0)) - 1;
			rot = Character.toLowerCase(s2.charAt(1));
			
			isLegal = board.isLegal(block, position, rotBlock, rot);
			if (!isLegal) {
				System.out.println("Move Invalid, Please Re-Enter a Valid Move: ");
			}
		}	
	
		// Place tile
		board.placeTile(block, position, playerColor);

		if (board.isGameOver()) {
			board.printBoard();
			winner = board.getWinner();
		} else {	// Rotate block
			board.rotateBlock(rotBlock, rot);
			board.printBoard();
			if (board.isGameOver()) {
				winner = board.getWinner();
			} else {
				pentasMove();
			}
		}
	}
	
	/**
	 * Returns the winning color.
	 * 
	 * @return winning color
	 */
	public char getWinner() {
		return winner;
	}
}
