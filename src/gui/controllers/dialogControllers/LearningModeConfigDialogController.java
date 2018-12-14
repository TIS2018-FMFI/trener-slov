package gui.controllers.dialogControllers;

import java.net.URL;
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

public class LearningModeConfigDialogController extends ControllerBase {
	
	@FXML
	Button startBtn;
	
	@FXML 
	Label label;
	
	@FXML 
	TextField numberInput;
	
	AtomicInteger number;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		numberInput.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue.matches("\\d*")) {
	        	numberInput.setText(newValue.replaceAll("[^\\d]", ""));
	        }
	        String text = numberInput.getText();
	        if (!text.isEmpty()) {
	        	number.set(Integer.parseInt(text));
	        }
		});	
		startBtn.setOnMouseClicked(event -> start(event));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(startBtn, fontSize);
		setFontSizeToNode(label, fontSize);
		setFontSizeToNode(numberInput, fontSize);
	}
	
	private void start(MouseEvent event) {
		if (numberInput.getText().equals("")) {
			return;
		}
		Node  source = (Node)  event.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}

	public void setNumber(AtomicInteger number) {		
		this.number = number;
	}
}
