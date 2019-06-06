package poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Score {
	
	private static List<Character> nextVals = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'); 
	
	/***********	USEFUL PRIVATE CLASS METHODS	***************************************/
	
	// return whether or not hand contains a pair
	private static boolean containsPair(Hand hand) {
		boolean containsPair = false;
		List<Character> values = hand.getAllValues();
		for (int i = 0; i < hand.getSize(); i++) {
			if (Collections.frequency(values, values.get(i)) == 2) containsPair = true;
		}
		return containsPair;
	}
	
	// return twice the number of pairs in the hand (so I dont have to remove dupe pairs)
	private static int doubleNoPairs(Hand hand) {
		int numberOfPairsTimes2 = 0;
		List<Character> values = hand.getAllValues();
		for (int i = 0; i < hand.getSize(); i++) {
			if (Collections.frequency(values, values.get(i)) == 2) numberOfPairsTimes2++;
		}
		return numberOfPairsTimes2;
	}
	
	// return the maximum count of duplicate values in hand
	private static int maxDuplicateValues(Hand hand) {
		int max = 0;
		List<Character> values = hand.getAllValues();
		for (int i = 0; i < hand.getSize();i++) {
			int numberOfDupes = Collections.frequency(values, values.get(i));
			if (numberOfDupes > max) max = numberOfDupes;
		}
		return max;
	}
	
	// return the maximum count of duplicate suits in hand
	private static int maxDuplicateSuits(Hand hand) {
		int max = 0;
		List<Character> suits = hand.getAllSuits();
		for (int i = 0; i < hand.getSize();i++) {
			int numberOfDupes = Collections.frequency(suits, suits.get(i));
			if (numberOfDupes > max) max = numberOfDupes;
		}
		return max;
	}
	
	/***********	CHECK RANK OF HAND	*****************************************/
	
	private static boolean checkStraightFlush(Hand hand) {
		boolean isStraightFlush = true;
		char firstSuit = hand.getCard(0).getSuit();
		char currentValue = hand.getCard(0).getValue();
		List<Character> values = hand.getAllValues();
		
		for (int i = 1; i < hand.getSize(); i++) {
			char nextValue = nextVals.get(nextVals.indexOf(currentValue) + 1);
			if (hand.getCard(i).getSuit() != firstSuit || !values.contains(nextValue)) {
				isStraightFlush = false;
			}
			currentValue = nextValue;
		}
		
		return isStraightFlush;
	}
	
	private static boolean checkFourOfAKind(Hand hand) {
		return maxDuplicateValues(hand) == 4;
	}
	
	private static boolean checkFullHouse(Hand hand) {
		return maxDuplicateValues(hand) == 3 && containsPair(hand);
	}
	
	private static boolean checkStraight(Hand hand) {
		return maxDuplicateSuits(hand) == 5;
	}
	
	private static boolean checkThreeOfAKind(Hand hand) {
		return maxDuplicateValues(hand) == 3;
	}
	
	private static boolean checkTwoPairs(Hand hand) {
		return doubleNoPairs(hand) == 4;
	}
	
	private static boolean checkPair(Hand hand) {
		return doubleNoPairs(hand) == 2;
	}
	
	public static void test(String[] cards) {
		Hand hand = new Hand(cards);
		System.out.println("Stratight flush: " + checkStraightFlush(hand));
		System.out.println("Four of a kind: " + checkFourOfAKind(hand));
		System.out.println("Full house: " + checkFullHouse(hand));
		System.out.println("Straight: " + checkStraight(hand));
		System.out.println("Three of a kind: " + checkThreeOfAKind(hand));
		System.out.println("Two pairs: " + checkTwoPairs(hand));
		System.out.println("One pair: " + checkPair(hand));
	}
}
