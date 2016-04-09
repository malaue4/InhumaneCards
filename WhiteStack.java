package inhumane;

import java.util.Collections;
import java.util.Stack;

/**
 * Created by martin on 4/5/16.
 *
 */
public class WhiteStack extends Stack<WhiteCardData> {
	void shuffle(){
		Collections.shuffle(this);
	}
}
