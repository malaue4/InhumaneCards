package inhumane;

import java.util.Arrays;

/**
 * Created by martin on 4/9/16.
 */
public class Answer {
	Player playedBy;
	WhiteCardData[] cards;

	public Answer(Player playedBy, WhiteCardData[] cards) {
		this.playedBy = playedBy;
		this.cards = cards;
	}

	@Override
	public String toString() {
		return Arrays.toString(cards);
	}
}
