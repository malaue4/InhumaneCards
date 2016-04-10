package inhumane;

import inhumane.net.Server;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;

public class Controller {
    public ListView playerListView;
	public BorderPane serverStuff;
	public TextFlow textField;
	public ToggleButton discoverToggle;
	public HBox cardHolder;

	Dealer dealer;
	Game game;

	public void initialize(){
		playerListView.getItems().add(new Text("WHY"));
		discoverToggle.disableProperty().bind(Server.getInterruptedProperty());

		Deck deck = Main.loadDeck(getClass().getResourceAsStream("resources/cards/main.xml"));

		dealer = new Dealer();
		dealer.addDeck(deck);
		dealer.shuffleCards();

		game = new Game();

		ArrayList<Player> players = new ArrayList<>();
		players.add(new Player("player1"));
		players.add(new Player("player2"));
		players.add(new Player("player3"));
		players.add(new Player("player4"));
		players.add(new Player("player5"));
		game.newGame(players);

		BlackCardData card;
		card = dealer.drawBlackCard();
		cardHolder.getChildren().add(new Card(card));
		for (int i = 0; i < card.pick; i++) {
			cardHolder.getChildren().add(new Card(dealer.drawWhiteCard()));
		}
	}

    public void toggleDiscover(ActionEvent actionEvent) {
		if(Server.isDiscovering()){
			Server.stopDiscovery();
			discoverToggle.setText("Start Discovery");
		} else {
			Server.startDiscovery(8888);
			discoverToggle.setText("Stop Discovery");
		}
    }
}
