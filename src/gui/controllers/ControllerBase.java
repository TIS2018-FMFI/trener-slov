package gui.controllers;

import java.io.IOException;
import java.util.List;

import gui.Scenes;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class ControllerBase implements Initializable {
	
	public ControllerBase redirect(Scenes SCENE, MouseEvent event){
		Node node = (Node) event.getSource();
		return redirect(SCENE, node);
	}
	
	public ControllerBase redirect(Scenes SCENE, Node node){
		Stage stage = (Stage) node.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/scenes/" + SCENE + ".fxml"));
		Scene scene = null;
		try {
			scene = new Scene(loader.load(), node.getScene().getWidth(), node.getScene().getHeight());
			scene.getStylesheets().add(getClass().getResource("/gui/styles.css").toExternalForm());
		} catch (IOException e) { e.printStackTrace(); }
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle(SCENE.getTitle());
		stage.show();
		return loader.getController();
	}
	
	protected abstract void setFontSizeToTexts();
	
	protected void setFontSizeToNode(Node node, int fontSize) {
		node.setStyle(String.format("-fx-font-size: %dpx;", fontSize));
	}
	
	protected void setFontSizeToAllNodes(List<Node> nodes, int fontSize) {
		for (Node node : nodes) {
			setFontSizeToNode(node, fontSize);
		}
	}
}
