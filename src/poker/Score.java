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
	
	// return maximum value of hand
	private static char maxValue(Hand hand) {
		List<Character> values = hand.getAllValues();
		char max = values.get(0);
		for (int i = 1; i < hand.getSize(); i++) {
			if (orderOfValues.indexOf(values.get(i)) > orderOfValues.indexOf(max)) max = values.get(i);
		}
		return max;
	}
	
	// return the number of occurences of the value in hand
	private static int cardCount(Hand hand, char value) {
		int count = 0;
		List<Character> values = hand.getAllValues();
		for (int i = 1; i < hand.getSize(); i++) {
			if (values.get(i) == value) count++;
		}
		return count;
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
			hand.setTieBreaker(maxValue(hand));
		} else if (checkFourOfAKind(hand)) {
			hand.setRank(7);
			if (cardCount(hand, hand.getCard(0).getValue()) > 1) {
				hand.setTieBreaker(hand.getCard(0).getValue());
			} else {
				hand.setTieBreaker(hand.getCard(1).getValue());
			}
		} else if (checkFullHouse(hand)) {
			hand.setRank(6);
			for (int i = 0; i < hand.getSize(); i++) {
				if (cardCount(hand, hand.getCard(i).getValue()) == 3) {
					hand.setTieBreaker(hand.getCard(i).getValue());
					break;
				}
			}
		} else if (checkFlush(hand)) { // tiebreaker causes issue
			hand.setRank(5);
			hand.setTieBreaker(maxValue(hand));
		} else if (checkStraight(hand)) {
			hand.setRank(4);
			hand.setTieBreaker(maxValue(hand));
		} else if (checkThreeOfAKind(hand)) {
			hand.setRank(3);
			for (int i = 0; i < hand.getSize(); i++) {
				if (cardCount(hand, hand.getCard(i).getValue()) == 3) {
					hand.setTieBreaker(hand.getCard(i).getValue());
					break;
				}
			}
		} else if (checkTwoPairs(hand)) {
			hand.setRank(2);
		} else if (checkPair(hand)) {
			hand.setRank(1);
		} else { // tiebreaker issue
			hand.setRank(0);
		}
	}
	
	// return 0 if black wins, 1 if white wins, -1 if tie
	public static int compareHands(Hand black, Hand white) {
		rankHand(black);
		rankHand(white);
		int winner;
		if (black.getRank() > white.getRank()) {
			winner = 0;
		} else if (black.getRank() < white.getRank()) {
			winner = 1;
		} else {
			winner = -1;
		}
		
		return winner;
	}
	
	public static void test(String[] cards) {
		Hand hand = new Hand(cards);
		rankHand(hand);
		
//		System.out.println("Rank: " + hand.getRank());
//		System.out.println("Straight flush: " + checkStraightFlush(hand));
//		System.out.println("Four of a kind: " + checkFourOfAKind(hand));
//		System.out.println("Full house: " + checkFullHouse(hand));
//		System.out.println("Flush: " + checkFlush(hand));
//		System.out.println("Straight: " + checkStraight(hand));
//		System.out.println("Three of a kind: " + checkThreeOfAKind(hand));
//		System.out.println("Two pairs: " + checkTwoPairs(hand));
//		System.out.println("One pair: " + checkPair(hand));
	}
}
