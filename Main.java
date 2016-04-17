package inhumane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Main extends Application {

	FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception{
		URL location = getClass().getResource("resources/layouts/sample.fxml");
		fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
		Parent root = fxmlLoader.load(location.openStream());

        primaryStage.setTitle("Hello World");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		primaryStage.show();
    }


	public void stop(){
		((Controller) fxmlLoader.getController()).stop();
	}

    public static void main(String[] args) {
        launch(args);
    }

	public static Document readXML(InputStream inputStream) {
		Document dom;
		// Make an  instance of the DocumentBuilderFactory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// use the factory to take an instance of the document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			// parse using the builder to get the DOM mapping of the
			// XML file
			dom = db.parse(inputStream);

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

	public Dealer loadDecks(){
		Dealer dealer = new Dealer();

		//Path[] paths = Files.list(Paths.get("./src/inhumane/resources/cards")).toArray(size -> new Path[size]);
		InputStream[] paths = {getClass().getResourceAsStream("/inhumane/resources/cards/black1.xml"),
				getClass().getResourceAsStream("/inhumane/resources/cards/white1.xml")};
		for(InputStream path : paths){
			Document document = readXML(path);
			if(document!=null) {
				Deck deck = new Deck("Main", "Cards Against Humanity");
				NodeList blackdecks = document.getElementsByTagName("blackdeck");
				for (int i = 0; i < blackdecks.getLength(); i++) {
					NodeList blackdeck = blackdecks.item(i).getChildNodes();
					for (int j = 0; j < blackdeck.getLength(); j++) {
						if (blackdeck.item(j).getNodeName().equals("cardData")) {
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
							deck.blackCards.add(new BlackCardData(text, deck, pick, draw));
						}
					}
				}
				NodeList whitedecks = document.getElementsByTagName("whitedeck");
				for (int i = 0; i < whitedecks.getLength(); i++) {
					NodeList whitedeck = whitedecks.item(i).getChildNodes();
					for (int j = 0; j < whitedeck.getLength(); j++) {
						if(whitedeck.item(j).getNodeName().equals("cardData")){
							String text = "";
							NodeList card = whitedeck.item(j).getChildNodes();
							for (int k = 0; k < card.getLength(); k++) {
								if(card.item(k).getNodeName().equals("text")){
									text = card.item(k).getTextContent();
								}
							}
							deck.whiteCards.add(new WhiteCardData(text, deck));
						}
					}
				}
				dealer.blackStack.addAll(deck.blackCards);
				dealer.whiteStack.addAll(deck.whiteCards);
			}
		}
		return dealer;
	}

	static Deck loadDeck(InputStream inputStream){
		Document document = readXML(inputStream);
		Element deckNode = (Element) document.getElementsByTagName("deck").item(0);

		Deck deck = new Deck(deckNode.getAttribute("name"), deckNode.getAttribute("author"));

		NodeList blackCardNodes = deckNode.getElementsByTagName("blackcard");
		for (int i = 0; i < blackCardNodes.getLength(); i++) {
			Element cardNode = (Element) blackCardNodes.item(i);
			deck.add(new BlackCardData(
					cardNode.getTextContent(),
					deck,
					Integer.parseInt(cardNode.getAttribute("pick")),
					Integer.parseInt(cardNode.getAttribute("draw"))
			));
		}
		NodeList whiteCardNodes = deckNode.getElementsByTagName("whitecard");
		for (int i = 0; i < whiteCardNodes.getLength(); i++) {
			Element cardNode = (Element) whiteCardNodes.item(i);
			if(cardNode==null)System.out.println("cardNode is null");
			if(cardNode.getTextContent()==null)System.out.println("cardNode text is null is null");
			deck.add(new WhiteCardData(
					cardNode.getTextContent(),
					deck
			));
		}
		return deck;
	}

	boolean saveDeck(Deck deck){
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("deck");
			doc.appendChild(rootElement);

			// attributes
			rootElement.setAttribute("name", deck.name);
			rootElement.setAttribute("author", deck.author);

			for(BlackCardData blackcard : deck.blackCards){
				Element card = doc.createElement("blackcard");
				card.appendChild(doc.createTextNode(blackcard.getText()));
				card.setAttribute("draw", ""+ blackcard.draw);
				card.setAttribute("pick", ""+ blackcard.pick);
				rootElement.appendChild(card);
			}
			for(WhiteCardData whitecard : deck.whiteCards){
				Element card = doc.createElement("whitecard");
				card.appendChild(doc.createTextNode(whitecard.getText()));
				rootElement.appendChild(card);
			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("/home/martin/Desktop/test.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

			return true;
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (TransformerException e) {
			e.printStackTrace();
			return false;
		}
	}
}
