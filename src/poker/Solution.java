package poker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) throws FileNotFoundException {
		// file io stuff
		File file = new File("src/input");
		Scanner scanner = new Scanner(file);
		
		String[] handStrings = new String[5];
		int cardCount = 0;
		int handCount = 0;
		
		Hand blackHand, whiteHand;
		
		while (scanner.hasNext()) {
			String next = scanner.next();
			if (!next.equals("Black:") && !next.equals("White:")) { // filter out hand labels
				handStrings[cardCount] = next;
				cardCount++;
				if (cardCount == 5) {
					if (handCount == 0) { // deal black a hand
						blackHand = new Hand(handStrings);
						handCount++;
					} else { // deal white a hand, reset handCount
						whiteHand = new Hand(handStrings);
						handCount = 0;
					}
					cardCount = 0;
				}
			}
		}
		scanner.close();
		
		Score.test(new String[] {"2D", "2C", "7H", "KD", "AD"});
	}
}
