package core;
import java.util.Arrays;

public class ProbCalculator {
	private Card[] compCards;
	private Card[] shownCards;
	private final int SUITS = 4; //diamond, heart, club, spade
	private final int CARDS = 13; //2 - 10, J - A
	
	public ProbCalculator (Card[] compCards, Card[] shownCards) {
		this.compCards = compCards;
		this.shownCards = shownCards;
	}
}

//http://paginas.fe.up.pt/~niadr/PUBLICATIONS/LIACC_publications_2011_12/pdf/CN10_Estimating_Probability_Winning_LFT.pdf
public double probWin () {
	int ahead = 0;
	int tied = 0;
	int behind = 0;
	
	int compRank = rank(compCards, shownCards);
	
	int numShown = shownCards.length;
	int numCards = compCards.length;
	
	//possible user cards
	int numUserCards = SUITS * CARDS - shownCards.length - compCards.length;
	Card[] userCards = new int[numUserCards];
	
	int count = 0;
	for (int i = 0; i < SUITS; i++) {
		for (int j = 0; j < CARDS; j++) {
			for (Card c : compCards) {
				if (c.getNumber() == j && c.getSuit() = i) break;
			}
			
			for (Card c : shownCards) {
				if (c.getNumber() == j && c.getSuit() == i) break;
			}
			
			userCards[count] = new Card(j, i);
			count++;
		}
		
	}
	
	for (int i = 0; i < numUserCards; i++) {
		for (int j = 0; j < numUserCards; j++)
			if (i != j) {
				Card[] userPossibility = new Card[2];
				Card[0] = userCards[i];
				Card[1] = userCards[j];
			}
		int userRank = rank(userPossibility, shownCards);
		
	}
}
function HandStrength(ourcards, boardcards){
	 ahead = tied = behind = 0
	 ourrank = Rank(ourcards, boardcards)
	 /*Consider all two-card combinations of remaining
	cards*/
	 for (int i = 0; i < 4; i++)) {
	 opprank = Rank(oppcards, boardcards)
	 if(ourrank > opprank) ahead++
	 else if (ourrank == opprank) tied++
	 else behind++
	 }
	 return (ahead + tied / 2) / (ahead + tied + behind)
	}
