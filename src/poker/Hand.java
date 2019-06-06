package poker;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> cards;
	
	// return a list of all of the values in this hand
	public List<Character> getAllValues() {
		List<Character> values = new ArrayList<Character>();
		for (int i = 0; i < this.cards.size(); i++) {
			values.add(this.cards.get(i).getValue());
		}
		return values;
	}
	
	public List<Character> getAllSuits() {
		List<Character> suits = new ArrayList<Character>();
		for (int i = 0; i < this.cards.size(); i++) {
			suits.add(this.cards.get(i).getSuit());
		}
		return suits;
	}
	
	// print hand (for testing purposes)
	public void printHand() {
		for (int i = 0; i < this.cards.size(); i++) {
			System.out.print(Character.toString(this.cards.get(i).getValue()) + this.cards.get(i).getSuit() + " ");
		}
		System.out.print("\n");
	}
	
	// return the card at position i in this.cards
	public Card getCard(int i) {
		return this.cards.get(i);
	}
	
	// return the size of this.cards
	public int getSize() {
		return this.cards.size();
	}
	
	// contstruct a hand from a string array of cards
	public Hand(String[] cardStrings) {
		this.cards = new ArrayList<Card>();
		for (int i = 0; i < cardStrings.length; i++) {
			this.cards.add(new Card(cardStrings[i].charAt(0), cardStrings[i].charAt(1)));
		}
	}
}
