package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts(); 
		// TODO riad mod
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
}
