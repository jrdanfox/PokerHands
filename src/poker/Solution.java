package poker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
	
	// Deal a hand of 5 cards
	public static Card[] dealHand(String[] cardStrings) {
		Card[] hand = new Card[5];
		for (int i = 0; i < cardStrings.length; i++) {
			hand[i] = new Card(cardStrings[i].charAt(0), cardStrings[i].charAt(1));
			System.out.print(Character.toString(hand[i].getValue()) + Character.toString(hand[i].getSuit()) + " ");
		}
		System.out.print("\n");
		return hand;
	}

	public static void main(String[] args) throws FileNotFoundException {
		// file io stuff
		File file = new File("src/input");
		Scanner scanner = new Scanner(file);
		
		String[] handStrings = new String[5];
		int cardCount = 0;
		int handCount = 0;
		
		Card[] blackHand, whiteHand;
		
		while (scanner.hasNext()) {
			String next = scanner.next();
			if (!next.equals("Black:") && !next.equals("White:")) { // filter out hand labels
				handStrings[cardCount] = next;
				cardCount++;
				if (cardCount == 5) {
					if (handCount == 0) { // deal black a hand
						blackHand = dealHand(handStrings);
						handCount++;
					} else { // deal white a hand, reset handCount
						whiteHand = dealHand(handStrings);
						handCount = 0;
					}
					cardCount = 0;
				}
			}
		}
		scanner.close();
	}
}
