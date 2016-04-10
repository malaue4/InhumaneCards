package inhumane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Created by martin on 4/5/16.
 *
 */

public class Card extends AnchorPane {
	CardData cardData;
	CardType cardType;

	private Text cardText;
	private Font cardTextFont;
	final Image icon;
	private Text iconText;
	static final Font iconFont = new Font("Arial", 12);
	static final Font specialFont = new Font("Impact", 16);

	enum CardType {
		black,
		white
	}

	enum Special{
		draw,
		pick
	}

	public Card(CardData cardData) {
		this.cardData = cardData;

		icon = new Image(getClass().getResource("/inhumane/resources/images/CAH.png").toExternalForm());
		cardText = new Text(cardData.getText());
		cardTextFont = new Font("Arial", Math.min(22, 18*100/ cardText.getText().length()));
		cardText.setWrappingWidth(200);
		cardText.setFont(cardTextFont);
		setTopAnchor(cardText, 0.0);
		setLeftAnchor(cardText, 0.0);
		setRightAnchor(cardText, 0.0);
		setPrefSize(240, 240);
		setMinSize(240, 240);
		setMaxSize(240, 240);
		setPadding(new Insets(20, 20, 20, 20));
		setStyle("-fx-border-color: black; -fx-border-width: 1;");

		ImageView imageView = new ImageView(icon);
		iconText = new Text("Cards Against Humanity");
		iconText.setFont(iconFont);
		HBox iconPane = new HBox();
		iconPane.setSpacing(3);
		iconPane.getChildren().add(imageView);
		iconPane.getChildren().add(iconText);
		iconPane.setAlignment(Pos.CENTER_LEFT);
		setBottomAnchor(iconPane, -15.0);
		setLeftAnchor(iconPane, -10.0);
		getChildren().addAll(cardText, iconPane);


	}

	public Card(WhiteCardData card) {
		this((CardData) card);
		this.cardType = CardType.white;
		setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public Card(BlackCardData card) {
		this((CardData) card);
		this.cardType = CardType.black;
		cardText.setFill(Color.WHITE);
		iconText.setFill(Color.WHITE);
		setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		if(card.draw>0 || card.pick>1){
			iconText.setText("CAH");
			VBox vBox = new VBox();
			vBox.setPrefSize(0, 0);
			vBox.setSpacing(3);
			if(card.draw>0){
				vBox.getChildren().add(createThing(Special.draw, card.draw));
			}
			if(card.pick>1){
				vBox.getChildren().add(createThing(Special.pick, card.pick));
			}
			setRightAnchor(vBox, -10.0);
			setBottomAnchor(vBox, -10.0);
			getChildren().add(vBox);
		}
	}

	HBox createThing(Special label, int number){
		HBox layout = new HBox();
		layout.setAlignment(Pos.CENTER_RIGHT);
		layout.setSpacing(3);

		//The cardText next to the circle
		Text text = new Text(label.toString().toUpperCase());
		text.setFont(specialFont);
		text.setFill(Color.WHITE);
		layout.getChildren().add(text);

		//The circle and number
		StackPane stackPane = new StackPane();
		Circle circle = new Circle(13);
		circle.setFill(Color.WHITE);
		Text textNumber = new Text("" + number);
		textNumber.setFont(specialFont);
		//textNumber.setStyle("-fx-cardTextFont-weight: bold;");
		stackPane.getChildren().addAll(
				circle,
				textNumber
		);
		layout.getChildren().add(stackPane);
		return layout;
	}
}
