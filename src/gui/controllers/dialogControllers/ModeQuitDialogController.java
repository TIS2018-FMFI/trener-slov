package gui.controllers.dialogControllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import application.Main;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ModeQuitDialogController extends ControllerBase {
	
	@FXML
	Label label;
	
	@FXML
	Button quitBtn, againBtn;
	
	AtomicBoolean startAgain;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		quitBtn.setOnMouseClicked(e -> quit());
		againBtn.setOnMouseClicked(e -> startAgain());
	}

	private void startAgain() {
		startAgain.set(true);
		close();
	}

	private void quit() {
		startAgain.set(false);
		close();
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(label, fontSize);
		setFontSizeToNode(quitBtn, fontSize);
		setFontSizeToNode(againBtn, fontSize);
	}

	public void setResultObject(AtomicBoolean startAgain) {
		this.startAgain = startAgain;
	}
	
	private void close() {
	    Stage stage  = (Stage) quitBtn.getScene().getWindow();
	    stage.close();
	}

}
