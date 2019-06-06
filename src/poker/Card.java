package poker;

public class Card {
	private char suit;
	private char value;
	
	// Getters (probably no need for setters)
	public char getSuit() {
		return suit;
	}
	public char getValue() {
		return value;
	}
	
	// Constructor
	public Card( char myValue, char mySuit) {
		this.suit = mySuit;
		this.value = myValue;
	}
}
