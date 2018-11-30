package gui.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class ControllerBase {
	
	public FXMLLoader redirect(String fxmlName, MouseEvent event) throws IOException {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/" + fxmlName));
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
		stage.show();
		return loader;
	}
	
	protected abstract void setFontSizeToTexts();
	
}
