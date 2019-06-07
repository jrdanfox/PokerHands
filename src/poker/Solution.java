package poker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {
	
	private static List<Character> cardValues = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
	private static Map<Character, String> cardMap;
	
	private static void buildCardMap() {
		cardMap = new HashMap<Character, String>();
		for (int i = 0; i < cardValues.size(); i++) {
			char val = cardValues.get(i);
			switch (val) {
				case 'T':
					cardMap.put(val, "10");
					break;
				case 'J':
					cardMap.put(val, "Jack");
					break;
				case 'Q':
					cardMap.put(val, "Queen");
					break;
				case 'K':
					cardMap.put(val, "King");
					break;
				case 'A':
					cardMap.put(val, "Ace");
					break;
				default:
					cardMap.put(val, Character.toString(val));
					break;
			}
		}
	}
	
	private static void printWinReason(Hand hand) {
		if (!hand.isTied()) {
			switch (hand.getRank()) {
				case 8:
					System.out.print("straight flush: " + cardMap.get(hand.getTieBreaker()) + " high\n");
					break;
				case 7:
					System.out.print("four of a kind: " + cardMap.get(hand.getTieBreaker()) + "\n");
					break;
				case 6:
					System.out.print("full house: " + cardMap.get(hand.getPrimaryWinValue()) + " over " + cardMap.get(hand.getSecondaryWinValue()) + "\n");
					break;
				case 5:
					System.out.print("flush: " + cardMap.get(hand.getTieBreaker()) + " high\n");
					break;
				case 4:
					System.out.print("straight: " + cardMap.get(hand.getTieBreaker()) + " high\n");
					break;
				case 3:
					System.out.print("three of a kind: " + cardMap.get(hand.getTieBreaker()) + "high\n");
					break;
				case 2:
					System.out.print("two pair: " + cardMap.get(hand.getPrimaryWinValue()) + " high pair, " + cardMap.get(hand.getSecondaryWinValue()) + " low pair\n");
					break;
				case 1:
					System.out.print("pair: " + cardMap.get(hand.getTieBreaker()) + "\n");
					break;
				case 0:
					System.out.print("high card: " + cardMap.get(hand.getTieBreaker()) + "\n");
					break;
			}
		} else {
			System.out.print("high card: " + cardMap.get(hand.getPrimaryWinValue()) + "\n");
		}
	}
	
	private static void displayWinner(Hand blackHand, Hand whiteHand) {
		int winner = Score.compareHands(blackHand, whiteHand);
		if (winner == 0) {
			System.out.print("Black wins. - with ");
			printWinReason(blackHand);
		} else if (winner == 1) {
			System.out.print("White wins. - with ");
			printWinReason(whiteHand);
		} else {
			System.out.println("Tie.");
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("src/input");
		Scanner scanner = new Scanner(file);
		buildCardMap();
		String[] handStrings = new String[5];
		int cardCount = 0;
		int handCount = 0;
		Hand blackHand = null, whiteHand = null;
		
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
						displayWinner(blackHand, whiteHand);
					}
					cardCount = 0;
				}
			}
		}
		scanner.close();
	}
}
