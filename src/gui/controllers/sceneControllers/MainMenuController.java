package gui.controllers.sceneControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.controllers.dialogControllers.ConfigDialogController;
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
			// TODO
		});
		editBtn.setOnMouseClicked(e -> {
			redirect(Scenes.LESSON_LIST, e);
		});
		configBtn.setOnMouseClicked(e -> {
			openConfigDialog();
		});
	}

	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub
	}
	
	private void openConfigDialog() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/fxml/dialogs/configDialog.fxml"));
        Parent parent = null;
		try {
			parent = fxmlLoader.load();
		} catch (IOException e)  { e.printStackTrace(); }

        Scene scene = new Scene(parent, 300, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
	}
}

