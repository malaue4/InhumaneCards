package inhumane;

import java.util.ArrayList;

/**
 * Created by martin on 4/9/16.
 *
 */

public class Player {
	String name;
	// Address
	int awesomePoints;
	ArrayList<WhiteCardData> cards;

	@Override
	public String toString() {
		return name;
	}

	public Player(String name) {
		this.name = name;
		awesomePoints = 0;
		cards = new ArrayList<>();
	}

	void fillHand(Dealer dealer, int cardsMax){
		for (int i = 0; i < cardsMax; i++) {
			cards.add(dealer.drawWhiteCard());
		}
	}

	WhiteCardData playCard(WhiteCardData card){
		return card;
	}
}
