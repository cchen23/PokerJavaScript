package core;

import java.io.BufferedReader;

/*****************************************************************************/
/* Reads in file of previous games played. Based on previous games and       */
/* cards, generates computer's bid.                                          */
/* Previous games log in the format:                                         */
/* game# bluff/not                                                           */
/*****************************************************************************/
 
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BetCalculator {
	
	double probBluff;
	
	//read in and store data based on previous hands
	//Note: currently just a generic probability. Maybe expand later.
	public BetCalculator(File prevHands) {
		BufferedReader br = new BufferedReader(new FileReader(prevHands));
		
		String line = null;
		
		int numGames = 0;
		int numBluffs = 0;
		
		try {
			while ((line = br.readLine()) != null) {
				String[] gameData = line.split(" ");
				if (gameData[1].equals("bluff")) numBluffs++;
				numGames++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		probBluff = numBluffs / numGames;
	}
	//calculate bet
	public int getBet(Game game) {
		//get computer's cards
		Card[] compCards = game.getCompCards();
		
		//get shown cards
		Card[] shownCards = game.getShownCards();
		
		//get computer's chip amount
		int numChips = game.getCompChips();
		
		//calculate bet
		int bet = calcBet(compCards, shownCards, probBluff);
		
		//get user bet
		int pot = game.getPot();
		
		//return bet
			//if less than computer's number of chips, return numChips
			if (bet >= numChips) return numChips;
			else return bet;
	}
	
	public int calcBet(Card[] compCards, Card[] shownCards, int pot) {
		int primaryBet = primaryBet(compCards, shownCards, pot);
	}
	
	private double primaryBet(Card[] compCards, Card[] shownCards, int pot) {
		double probWin = probWin(compCards, shownCards);
		return pot * probWin;
	}
	
	double probWin(Card[] compCards, Card[] shownCards) {
		ProbCalculator prob = new ProbCalculator (compCards, shownCards);
	}
}
