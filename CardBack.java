package inhumane;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
 */
public class CardBack extends BorderPane {
	Card card;
	Font font;
	static final Font iconFont = new Font("Arial", 10);
	static final Font pickFont = new Font("Impact", 16);
	final Image icon;

	enum Special{
		draw,
		pick
	}

	public CardBack(Card card) {
		icon = new Image(getClass().getResource("/inhumane/resources/images/CAH.png").toExternalForm());
		this.card = card;
		Text text = new Text(card.getText());
		font = new Font("Arial", Math.min(22, 18*100/text.getText().length()));
		text.setWrappingWidth(200);
		text.setFont(font);
		setTop(text);
		setPrefSize(240, 240);
		setMinSize(240, 240);
		setMaxSize(240, 240);
		setMargin(text, new Insets(20, 20, 20, 20));
		setStyle("-fx-border-color: black; -fx-border-width: 1;");

		ImageView imageView = new ImageView(icon);
		imageView.setAccessibleText("Image of a black card and two white cards");
		Text iconText = new Text("CAH");
		iconText.setFont(iconFont);
		BorderPane iconPane = new BorderPane();
		iconPane.setLeft(imageView);
		iconPane.setCenter(iconText);
		iconPane.setAlignment(iconText, Pos.CENTER_LEFT);
		setBottom(iconPane);
	}

	public CardBack(WhiteCard card) {
		this((Card) card);
		setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public CardBack(BlackCard card) {
		this((Card) card);
		Text text = (Text) getTop();
		text.setFill(Color.WHITE);
		Text iconText = (Text) ((BorderPane)getBottom()).getCenter();
		iconText.setFill(Color.WHITE);
		setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

		TilePane tilePane = new TilePane(Orientation.HORIZONTAL);
		tilePane.setPrefHeight(10);
		tilePane.setMaxWidth(40);
		if(card.draw>0){
			tilePane.getChildren().add(createThing(Special.draw, card.draw));
		}
		if(card.pick>1){
			tilePane.getChildren().add(createThing(Special.pick, card.pick));
		}
		((BorderPane)getBottom()).setRight(tilePane);
	}

	BorderPane createThing(Special label, int number){
		BorderPane layout = new BorderPane();
		Text text = new Text(label.toString().toUpperCase());
		text.setFont(pickFont);
		text.setStyle("-fx-font-weight: bold;");
		text.setFill(Color.WHITE);
		layout.setCenter(text);
		layout.setAlignment(text, Pos.CENTER_RIGHT);
		Circle circle = new Circle(13);
		circle.setFill(Color.WHITE);
		StackPane stackPane = new StackPane();
		Text textNumber = new Text("" + number);
		textNumber.setFont(pickFont);
		textNumber.setStyle("-fx-font-weight: bold;");
		stackPane.getChildren().addAll(
				circle,
				textNumber
		);
		stackPane.setPadding(new Insets(3,23,3,3));
		layout.setRight(stackPane);
		return layout;
	}
}
