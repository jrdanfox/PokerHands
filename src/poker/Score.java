package poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Score {
	
	private static List<Character> orderOfValues = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'); 
	
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
		for (int i = 0; i < hand.getSize(); i++) {
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
		List<Integer> intvals = new ArrayList<Integer>();
		for (int i = 0; i < values.size(); i++) {
			intvals.add(orderOfValues.indexOf(values.get(i)));
		}
		Collections.sort(intvals); // switch to ints so i can sort it
		for (int i = 0; i < intvals.size() - 1; i++) {
			if (intvals.get(i + 1) != intvals.get(i) + 1) isStraight = false;
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
					hand.setPrimaryWinValue(hand.getCard(i).getValue());
				} else {
					hand.setSecondaryWinValue(hand.getCard(i).getValue());
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
			char highpair = '2', currentpair;
			for (int i = 0; i < hand.getSize(); i++) {
				if (cardCount(hand, hand.getCard(i).getValue()) == 2) {
					currentpair = hand.getCard(i).getValue();
					if (orderOfValues.indexOf(currentpair) >= orderOfValues.indexOf(highpair) && currentpair != '2') {
						highpair = currentpair;
						hand.setPrimaryWinValue(highpair);
					} else {
						hand.setSecondaryWinValue(currentpair);
					}
				}
				hand.setTieBreaker(highpair);
			}
		} else if (checkPair(hand)) {
			hand.setRank(1);
			for (int i = 0; i < hand.getSize(); i++) {
				if (cardCount(hand, hand.getCard(i).getValue()) == 2) {
					hand.setTieBreaker(hand.getCard(i).getValue());
					break;
				}
			}
		} else {
			hand.setRank(0);
			hand.setTieBreaker(maxValue(hand));
		}
	}

	// return 0 if black wins, 1 if white wins, -1 if tie
	private static int tieBreakHighCard(Hand black, Hand white) {
		int winner = -1;
		if (orderOfValues.indexOf(black.getTieBreaker()) > orderOfValues.indexOf(white.getTieBreaker())) {
			winner = 0;
			black.setPrimaryWinValue(black.getTieBreaker());
		} else if (orderOfValues.indexOf(black.getTieBreaker()) < orderOfValues.indexOf(white.getTieBreaker())) {
			winner = 1;
			white.setPrimaryWinValue(white.getTieBreaker());
		} else { // hands have the same max value
			List<Character> blackvalues = black.getAllValues();
			List<Character> whitevalues = white.getAllValues();
			List<Integer> blackintvals = new ArrayList<Integer>();
			List<Integer> whiteintvals = new ArrayList<Integer>();
			for (int i = 0; i < blackvalues.size(); i++) {
				blackintvals.add(orderOfValues.indexOf(blackvalues.get(i)));
				whiteintvals.add(orderOfValues.indexOf(whitevalues.get(i)));
			}
			Collections.sort(blackintvals);
			Collections.sort(whiteintvals);
			int cardcount = 4; // start at 1 since we already checked tiebreaker
			while (cardcount > 0 && winner == -1) {
				if (blackintvals.get(cardcount) > whiteintvals.get(cardcount)) {
					winner = 0;
					black.setPrimaryWinValue(orderOfValues.get(blackintvals.get(cardcount)));
				} else if (blackintvals.get(cardcount) < whiteintvals.get(cardcount)) {
					winner = 1;
					white.setPrimaryWinValue(orderOfValues.get(whiteintvals.get(cardcount)));
				}
				cardcount--;
			}
		}
		
		return winner;
	}
	
	private static int tieBreakSpecial(Hand black, Hand white) {
		int winner = -1;
		if (black.getRank() == 2) { // handle 2 pair case
			if (orderOfValues.indexOf(black.getPrimaryWinValue()) > orderOfValues.indexOf(white.getPrimaryWinValue())) {
				winner = 0;
			} else if (orderOfValues.indexOf(black.getPrimaryWinValue()) < orderOfValues.indexOf(white.getPrimaryWinValue()) ) {
				winner = 1;
			} else {
				if (orderOfValues.indexOf(black.getSecondaryWinValue()) > orderOfValues.indexOf(white.getSecondaryWinValue())) {
					winner = 0;
				} else if (orderOfValues.indexOf(black.getSecondaryWinValue()) < orderOfValues.indexOf(white.getSecondaryWinValue()) ) {
					winner = 1;
				} else {
					char blackLoneCard = 'a', whiteLoneCard = 'a'; // initialize to random chars to avoid warnings
					for (int i = 0; i < black.getSize(); i++) {
						if (cardCount(black, black.getCard(i).getValue()) == 1) {
							blackLoneCard = black.getCard(i).getValue();
						}
					}
					for (int i = 0; i < white.getSize(); i++) {
						if (cardCount(white, white.getCard(i).getValue()) == 1) {
							whiteLoneCard = white.getCard(i).getValue();
						}
					}
					if (orderOfValues.indexOf(blackLoneCard) > orderOfValues.indexOf(whiteLoneCard)) {
						winner = 0;
					} else if (orderOfValues.indexOf(blackLoneCard) < orderOfValues.indexOf(whiteLoneCard)) {
						winner = 1;
					}
				}
			}
		}
		
		return winner;
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
		} else { // tie
			black.setTied(true);
			white.setTied(true);
			if (black.getRank() == 2) {
				winner = tieBreakSpecial(black, white);
			} else {
				winner = tieBreakHighCard(black, white);
			}
		}
		return winner;
	}
}
