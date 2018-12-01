package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.Scenes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController extends ControllerBase {
	
	@FXML
	Button startBtn, editBtn, configBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startBtn.setOnMouseClicked(e -> {
			// TODO
		});
		editBtn.setOnMouseClicked(e -> {
			redirect(Scenes.LESSON_LIST, e);
		});
		configBtn.setOnMouseClicked(e -> {
			// TODO
		});
	}

	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub
	}
}

