package inhumane;

import java.util.Collections;
import java.util.Stack;


/**
 * Created by martin on 4/5/16.
 *
 */
class BlackStack extends Stack<BlackCardData> {
	void shuffle(){
		Collections.shuffle(this);
	}
}
