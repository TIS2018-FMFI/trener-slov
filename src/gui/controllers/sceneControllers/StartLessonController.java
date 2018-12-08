package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.customCells.StartLessonCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class StartLessonController extends ControllerBase {
	
	@FXML
	BorderPane pane;
	
	@FXML 
	Button backBtn, startBtn;
	
	@FXML
	ListView<Lesson> listView;
	
	ObservableList<Lesson> lessonObservableList;
	
	public StartLessonController() {
		List<Lesson> lessons = Main.mainController.getLessons();	
		lessonObservableList = FXCollections.observableArrayList(lessons);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		backBtn.setOnMouseClicked(e -> redirect(Scenes.MAIN_MENU, e));
		startBtn.setOnMouseClicked(e -> startLesson(e));
		listView.setItems(lessonObservableList);
		listView.setCellFactory(lesson -> new StartLessonCell(listView));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(backBtn, fontSize);
		setFontSizeToNode(startBtn, fontSize);
	}

	private void startLesson(MouseEvent e) {
		Lesson selectedLesson = listView.getSelectionModel().getSelectedItem();
		if (selectedLesson != null) {
			StartModeController controller = (StartModeController) redirect(Scenes.START_MODE, e);
			controller.setLesson(selectedLesson);
		}
	}
}
