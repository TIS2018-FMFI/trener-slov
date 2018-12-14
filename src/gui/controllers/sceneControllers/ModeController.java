package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

import application.Main;
import data.Item;
import gui.ModeTimer;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import modes.GameMode;

public class ModeController extends ControllerBase {
	
	@FXML
	Button showAnswerBtn, wrongBtn, rightBtn, playSoundBtn;
	
	@FXML
	Label text;
	
	@FXML ImageView image;
	
	GameMode mode;
	ModeTimer timer;
	Item item;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		initializeBtns();
		
		timer = new ModeTimer();
		timer.start();

		item = mode.next(null);
		if (item == null) {
			showStats();
		}
		showQuestion();
	}
	
	private void initializeBtns() {
		// TODO 
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		List<Node> nodes = Arrays.asList(showAnswerBtn, text, image);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setMode(GameMode mode) {
		this.mode = mode;
	}
	
	private void showQuestion() {
		// TODO ukaze otazku
	}
	
	private void showAnswer() {
		// TODO ukaze odpoved
	}
	
	private void right() {
		// TODO ked vedel
	}
	
	private void wrong() {
		// TODO ked nevedel
	}
	
	private void showStats() {
		// TODO skoci a ukaz statistiku
	}
}
