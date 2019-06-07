package poker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Score {
	
	private static List<Character> orderOfValues = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'); 
	
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
	
	// return the minimum value of hand
	private static char minValue(Hand hand) {
		List<Character> values = hand.getAllValues();
		char min = values.get(0);
		for (int i = 1; i < hand.getSize(); i++) {
			if (orderOfValues.indexOf(values.get(i)) < orderOfValues.indexOf(min)) min = values.get(i);
		}
		return min;
	}
	
	/***********	CHECK RANK OF HAND	*****************************************/
	
	private static boolean checkStraightFlush(Hand hand) {
		return checkFlush(hand) && checkStraight(hand);
	}
	
	private static boolean checkFourOfAKind(Hand hand) {
		return maxDuplicateValues(hand) == 4;
	}
	
	private static boolean checkFullHouse(Hand hand) {
		return maxDuplicateValues(hand) == 3 && containsPair(hand);
	}
	
	private static boolean checkFlush(Hand hand) {
		return maxDuplicateSuits(hand) == 5;
	}
	
	private static boolean checkStraight(Hand hand) {
		boolean isStraight = true;
		List<Character> values = hand.getAllValues();
		Collections.sort(values);
		
		char currentValue = values.get(0);
		for (int i = 1; i < values.size();i++) {
			char nextvalue = orderOfValues.get(orderOfValues.indexOf(currentValue) + 1);
			if (!values.contains(nextvalue)) isStraight = false;
			currentValue = nextvalue;
		}
		
		return isStraight;
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
	
	private static void rankHand(Hand hand) {
		if (checkStraightFlush(hand)) {
			hand.setRank(8);
		} else if (checkFourOfAKind(hand)) {
			hand.setRank(7);
		} else if (checkFullHouse(hand)) {
			hand.setRank(6);
		} else if (checkFlush(hand)) {
			hand.setRank(5);
		} else if (checkStraight(hand)) {
			hand.setRank(4);
		} else if (checkThreeOfAKind(hand)) {
			hand.setRank(3);
		} else if (checkTwoPairs(hand)) {
			hand.setRank(2);
		} else if (checkPair(hand)) {
			hand.setRank(1);
		} else {
			hand.setRank(0);
		}
	}
	
	public static void test(String[] cards) {
		Hand hand = new Hand(cards);
		rankHand(hand);
		System.out.println("Rank: " + hand.getRank());
		System.out.println("Straight flush: " + checkStraightFlush(hand));
		System.out.println("Four of a kind: " + checkFourOfAKind(hand));
		System.out.println("Full house: " + checkFullHouse(hand));
		System.out.println("Flush: " + checkFlush(hand));
		System.out.println("Straight: " + checkStraight(hand));
		System.out.println("Three of a kind: " + checkThreeOfAKind(hand));
		System.out.println("Two pairs: " + checkTwoPairs(hand));
		System.out.println("One pair: " + checkPair(hand));
	}
}
