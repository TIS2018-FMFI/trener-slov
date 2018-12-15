package gui.controllers.dialogControllers;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import application.Main;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StationaryBicycleModeConfigDialogController extends ControllerBase {
	
	@FXML
	Button startBtn;
	
	@FXML
	Label numberOfAnswersPlayLabel, pauseDurationInSecsLabel, modeDurationInSecsLabel;
	
	@FXML
	TextField numberOfAnswersPlayValue, pauseDurationInSecsValue, modeDurationInSecsValue;
	
	AtomicInteger numberOfAnswersPlay;
	AtomicInteger pauseDurationInSecs;
	AtomicInteger modeDurationInSecs;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		numberOfAnswersPlayValue.textProperty().addListener((observable, oldValue, newValue) -> {
        	onChangeListenerOnValue(numberOfAnswersPlayValue, numberOfAnswersPlay, newValue);
		});	
		pauseDurationInSecsValue.textProperty().addListener((observable, oldValue, newValue) -> {
        	onChangeListenerOnValue(pauseDurationInSecsValue, pauseDurationInSecs, newValue);
		});	
		modeDurationInSecsValue.textProperty().addListener((observable, oldValue, newValue) -> {
        	onChangeListenerOnValue(modeDurationInSecsValue, modeDurationInSecs, newValue);
		});	
		startBtn.setOnMouseClicked(event -> start(event));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		List<Node> nodes = Arrays.asList(startBtn, numberOfAnswersPlayLabel, pauseDurationInSecsLabel, modeDurationInSecsLabel, 
							numberOfAnswersPlayValue, pauseDurationInSecsValue, modeDurationInSecsValue);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setNumbers(AtomicInteger numberOfAnswersPlay, AtomicInteger pauseDurationInSecs,
			AtomicInteger modeDurationInSecs) {
		this.numberOfAnswersPlay = numberOfAnswersPlay;
		this.pauseDurationInSecs = pauseDurationInSecs;
		this.modeDurationInSecs = modeDurationInSecs;
	}

	
	private void start(MouseEvent event) {
		if (numberOfAnswersPlayValue.getText().equals("")
				||  pauseDurationInSecsValue.getText().equals("")) {
			return;
		}
		Node source = (Node)  event.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}

	private void onChangeListenerOnValue(TextField textField, AtomicInteger integer, String newValue) {
        if (!newValue.matches("\\d*")) {
        	textField.setText(newValue.replaceAll("[^\\d]", ""));
        }
        String text = textField.getText();
        if (!text.isEmpty()) {
        	integer.set(Integer.parseInt(text));
        }
	}
}
