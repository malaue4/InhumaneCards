package inhumane;

/**
 * Created by martin on 4/5/16.
 *
 */

class BlackCardData extends CardData {
	int pick;
	int draw;

	BlackCardData(String text, Deck deck, int pick, int draw) {
		super(text, deck);
		this.pick = pick;
		this.draw = draw;
		this.type = Type.black;
	}

	@Override
	public String toString() {
		String text = getText();
		String special = (draw>0?"\nDraw "+draw+" cards":"") + (pick>1?"\nPick "+pick+" cards":"");
		return text+special;
	}
}
