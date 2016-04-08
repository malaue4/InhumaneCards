package inhumane;


import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by martin on 4/5/16.
 */

public class Deck extends ArrayList<Card>{
	String name;
	void shuffle(){
		Collections.shuffle(this);
	}
}
