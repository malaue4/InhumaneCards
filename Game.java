package inhumane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by martin on 4/9/16.
 *
 */

public class Game {
	Dealer dealer;
	ObservableList<Player> players;
	Player tzar;
	int cardsPerPlayer = 10;
	BlackCardData blackCard;
	ArrayList<Answer> answers;

	public Game() {
		dealer = new Dealer();
		players = FXCollections.observableArrayList();
	}

	void newGame(ArrayList<Player> players){
		this.players.clear();
		this.players.addAll(players);
		System.out.printf("%d players entered the game!%n", players.size());
		tzar = players.get(new Random().nextInt(players.size()));
		System.out.printf("%s is the Tzar!%n", tzar.name);
	}

	void newGame(){
		System.out.printf("%d players entered the game!%n", players.size());
		tzar = players.get(new Random().nextInt(players.size()));
		System.out.printf("%s is the Tzar!%n", tzar.name);
	}

	void startRound() {
		System.out.println("Round begins!");
		blackCard = dealer.drawBlackCard();
		System.out.printf("%s: %s%n", tzar.name, blackCard);
		System.out.println("-------------");
		players.forEach(player -> player.fillHand(dealer, cardsPerPlayer+blackCard.draw));
		tzar = players.get((players.indexOf(tzar)+1)%players.size());
	}

	public void addPlayer(Player player) {
		players.add(player);
	}
}
