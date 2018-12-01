package gui.customCells;

import java.io.IOException;

import gui.Scenes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public abstract class CustomCellBase<T> extends ListCell<T> {
	
	public FXMLLoader redirect(Scenes SCENE, MouseEvent event){
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/scenes/" + SCENE + ".fxml"));
		Scene scene = null;
		try {
			scene = new Scene(loader.load(), node.getScene().getWidth(), node.getScene().getHeight());
		} catch (IOException e) { e.printStackTrace(); }
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle(SCENE.getTitle());
		stage.show();
		return loader;
	}
	
	protected abstract void setFontSizeToTexts();
	
}
