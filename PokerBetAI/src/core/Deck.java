package sourcepkg;

import java.util.Stack;

public class Deck {
	private Stack<Card> deck;
	
	public Deck() {
		deck = new Stack<Card>();
		Card[] unshuffled = new Card[52];
		int i = 0;
		for (int j = 0; j < 13; j++) {
			for (int k = 0; k < 4; k++) {
				unshuffled[i] = new Card(j,k);
			}
		}
		// shuffle
		for (i = 0; i < 100; i++) {
			int j = (int) (52*Math.random());
			int k = (int) (52*Math.random());
			
			Card temp = unshuffled[j];
			unshuffled[j] = unshuffled[i];
			unshuffled[i] = temp;
		}
		
		for (i = 0; i < 52; i++) {
			deck.push(unshuffled[i]);
		}
	}
	
	public Card deal() {
		return deck.pop();
	}
	
	}
