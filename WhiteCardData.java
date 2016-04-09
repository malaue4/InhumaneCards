package inhumane;

/**
 * Created by martin on 4/5/16.
 */
public class WhiteCardData extends CardData {
	private Boolean blank = false;

	public WhiteCardData(String text, Deck deck) {
		super(text, deck);
		type = Type.white;
	}

	public Boolean getBlank() {
		return blank;
	}

	public void setBlank(Boolean blank) {
		this.blank = blank;
	}
}
