package gui.controllers.dialogControllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import application.Main;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfigDialogController extends ControllerBase {
	
	@FXML 
	Label label;
	
	@FXML
	TextField font;
	
	@FXML
	Button okBtn;
	
	int fontSize;
	
	final int MIN_FONT_SIZE = 10;
	final int MAX_FONT_SIZE = 30;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fontSize = Main.mainController.getFontSize();
		setFontSizeToTexts();
		font.setText(fontSize + "");
		
		font.textProperty().addListener((observable, oldValue, newValue) -> {
	        if (!newValue.matches("\\d*")) {
	            font.setText(newValue.replaceAll("[^\\d]", ""));
	        }
		});
		
		okBtn.setOnMouseClicked(event -> onOkClick(event));
	}
	
	private void onOkClick(MouseEvent event){
		int newFont = Integer.parseInt(font.getText());
		if (newFont < MIN_FONT_SIZE || newFont > MAX_FONT_SIZE) {
			showAlert();
			return;
		}
		Main.mainController.setFontSize(newFont);
		try {
			Main.mainController.saveData();
		} catch (JAXBException exception) {
			exception.printStackTrace();
		}
		close(event);
	}

	private void close(MouseEvent event) {
		Node  source = (Node)  event.getSource(); 
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
	}
	
    private void showAlert() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("");
 
        alert.setHeaderText(null);
        alert.setContentText("Minimálna velkosť fontu je " + MIN_FONT_SIZE + " a maximálna " + MAX_FONT_SIZE + ".");
 
        alert.showAndWait();
    }

	@Override
	protected void setFontSizeToTexts() {
		setFontSizeToNode(label, fontSize);
		setFontSizeToNode(font, fontSize);
		setFontSizeToNode(okBtn, fontSize);
	}
	
}
