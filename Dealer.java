package inhumane;

/**
 * Created by martin on 4/5/16.
 *
 */
public class Dealer {
	BlackStack blackStack;
	WhiteStack whiteStack;

	public Dealer() {
		blackStack = new BlackStack();
		whiteStack = new WhiteStack();
	}

	public boolean addDeck(Deck deck) {
		if(deck == null){
			System.out.println("something got fucked, the deck is null");
			return false;
		} else {
			blackStack.addAll(deck.blackCards);
			whiteStack.addAll(deck.whiteCards);
			return true;
		}
	}

	void shuffleCards(){
		shuffleBlackCards();
		shuffleWhiteCards();
	}
	void shuffleBlackCards(){
		blackStack.shuffle();
	}
	void shuffleWhiteCards(){
		whiteStack.shuffle();
	}

	WhiteCardData drawWhiteCard(){
		WhiteCardData cardData = whiteStack.remove(0);
		whiteStack.add(cardData);
		return cardData;
	}

	BlackCardData drawBlackCard(){
		BlackCardData cardData = blackStack.remove(0);
		blackStack.add(cardData);
		return cardData;
	}

	public String printTest() {
		String text = "";

		blackStack.shuffle();
		whiteStack.shuffle();

		BlackCardData blackCard = drawBlackCard();
		System.out.println(blackCard.toString());
		text += blackCard.toString();
		for (int i = 0; i < blackCard.pick; i++) {
			WhiteCardData whiteCard = drawWhiteCard();
			System.out.println(whiteCard.getText());
			text += String.format("%n%s", whiteCard.getText());
		}
		return text;
	}
}
