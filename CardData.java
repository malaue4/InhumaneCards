package inhumane;

/**
 * Created by martin on 4/5/16.
 *
 */

public abstract class CardData {
	private String text;
	Deck deck;
	Type type;

	enum Type{
		black,
		white
	}

	public CardData(String text, Deck deck) {
		this.text = text;
		this.deck = deck;
	}

	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return text;
	}
}
