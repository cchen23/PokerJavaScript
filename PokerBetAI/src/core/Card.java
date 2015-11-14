package core;

public class Card {
	private int number;
	private int suit;
	
	public Card (int n, int s) {
		number = n;
		suit = s;
	}
	
	public int getNumber() { return number; }
	
	public int getSuit() { return suit; }
	
	public String toString() {
		String s;
		String n;
		switch (suit) {
		case 0:
			s = "Diamonds";
			break;
		case 1:
			s = "Hearts";
			break;
		case 2:
			s = "Clubs";
			break;
		case 3:
			s = "Spades";
			break;
		default:
			s = "Error";
			break;
		}
		switch (number) {
		case 0:
			n = "Two";
			break;
		case 1:
			n = "Three";
			break;
		case 2:
			n = "Four";
			break;
		case 3:
			n = "Five";
			break;
		case 4:
			n = "Six";
			break;
		case 5:
			n = "Seven";
			break;
		case 6:
			n = "Eight";
			break;
		case 7:
			n = "Nine";
			break;
		case 8:
			n = "Ten";
			break;
		case 9:
			n = "Jack";
			break;
		case 10:
			n = "Queen";
			break;
		case 11:
			n = "King";
			break;
		case 12:
			n = "Ace";
			break;
		default:
			n = "Error";
			break;
		}
		
		return n + " of " + s;
			
	}
}
