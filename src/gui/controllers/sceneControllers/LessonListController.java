package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.customCells.LessonListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class LessonListController extends ControllerBase {
	
	@FXML
	Button backBtn, newLessonBtn, importBtn, exportBtn;
	
	@FXML
	ListView<Lesson> lessonsListView;
	
	ObservableList<Lesson> lessonObservableList;
	
	public LessonListController() {
		List<Lesson> lessons = Main.mainController.getLessons();	
		lessonObservableList = FXCollections.observableArrayList(lessons);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lessonsListView.setItems(lessonObservableList);
		lessonsListView.setCellFactory(lesson -> new LessonListCell(lessonsListView));
		backBtn.setOnMouseClicked(e -> redirect(Scenes.MAIN_MENU, e));
		
		// TODO
		//newLessonBtn.setOnMouseClicked(e -> redirect(Scenes., e));
		//importBtn.setOnMouseClicked(e -> Main.mainController.importLesson());
		//exportBtn.setOnMouseClicked(e -> redirect(Scenes., e));
	}

	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub
	}
}
