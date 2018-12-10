package gui.controllers.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.Scenes;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainMenuController extends ControllerBase {
	
	@FXML
	Button startBtn, editBtn, configBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		startBtn.setOnMouseClicked(e -> {
			redirect(Scenes.START_LESSON, e);
		});
		editBtn.setOnMouseClicked(e -> {
			redirect(Scenes.LESSON_LIST, e);
		});
		configBtn.setOnMouseClicked(e -> {
			openConfigDialog();
		});
		setFontSizeToTexts();
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(startBtn, fontSize);
		setFontSizeToNode(editBtn, fontSize);
	}
	
	private void openConfigDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fxml/dialogs/configDialog.fxml"));
        Parent parent = null;
		try {
			parent = fxmlLoader.load();
		} catch (IOException e)  { e.printStackTrace(); }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(600);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.showAndWait();
       
        // after dialog close
        setFontSizeToTexts();
	}
}

