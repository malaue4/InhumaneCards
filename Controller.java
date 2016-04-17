package inhumane;

import inhumane.net.Client;
import inhumane.net.Server;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Controller {
    public ListView<Player> playerListView;
	public ToggleButton discoverToggle;
	public HBox cardHolder;

	ImageView connectIcon, disconnectIcon;

	Dealer dealer;
	Game game;
	Client client;
	Server server;

	public void initialize(){
		server = new Server();
		connectIcon = new ImageView(getClass().getResource("resources/images/discover.png").toExternalForm());
		connectIcon.setFitWidth(16); connectIcon.setFitHeight(16);
		disconnectIcon = new ImageView(getClass().getResource("resources/images/no_discover.png").toExternalForm());
		disconnectIcon.setFitWidth(16); disconnectIcon.setFitHeight(16);
		discoverToggle.setGraphic(disconnectIcon);
		discoverToggle.disableProperty().bind(server.getInterruptedProperty());

		Deck deck = Main.loadDeck(getClass().getResourceAsStream("resources/cards/main.xml"));

		dealer = new Dealer();
		dealer.addDeck(deck);
		dealer.shuffleCards();

		client = new Client();

		game = new Game();
		playerListView.setItems(game.players);
		game.addPlayer(new Player("player1"));
		game.addPlayer(new Player("player2"));
		game.addPlayer(new Player("player3"));
		game.addPlayer(new Player("player4"));
		game.addPlayer(new Player("player5"));
		game.newGame();

		BlackCardData card;
		card = dealer.drawBlackCard();
		cardHolder.getChildren().add(new Card(card));
		for (int i = 0; i < card.pick; i++) {
			cardHolder.getChildren().add(new Card(dealer.drawWhiteCard()));
		}
	}

	public void clientDiscover(){
		client.discoverServer();
	}

    public void toggleDiscover(ActionEvent actionEvent) {
		if(server.isListening()){
			//Server.stopDiscovery();
			server.stopListening();
			//discoverToggle.setText("Start Discovery");
			discoverToggle.setGraphic(disconnectIcon);
		} else {
			//Server.startDiscovery(8888);
			server.startListening();
			//discoverToggle.setText("Stop Discovery");
			discoverToggle.setGraphic(connectIcon);
		}
    }

	public void stop() {
		server.stopListening();
		client.stopListening();
	}
}
