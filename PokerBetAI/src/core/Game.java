package sourcepkg;

import java.util.Scanner;

public class Game {
	private Deck deck;
	private Card[] player;
	private Card[] cpu;
	private Card[] board;
	private int round;
	private int playerchips;
	private int cpuchips;
	private boolean allIn;
	private int blinds;
	private int pot;
	
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
		if (round%2 == 0) {
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
		System.out.print("Player bet: ");
		Scanner s = new Scanner(System.in);
		int pb = s.nextInt();
		System.out.println("\n");
		pot += pb;
		playerchips -= pb;
		return pb;
	}
	
	public int computerBet() {
		return 0;
	}
	
	public void bettingRound(int ) {
		int playerTotal = 0;
		int cpuTotal = 0;
		int betcycle = 0;
		
		while (playerTotal != cpuTotal && betcycle < 2) {
			if (round % 2 == 0) {
				playerTotal += playerBet();
				cpuTotal += computerBet();  
			}
			else {
				cpuTotal += computerBet();
				playerTotal += playerBet();
			} 
			
		}
	}
	
	public void printGameState() {
		System.out.println("Your cards: " + player[0] + ", " + player[1]);
		System.out.println("Your chips: " + playerchips);
		System.out.println("Pot: " + pot);
	}
	}
	
	
	
	
	

