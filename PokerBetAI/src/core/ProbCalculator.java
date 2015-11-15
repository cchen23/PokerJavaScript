	package core;
	import java.util.Arrays;
	
public class ProbCalculator {
	private Card[] compCards;
	private Card[] shownCards;
	private final int SUITS = 4; //diamond, heart, club, spade
	private final int CARDS = 13; //2 - 10, J - A
	private final int HAND = 2;
	private int numUnknownCards; //cards w/ unknown vals (in deck or user hand)
	private int numShown;
		
	public ProbCalculator (Card[] compCards, Card[] shownCards) {
		this.compCards = compCards;
		this.shownCards = shownCards;
		numShown = shownCards.length;
		numUnknownCards = SUITS * CARDS - numShown - HAND;
	}
	
	//http://paginas.fe.up.pt/~niadr/PUBLICATIONS/LIACC_publications_2011_12/pdf/CN10_Estimating_Probability_Winning_LFT.pdf
	public double probWin () {
		int ahead = 0;
		int tied = 0;
		int behind = 0;
		
		int compRank = rank(compCards, shownCards);
		
		//possible user cards
		int numUserCards = SUITS * CARDS - numShown - HAND;
		Card[] userCards = new Card[numUserCards];
		
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
		
		//probability that comp hand is better than user hand for possible user hand
		for (int i = 0; i < numUserCards; i++) {
			for (int j = 0; j < numUserCards; j++)
				if (i != j) {
					Card[] userPossibility = new Card[2];
					userPossibility[0] = userCards[i];
					userPossibility[1] = userCards[j];
					int userRank = rank(userPossibility, shownCards);
					if (compRank > userRank) ahead++;
					else if (compRank == userRank) tied++;
					else behind++;
				}
		}
		 return (ahead + tied / 2) / (ahead + tied + behind)
		}
	
	//assign rank based on hand strength. APPROXIMATION
	//__Value = weight given to each type of hand
	//__Prob = probability of having __ type of hand
	public int rank(Card[] playerCards, Card[] shownCards) {		
		
		double highValue = 1;
		for (Card c : playerCards) {
			if (c.getNumber() highValue) {
				highValue = c.getNumber();
			}
		}
		highValue /= 13;
		double sumProb = 1;
		
		int pairValue = 2;
		double pairProb = pairProb(playerCards, shownCards);		
		
		int twoPairValue = 3;
		double twoPairProb = twoPairProb(playerCards, shownCards);
		if (twoPairProb == 1) pairProb = 0; //so we don't overcount the pairs
		
		int threeKindValue = 4;
		double threeKindProb = threeKindProb(playerCards, shownCards);
		
		int straightValue = 5;
		double straightProb = straightProb(playerCards, shownCards);
		
		int flushValue = 6;
		double flushProb = flushProb(playerCards, shownCards);
		
		int fullHouseValue = 7;
		double fullHouseProb = fullHouseProb(playerCards, shownCards);
		
		int fourKindValue = 8;
		double fourKindProb = fourKindProb(playerCards, shownCards);
		
		int straightFlushValue = 9;
		double straightFlushProb = straightFlushProb(playerCards, shownCards);
		
		int royalFlushValue = 10;
		double royalFlushProb = royalFlushProb(playerCards, shownCards);
	}
	
	//probability that hand will have a pair
	private double pairProb(Card[] playerCards, Card[] shownCards) {
		
		if (playerCards[0].getNumber() == playerCards[1].getNumber()) return 1;
		
		//if pair already exists, probability is one
			for (int i = 0; i < HAND; i++) {
				int rank = playerCards[i].getNumber();

				for (Card d : shownCards) {
					if (d.getNumber() == rank) {
						return 1;
					}
				}
			}
			
			//if pair doesn't yet exist, all 3 other cards of rank still in deck
			return (SUITS - 1) / (double) numUnknownCards;
	}
	
	//probability that hand will have two pairs
	private double twoPairProb(Card[] playerCards, Card[] shownCards) {

		int numDistinctPairs = 0;
		
		//if player cards are the same, cannot have two distinct pairs
		if (playerCards[0].getNumber() == playerCards[1].getNumber()) {
			return 0;
		}
		
			for (int i = 0; i < HAND; i++) {
				int rank = playerCards[i].getNumber();
				
				for (Card d : shownCards) {
					if (d.getNumber() == rank) {
						numDistinctPairs++;
						break;
					}
				}
			}
			
			if (numDistinctPairs == 0) return ((SUITS - 1) / 
					(double) numUnknownCards) * ((SUITS - 1) / 
							(double) numUnknownCards);
			if (numDistinctPairs == 1) return (SUITS - 1) / (double) numUnknownCards;
			return 1;
	}
	
	//approx probability that hand will have three of a kind
	private double threeKindProb(Card[] playerCards, Card[] shownCards) {
		int numPairs = 0;
		if (playerCards[0].getNumber() == playerCards[1].getNumber()) {
			int rank = playerCards[0].getNumber();
			for (Card d : shownCards) {
				if (d.getNumber() == rank) return 1;
			}
			numPairs++;
		}
		
		for (int i = 0; i < HAND; i++) {
			int rank = playerCards[i].getNumber();
			int numMatched = 0;
			
			for (Card d : shownCards) {
				if (d.getNumber() == rank) numMatched++;
			}
			if (numMatched == 2) return 1;
			if (numMatched == 1) numPairs++;
		}
		
		if (numPairs == 0) return 2 * ((SUITS - 1) / (double) numUnknownCards) * ((SUITS - 2 / (double) (numUnknownCards - 1)));
		if (numPairs == 1) return ((SUITS - 1) / (double) numUnknownCards) * ((SUITS - 2 / (double) (numUnknownCards - 1)))
				+ (SUITS - 2) / (double) numUnknownCards;
		return 2 * ((SUITS - 2) / (double) numUnknownCards);
	}
	
	private double straightProb(Card[] playerCards, Card[] shownCards) {
		int length = 2 + showncards.length;
		
		int [] straightCheck = new int [length];
		straightCheck[0] = playercards[0];
		straightCheck[1] = playercards[1];
		for (int i = 0; i < shownCards.length; i++){
			straightCheck[i + 2] = shownCards[i];
		}
		
		//Selection sorting
		for (int i = 1; i < length; i++) {
            		int x = straightCheck[i];
      			int j = i;
        		while (j > 0 && straightCheck[j - 1] > x) {
                	straightCheck[j] = straightCheck[j - 1]; 
                	j--;
                	straightCheck[j] = x;
            		}
        	}
        	
        	int tracker = 0;
        	for (int i = 0; i < length; i++) {
        		if ((straighCheck[i] + 1) == straightCheck[i+1]) {
        			tracker++;
        		}
        		else {
        			tracker = 0;
        		}
        	}
		if (tracker > 4) {
			return 1;
		}
		if (tracker == 4) {
			//return
		}
		if (tracker == 3) {
			//return	
		}
		return 0;
	}
	
	private double flushProb(Card[] playerCards, Card[] shownCards) {
		
	}
	
	private double fullProb(Card[] playerCards, Card[] shownCards) {
		
	}
	
	private double fourKindProb(Card[] playerCards, Card[] shownCards) {
		
	}
	
	private double straightFlushProb(Card[] playerCards, Card[] shownCards) {
		
	}
	
	private double royalFlushProb(Card[] playerCards, Card[] shownCards) {
		
	}
	
}
