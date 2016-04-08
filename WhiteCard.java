package inhumane;

/**
 * Created by martin on 4/5/16.
 */
public class WhiteCard extends Card {
	private Boolean blank = false;

	public WhiteCard(String text) {
		super(text);
	}

	public Boolean getBlank() {
		return blank;
	}

	public void setBlank(Boolean blank) {
		this.blank = blank;
	}
}
