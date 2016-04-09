package inhumane;

import java.util.ArrayList;

/**
 * Created by martin on 4/9/16.
 *
 */

public class Game {
	Dealer dealer;
	ArrayList<Player> players;
	Player tzar;
	int cardsPerPlayer = 10;
	BlackCardData blackCard;

	public Game() {
		players = new ArrayList<>();
		dealer = new Dealer();
	}

	void newGame(ArrayList<Player> players){
		this.players.clear();
		this.players.addAll(players);
		tzar = players.get(0);
	}

	void newRound() {
		blackCard = dealer.drawBlackCard();
		players.forEach(player -> player.fillHand(dealer, cardsPerPlayer+blackCard.draw));
		tzar = players.get((players.indexOf(tzar)+1)%players.size());
	}
}
