package gui.controllers.dialogControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import application.Main;
import data.Configuration;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ConfigDialogController extends ControllerBase {
	
	@FXML
	TextField font;
	
	@FXML
	Button okBtn;
	
	int fontSize;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fontSize = Main.mainController.getFontSize();
		font.setText(fontSize + "");
		
		font.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue.matches("\\d*")) {
	            font.setText(newValue.replaceAll("[^\\d]", ""));
	        }
		});
		
		okBtn.setOnMouseClicked(event -> {
			//TODO over font
			int newFont = Integer.parseInt(font.getText());
			Main.mainController.setFontSize(newFont);
			try {
				Main.mainController.saveData();
			} catch (JAXBException exception) {
				exception.printStackTrace();
			}
			Node  source = (Node)  event.getSource(); 
	        Stage stage  = (Stage) source.getScene().getWindow();
	        stage.close();
		});
	}


	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub
	}
	
}
