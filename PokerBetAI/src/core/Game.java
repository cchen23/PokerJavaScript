package core;

import java.util.Scanner;

public class Game {
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
	private int matchAmt;
	
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
			playerchips -= 10;
			cpuchips -=20;
		}
		else {
			System.out.println("Dealer: Computer");
			playerchips -=20;
			cpuchips -=10;
		}

		System.out.println("Blinds taken.");
		printGameState();
		System.out.println("Board is empty");
		
		//round of betting
		bettingRound();	 
	}
	
	public int playerBet() {
		printGameState();
		System.out.print("Player bet: ");
		Scanner s = new Scanner(System.in);
		int pb = s.nextInt(); 
		//catch if invalid
		System.out.println("\n");
		pot += pb;
		playerchips -= pb;
		return pb;
	}
	
	public int computerBet() {
		//return bet from BetCalculator class
	}
	
	public void bettingRound(int ) {
		int playerTotal = 0;
		int cpuTotal = 0;
		int betcycle = 0;
		
		//who goes first
		//first two turns (1 complete round)
		//prompt user w/ check, fold, raise
			//p2 can only check if p1 checks
			//fold = game over. all money goes to other person
			//raise
		
		while (playerTotal != cpuTotal || betcycle < 2 ){
			if (round % 2 == 0) {
				playerTotal += playerBet(); //1 = reg, 0 = allin, -1 = fold
				cpuTotal += computerBet();
				matchAmt = Math.abs(playerTotal - cpuTotal);
			}
			else {
				cpuTotal += computerBet();
				playerTotal += playerBet();
				matchAmt = Math.abs(playerTotal - cpuTotal);
			}
			betcycle++;
		}		
	}
	
	public Card[] getCompCards() {
		return cpu;
	}
	
	public Card[] getShownCards() {
		return board;
	}
	
	public int getCompChips() {
		return cpuchips;
	}
	
	public int getPot() {
		return pot;
	}
	
	public int getMatchAmt() {
		return matchAmt;
	}
	public void printGameState() {
		System.out.println("Your cards: " + player[0] + ", " + player[1]);
		System.out.println("Your chips: " + playerchips);
		System.out.println("Pot: " + pot);
	}
}
	
	
	
	
	

