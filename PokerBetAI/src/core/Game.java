package core;

import java.io.File;
import java.util.Scanner;

public class Game {
	private final int BIGBLIND = 20;
	private final int SMALLBLIND = 10;
	private Deck deck;
	private Card[] player;
	private Card[] cpu;
	private Card[] board; //toString method/print manually
	private int round;
	private int playerchips;
	private int cpuchips;
	private boolean allIn;
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
		allIn = false;
		blinds = 10;		
		pot = 0;
		
		printGameState();
	}
	
	public void preflop() {
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
	}
	
	//returns user bet. returns -1 if user folds
	public int playerBet(int matchAmt) {
		printGameState();
		System.out.println("Raise, call, or fold?");
		Scanner s = new Scanner(System.in);
		String choice = s.next().toLowerCase();
		
		if (choice.equals("raise")) {
			System.out.println("Enter bet amount: ");
			int bet = s.nextInt();
			while (bet > playerchips) {
				System.out.println("Invalid bet. Enter bet amount: ");
				int bet = s.nextInt();
			}
			pot += pb;
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
		
		//first two turns (1 complete round)
		//prompt user w/ check, fold, raise
			//p2 can only check if p1 checks
			//fold = game over. all money goes to other person
			//raise
		
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
				cpuTotal += computerBet();
				matchAmt = Math.abs(playerTotal - cpuTotal);
				playerTotal += playerBet(matchAmt);
				matchAmt = Math.abs(playerTotal - cpuTotal);
			}
			betcycle++;
		}		
	}
	
	public void printGameState() {
		System.out.println("Your cards: " + player[0] + ", " + player[1]);
		System.out.println("Your chips: " + playerchips);
		System.out.println("Pot: " + pot);
	}
}
	
	
	
	
	

