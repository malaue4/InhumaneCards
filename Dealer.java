package inhumane;

/**
 * Created by martin on 4/5/16.
 */
public class Dealer {
	BlackDeck blackDeck;
	WhiteDeck whiteDeck;

	public Dealer() {
		blackDeck = new BlackDeck();
		whiteDeck = new WhiteDeck();
	}

	WhiteCard drawWhiteCard(){
		return (WhiteCard) whiteDeck.remove(0);
	}

	BlackCard drawBlackCard(){
		return (BlackCard) blackDeck.remove(0);
	}

	public String printTest() {
		String text = "";

		blackDeck.shuffle();
		whiteDeck.shuffle();

		BlackCard blackCard = drawBlackCard();
		System.out.println(blackCard.toString());
		text += blackCard.toString();
		for (int i = 0; i < blackCard.pick; i++) {
			WhiteCard whiteCard = drawWhiteCard();
			System.out.println(whiteCard.getText());
			text += String.format("%n%s", whiteCard.getText());
		}
		return text;
	}
}
