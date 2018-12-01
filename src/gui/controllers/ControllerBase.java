package gui.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class ControllerBase implements Initializable {
	
	public FXMLLoader redirect(String fxmlName, MouseEvent event){
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/" + fxmlName + ".fxml"));
		Scene scene = null;
		try {
			scene = new Scene(loader.load(), node.getScene().getWidth(), node.getScene().getHeight());
		} catch (IOException e) { e.printStackTrace(); }
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		return loader;
	}
	
	protected abstract void setFontSizeToTexts();
	
}
