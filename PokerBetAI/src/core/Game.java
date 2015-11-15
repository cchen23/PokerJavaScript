package core;

import java.io.File;
import java.util.Scanner;

public class Game {
	private final int BIGBLIND = 20;
	private final int SMALLBLIND = 10;
	private Deck deck;
	private Card[] player;
	private Card[] cpu;
	private Card[] board;
	private int boardCards = 0;
	private int round;
	private int playerchips;
	private int cpuchips;
	private int blinds;
	private int pot;
	private int matchAmt = BIGBLIND - SMALLBLIND;
	File history;
	
	public Game (int r, int pchips, int cchips) {
		//player is on button during even rounds;
		round = r;
		player = new Card[2];
		cpu = new Card[2];
		board = new Card[5];
		deck = new Deck();
		playerchips = pchips;
		cpuchips = cchips;
		blinds = 10;		
		pot = 0;
		
		printGameState();
	}
	
	//returns int array result. result[0] gives user chips left. result[1] 
	//gives computer chips left. result[2] gives pot. result[3] is 1 if
	//computer won, -1 if user won.
	//To Do: also writes game history to text file
	public int[] playGame() {
		int[] result = new int[3];
		
		int fold = preflop();
		if (fold == 1) {
			result[0] = playerchips;
			result[1] = cpuchips;
			result[2] = pot;
			result[3] = -1;
			return result;
		}
		if (fold == -1) {
			result[0] = playerchips;
			result[1] = cpuchips;
			result[2] = pot;
			result[3] = 1;
			return result;
		}
		
		postflop();
		if (fold == 1) {
			result[3] = -1;
		}
		else if (fold == -1) {
			result[3] = 1;
		}
		else {
			result[3] = cmp(player, cpu);
		}
		
		result[0] = playerchips;
		result[1] = cpuchips;
		result[2] = pot;
		return result;
		
	}
	//returns -1 if computer folds, 1 if user folds, 0 otherwise
	public int preflop() {
		System.out.println("PREFLOP");
		System.out.println("Cards dealt.");
		//deal 2 cards to each player
		player[0] = deck.deal();
		cpu[0] = deck.deal();
		player[1] = deck.deal();
		cpu[1] = deck.deal();
		
		//take blinds
		pot += blinds * 3;
		
		if (round % 2 == 0) {
			System.out.println("Dealer: Player");
			playerchips -= SMALLBLIND;
			cpuchips -= BIGBLIND;
		}
		else {
			System.out.println("Dealer: Computer");
			playerchips -= BIGBLIND;
			cpuchips -= SMALLBLIND;
		}
		
		System.out.println("Blinds taken.");
		printGameState();
		System.out.println("Board is empty");
		
		//round of betting
		int fold = bettingRound();	 
		return fold;
	}
	
	//returns -1 if computer folds, 1 if user folds, 0 otherwise
	public int postflop() {
		System.out.println("POSTFLOP");
		
		board[0] = deck.deal();
		board[1] = deck.deal();
		board[2] = deck.deal();
		boardCards += 3;
		printGameState();
		int fold = bettingRound();
		if (fold == 1 || fold == -1) return fold;
		
		board[4] = deck.deal();
		boardCards++;
		printGameState();
		fold = bettingRound();
		if (fold == 1 || fold == -1) return fold;
		
		board[5] = deck.deal();
		boardCards++;
		printGameState();
		fold = bettingRound();
		return fold;
	}
	
	//returns user bet. returns -1 if user folds
	public int playerBet(int matchAmt) {
		printGameState();
		System.out.println("Raise, call, or fold?");
		Scanner s = new Scanner(System.in);
		String choice = s.next().toLowerCase();
		
		while (true) {
			if (choice.equals("raise")) {
				System.out.println("Enter bet amount: ");
				int bet = s.nextInt();
				while (bet > playerchips) {
					System.out.println("Invalid bet. Enter bet amount: ");
					bet = s.nextInt();
				}
				pot += bet;
				playerchips -= bet;
				return bet;
			}
			else if (choice.equals("call")) {
				if (playerchips >= matchAmt) {
					pot += matchAmt;
					playerchips -= matchAmt;
					return matchAmt;
				}
				else {
					pot += playerchips;
					playerchips = 0;
					return playerchips;
				}
			}
			else if (choice.equals("fold")) {
				return -1;
			}
			else {
				System.out.println("Invalid choice");
			}
		}
	}
	
	public int computerBet() {
		BetCalculator bc = new BetCalculator(history);
		return bc.getBet(cpu, board, cpuchips, pot, matchAmt);
	}
	
	//returns 0 if no one folds. returns -1 if computer folds.
	//returns 1 if user folds.
	public int bettingRound() {
		int playerTotal = 0;
		int cpuTotal = 0;
		int betcycle = 0;
		
		while (playerTotal != cpuTotal || betcycle < 2 ){
			if (round % 2 == 0) {
				int playerBet = playerBet(matchAmt);
				if (playerBet == -1) return 1;
				playerTotal += playerBet;
				matchAmt = Math.abs(playerTotal - cpuTotal);
				
				int compBet = computerBet();
				if (compBet == -1) return -1;
				cpuTotal += computerBet();
				matchAmt = Math.abs(playerTotal - cpuTotal);
			}
			else {
				int compBet = computerBet();
				if (compBet == -1) return -1;
				cpuTotal += computerBet();
				matchAmt = Math.abs(playerTotal - cpuTotal);
				
				int playerBet = playerBet(matchAmt);
				if (playerBet == -1) return 1;
				playerTotal += playerBet;
				matchAmt = Math.abs(playerTotal - cpuTotal);
			}
			betcycle++;
		}
		return 0;
	}
	
	public void printGameState() {
		System.out.println("Your cards: " + player[0] + ", " + player[1]);
		System.out.println("Your chips: " + playerchips);
		System.out.println("Pot: " + pot);
		if (boardCards == 0) System.out.println("Board is empty.");
		else {
			System.out.print("Board: ");
			for (int i = 0; i < boardCards; i++) {
				System.out.print(board[i] + " ");
			}
			System.out.println();
		}
	}
	
	//returns 1 if cpu has better hand, -1 if player has better hand
	public int cmp(cpu, player) {
		return 0;
	}
}
	
	
	
	
	

