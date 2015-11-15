package core;

import java.io.BufferedReader;

/*****************************************************************************/
/* Reads in file of previous games played. Based on previous games and       */
/* cards, generates computer's bid.                                          */
/* Previous games log in the format:                                         */
/* game# bluff/not                                                           */
/*****************************************************************************/
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BetCalculator {
	
	double probBluff;
	
	//read in and store data based on previous hands
	//Note: currently just a generic probability. Maybe expand later.
	public BetCalculator(File prevHands) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(prevHands));
		
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
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	//calculate bet
	public int getBet(Card[] compCards, Card[] shownCards, int numChips, 
			int pot, int matchAmt) {
				
		//calculate bet
		int bet = calcBet(compCards, shownCards, pot, matchAmt);
		
		//return bet
			//if less than computer's number of chips, return numChips
			if (bet >= numChips) return numChips;
			else return bet;
	}
	
	//Returns computer bet based on bluff probability and cards known. Returns
	//-1 if computer folds.
	public int calcBet(Card[] compCards, Card[] shownCards, int pot, 
			int matchAmt) {
		double primaryBet = primaryBet(compCards, shownCards, pot);
		
		boolean bluffing = false;

		double bluffProb = bluffProb();
		if (bluffProb > 0.5) bluffing = true;
		
		if (bluffing) {
			return (int) Math.round(Math.max(primaryBet, matchAmt) + 0.5);
		}
		
		if (Math.round(primaryBet + 0.5) < matchAmt) return -1;
		return (int) Math.round(primaryBet + 0.5);

	}
	
	//Return probability of bluffing
	//Add learning based on past inputs
	private double bluffProb() {
		return 1.0 / 2;
	}
	
	//Return computer bet based on known cards
	private double primaryBet(Card[] compCards, Card[] shownCards, int pot) {
		double probWin = probWin(compCards, shownCards);
		return pot * probWin;
	}
	
	//Return probability of win
	private double probWin(Card[] compCards, Card[] shownCards) {
		ProbCalculator prob = new ProbCalculator(compCards, shownCards);
		return prob.probWin();
	}
}
