package inhumane;

/**
 * Created by martin on 4/5/16.
 */

public class Card {
	private String text;

	public Card(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
