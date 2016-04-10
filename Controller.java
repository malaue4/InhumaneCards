package inhumane;

import inhumane.net.Server;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;

public class Controller {
    public ListView playerListView;
	public static ToggleButton toggleDiscover;

    public void toggleDiscover(ActionEvent actionEvent) {
        if(((ToggleButton) actionEvent.getSource()).isSelected()){
			if(Server.startDiscovery(8888)){
				((ToggleButton) actionEvent.getSource()).setSelected(false);
			}
        } else {
            Server.stopDiscovery();
        }
    }
}
