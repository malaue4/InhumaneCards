package inhumane;

import java.util.ArrayList;

/**
 * Created by martin on 4/5/16.
 *
 */

public class Deck {
	String name;
	String author;
	ArrayList<BlackCardData> blackCards;
	ArrayList<WhiteCardData> whiteCards;

	public Deck(String name, String author) {
		this.name = name;
		this.author = author;
		blackCards = new ArrayList<>();
		whiteCards = new ArrayList<>();
	}

	public void add(BlackCardData blackCardData) {
		blackCards.add(blackCardData);
	}

	public void add(WhiteCardData whiteCardData) {
		whiteCards.add(whiteCardData);
	}
}
