package poker;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ScoreTest {

	@Test
	void testCompareHandsStraightFlush() {
		Hand black = new Hand(new String[] {"2C", "5D", "5C", "4S", "7C"});
		Hand white = new Hand(new String[] {"2H", "3H", "4H", "5H", "6H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins, straight flush
	}
	
	@Test
	void testCompareHandsStraightFlushTie() {
		Hand black = new Hand(new String[] {"3C", "4C", "5C", "6C", "7C"});
		Hand white = new Hand(new String[] {"2H", "3H", "4H", "5H", "6H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, straight flush high card
	}
	
	@Test
	void testCompareHandsFourOfAKind() {
		Hand black = new Hand(new String[] {"2C", "2D", "2H", "2S", "7C"});
		Hand white = new Hand(new String[] {"9D", "3H", "TC", "5H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, four of a kind
	}
	
	@Test
	void testCompareHandsFourOfAKindTie() {
		Hand black = new Hand(new String[] {"2C", "2D", "2H", "2S", "7C"});
		Hand white = new Hand(new String[] {"9D", "9S", "9C", "9H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins, four of a kind high
	}
	
	@Test
	void testCompareHandsFullHouse() {
		Hand black = new Hand(new String[] {"2C", "2D", "3H", "3S", "3C"});
		Hand white = new Hand(new String[] {"9D", "3H", "TC", "5H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, full house
	}
	
	@Test
	void testCompareHandsFullHouseHigh3() {
		Hand black = new Hand(new String[] {"2C", "2D", "3H", "3S", "3C"});
		Hand white = new Hand(new String[] {"9D", "9H", "9C", "5D", "5H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins, full house high 3 of a kind
	}
	
	@Test
	void testCompareHandsFlush() {
		Hand black = new Hand(new String[] {"2C", "4C", "5C", "9C", "QC"});
		Hand white = new Hand(new String[] {"9D", "3H", "TC", "5H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, flush
	}
	
	@Test
	void testCompareHandsFlushHigh() {
		Hand black = new Hand(new String[] {"2C", "4C", "5C", "9C", "QC"});
		Hand white = new Hand(new String[] {"AH", "3H", "TH", "5H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins, flush high
	}
	
	@Test
	void testCompareHandsStraight() {
		Hand black = new Hand(new String[] {"2C", "3S", "4C", "5H", "6D"});
		Hand white = new Hand(new String[] {"9D", "3H", "TC", "5D", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, straight
	}
	
	@Test
	void testCompareHandsStraightHigh() {
		Hand black = new Hand(new String[] {"2C", "3S", "4C", "5H", "6D"});
		Hand white = new Hand(new String[] {"3D", "4H", "5C", "6S", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins, straight high
	}
	
	@Test
	void testCompareHandsThreeOfAKind() {
		Hand black = new Hand(new String[] {"9D", "3H", "TC", "5H", "7H"});
		Hand white = new Hand(new String[] {"2C", "2S", "2D", "9C", "QC"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins, three of a kind
	}
	
	@Test
	void testCompareHandsThreeOfAKindHigh() {
		Hand black = new Hand(new String[] {"9D", "9H", "9C", "5H", "7H"});
		Hand white = new Hand(new String[] {"2C", "2S", "2D", "9S", "QC"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, three of a kind high
	}
	
	@Test
	void testCompareHandsTwoPair() {
		Hand black = new Hand(new String[] {"9S", "3H", "TC", "5H", "7H"});
		Hand white = new Hand(new String[] {"2C", "2S", "9D", "9C", "QC"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins, two pair
	}
	
	@Test
	void testCompareHandsTwoPairHighPair() {
		Hand black = new Hand(new String[] {"TS", "3H", "TC", "7D", "7H"});
		Hand white = new Hand(new String[] {"2C", "2S", "9D", "9C", "QC"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, two pair high pair
	}
	
	@Test
	void testCompareHandsTwoPairLowPair() {
		Hand black = new Hand(new String[] {"TS", "3H", "TC", "7D", "7H"});
		Hand white = new Hand(new String[] {"2C", "2S", "TD", "TH", "QC"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, two pair low pair higher
	}
	
	@Test
	void testCompareHandsTwoPairHighCard() {
		Hand black = new Hand(new String[] {"TS", "QH", "TC", "2D", "2H"});
		Hand white = new Hand(new String[] {"2C", "2S", "TD", "TH", "3C"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, two pair high card
	}
	
	@Test
	void testCompareHandsPair() {
		Hand black = new Hand(new String[] {"2C", "2H", "5D", "9S", "QC"});
		Hand white = new Hand(new String[] {"9D", "3H", "TC", "5H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, pair
	}
	
	@Test
	void testCompareHandsHighPair() {
		Hand black = new Hand(new String[] {"2C", "2H", "5D", "9S", "QC"});
		Hand white = new Hand(new String[] {"3D", "3H", "TC", "5H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins,  high pair
	}
	
	@Test
	void testCompareHandsPairHighCard() {
		Hand black = new Hand(new String[] {"2C", "2H", "5D", "9S", "QC"});
		Hand white = new Hand(new String[] {"2D", "2S", "AC", "5H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 1); // white wins,  high card
	}
	
	@Test
	void testCompareHandsHighCard() {
		Hand black = new Hand(new String[] {"2C", "3H", "5D", "9S", "AC"});
		Hand white = new Hand(new String[] {"9D", "3H", "TC", "5H", "7H"});
		int winner = Score.compareHands(black, white);
		assertEquals(winner, 0); // black wins, ace high
	}

}
