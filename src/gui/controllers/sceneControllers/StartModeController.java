package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import modes.Dictate;
import modes.Examination;
import modes.GameMode;
import modes.Learning;
import modes.StationaryBicycle;

public class StartModeController extends ControllerBase {
	
	@FXML
	BorderPane pane;
	
	@FXML 
	Button backBtn, learningModeBtn, ExaminationModeBtn, DictateModeBtn, StationaryBicycleModeBtn;
	
	Lesson lesson;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		backBtn.setOnMouseClicked(e -> redirect(Scenes.START_LESSON, e));
		learningModeBtn.setOnMouseClicked(e -> startMode(Learning.class, e));
		ExaminationModeBtn.setOnMouseClicked(e -> startMode(Exception.class, e));
		DictateModeBtn.setOnMouseClicked(e -> startMode(Dictate.class, e));
		StationaryBicycleModeBtn.setOnMouseClicked(e -> startMode(StationaryBicycle.class, e));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		List<Node> nodes = Arrays.asList(backBtn, learningModeBtn, ExaminationModeBtn, DictateModeBtn, StationaryBicycleModeBtn);
		setFontSizeToAllNodes(nodes, fontSize);
	}

	public void setLesson(Lesson selectedLesson) {
		lesson = selectedLesson;	
	}
	
	@SuppressWarnings("rawtypes")
	private void startMode(Class modeClass, MouseEvent e) {
		GameMode mode = null;
		String title = "";
		if (modeClass.equals(Learning.class)) {
			title = "Učenie";		
			/** 
			 * Ak sa vyberie mód učenie, vyskočí okno, v ktorom sa bude dať nastaviť počet opakovaní skupín v lekcii, 
			*  po ktorom sa skupina označí ako prebraná. 
			*/
			int num = 0;
			mode = new Learning(num);
		}
		else if (modeClass.equals(Exception.class)) {
			title = "Skúšanie";
			mode = new Examination();
		}
		else if (modeClass.equals(Dictate.class)) {
			title = "Diktát";
			mode = new Dictate();
		}
		else if (modeClass.equals(StationaryBicycle.class)) {
			title = "Stacinárny bicykel";
			mode = new StationaryBicycle();
			/**
			 * Ak sa vyberie mód stacionárny bicykel, vyskočí okno, v ktorom sa bude dať nastaviť dĺžka trvania módu v sekundách, 
			 * počet prehraní zvuku odpovede (ak má odpoveď zvuk) a dĺžka trvania zobrazenia otázky v sekundách. 
			 */
		}
		
		title += " - " + lesson.getName();
		Scenes modeScene = Scenes.MODE;
		modeScene.setTitle(title);
		ModeController controller = (ModeController) redirect(modeScene, e);
		controller.setMode(mode);
	}
}
