package gui.controllers.sceneControllers;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import data.Item;
import gui.ModeTimer;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import modes.GameMode;

public class ModeController extends ControllerBase {
	
	@FXML
	Button showAnswerBtn, wrongBtn, rightBtn, playSoundBtn;
	
	@FXML
	Label text;
	
	@FXML
	BorderPane imageParent;

	ImageView imageView;
	
	GameMode mode;
	ModeTimer timer;
	Item item;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		initializeBtns();
	}
	
	private void start() {
		timer = new ModeTimer(this);
		timer.start();

		item = mode.next(null);
		checkItem();
		showQuestion();
	}
	
	private void initializeBtns() {
		showAnswerBtn.setOnMouseClicked(e -> showAnswer());
		wrongBtn.setOnMouseClicked(e -> wrong());
		rightBtn.setOnMouseClicked(e -> right());
		playSoundBtn.setOnMouseClicked(e -> playSound());
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		List<Node> nodes = Arrays.asList(showAnswerBtn, text);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setMode(GameMode mode) {
		this.mode = mode;
		start();
	}
	
	private void showQuestion() {
		showAnswerBtn.setVisible(true);
		rightBtn.setVisible(false);
		wrongBtn.setVisible(false);
		text.setText( (item.getQuestionText() == null) ? "" : item.getQuestionText() );
		playSoundBtn.setVisible( (item.getQuestionSound() != null) );
		if (item.getQuestionImg() != null) {
			setImage(item.getQuestionImg());
		}
	}
	
	private void showAnswer() {
		showAnswerBtn.setVisible(false);
		rightBtn.setVisible(true);
		wrongBtn.setVisible(true);
		text.setText( (item.getAnswerText() == null) ? "" : item.getAnswerText() );
		playSoundBtn.setVisible( (item.getAnswerSound() != null) );
		if (item.getAnswerImg() != null) {
			setImage(item.getAnswerImg());
		}
	}
	
	private void right() {
		item = mode.next(true);
		checkItem();
		showQuestion();
	}
	
	private void wrong() {
		item = mode.next(false);
		checkItem();
		showQuestion();
	}
	
	private void checkItem() {
		if (item == null) {
			showStats();
		}
	}
	
	private void setImage(String imagePath) {
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
		imageView.fitHeightProperty().bind(imageParent.heightProperty());
		imageParent.setCenter(imageView);
	}
	
	private void playSound() {
		String soundPath = null;
		if (showAnswerBtn.isVisible()) {
			soundPath = item.getQuestionSound();
		}
		else {
			soundPath = item.getAnswerSound();
		}
		if (soundPath != null) {
			Main.mainController.playSound(soundPath);
		}
	}
	
	private void showStats() {
		System.out.println("Mod skoncil lebo uz nema ziadny item a ide sa ukazat statistika (este sa neukaze lebo nie je naimlementovana a preto to hodi error z ModeControlleru) !!! ");
		// TODO skoci a ukaz statistiku
	}
}
