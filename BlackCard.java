package inhumane;

/**
 * Created by martin on 4/5/16.
 *
 */

class BlackCard extends Card {
	int pick;
	int draw;

	BlackCard(String text, int pick, int draw) {
		super(text);
		this.pick = pick;
		this.draw = draw;
	}

	@Override
	public String toString() {
		String text = getText();
		String special = (draw>0?"\nDraw "+draw+" cards":"") + (pick>1?"\nPick "+pick+" cards":"");
		return text+special;
	}
}
