package inhumane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

	Dealer dealer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
		Scene scene = new Scene(root, 1000, 325);
		primaryStage.setScene(scene);

		dealer = loadDecks();
		dealer.whiteDeck.shuffle();
		dealer.blackDeck.shuffle();

		BlackCard card;
		do {
			card = dealer.drawBlackCard();
		} while (card.pick<2);
		((TilePane)root.getChildrenUnmodifiable().get(0)).getChildren().add(new CardBack(card));
		for (int i = 0; i < card.pick; i++) {
			((TilePane)root.getChildrenUnmodifiable().get(0)).getChildren().add(new CardBack(dealer.drawWhiteCard()));
		}

		((TextFlow)root.getChildrenUnmodifiable().get(1)).getChildren().add(new Text(dealer.printTest()));
		primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

	public static Document readXML(String xml) {
		Document dom;
		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the
			// XML file
			dom = db.parse(xml);

			return dom;
		} catch (ParserConfigurationException pce) {
			System.out.println(pce.getMessage());
		} catch (SAXException se) {
			System.out.println(se.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}

		return null;
	}

	public static Dealer loadDecks(){
		Dealer dealer = new Dealer();
		try {

			Path[] paths = Files.list(Paths.get("./src/inhumane/resources/cards")).toArray(size -> new Path[size]);
			for(Path path : paths){
				Document document = readXML(path.toString());
				if(document!=null) {
					NodeList blackdecks = document.getElementsByTagName("blackdeck");
					for (int i = 0; i < blackdecks.getLength(); i++) {
						NodeList blackdeck = blackdecks.item(i).getChildNodes();
						for (int j = 0; j < blackdeck.getLength(); j++) {
							if (blackdeck.item(j).getNodeName().equals("card")) {
								String text = "";
								int draw = 0, pick = 1;
								NodeList card = blackdeck.item(j).getChildNodes();
								for (int k = 0; k < card.getLength(); k++) {
									if (card.item(k).getNodeName().equals("text")) {
										text = card.item(k).getTextContent();
									} else if (card.item(k).getNodeName().equals("pick")) {
										pick = Integer.decode(card.item(k).getTextContent());
									} else if (card.item(k).getNodeName().equals("draw")) {
										draw = Integer.decode(card.item(k).getTextContent());
									}
								}
								dealer.blackDeck.add(new BlackCard(text, pick, draw));
							}
						}
					}
					NodeList whitedecks = document.getElementsByTagName("whitedeck");
					for (int i = 0; i < whitedecks.getLength(); i++) {
						NodeList whitedeck = whitedecks.item(i).getChildNodes();
						for (int j = 0; j < whitedeck.getLength(); j++) {
							if(whitedeck.item(j).getNodeName().equals("card")){
								String text = "";
								NodeList card = whitedeck.item(j).getChildNodes();
								for (int k = 0; k < card.getLength(); k++) {
									if(card.item(k).getNodeName().equals("text")){
										text = card.item(k).getTextContent();
									}
								}
								dealer.whiteDeck.add(new WhiteCard(text));
							}
						}
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dealer;
	}
}
