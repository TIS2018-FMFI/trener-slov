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
import javafx.scene.input.MouseEvent;

public class LessonListController extends ControllerBase {
	
	@FXML
	Button backBtn, newLessonBtn, importBtn, exportBtn;
	
	@FXML
	ListView<Lesson> lessonsListView;
	
	ObservableList<Lesson> lessonObservableList;
	
	public LessonListController() {
		loadLessons();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		lessonsListView.setItems(lessonObservableList);
		lessonsListView.setCellFactory(lesson -> new LessonListCell(lessonsListView));
		backBtn.setOnMouseClicked(e -> redirect(Scenes.MAIN_MENU, e));
		newLessonBtn.setOnMouseClicked(e -> newLesson(e));
		
		importBtn.setOnMouseClicked(e -> importLessons());
		exportBtn.setOnMouseClicked(e -> redirect(Scenes.EXPORT_LESSONS, e));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(backBtn, fontSize);
		setFontSizeToNode(newLessonBtn, fontSize);
		setFontSizeToNode(importBtn, fontSize);
		setFontSizeToNode(exportBtn, fontSize);
	}
	
	private void newLesson(MouseEvent event) {
		LessonController controller = (LessonController) redirect(Scenes.LESSON, event);
		controller.createLesson();
	}
	
	private void loadLessons() {
		List<Lesson> lessons = Main.mainController.getLessons();	
		lessonObservableList = FXCollections.observableArrayList(lessons);
	}
	
	private void importLessons() {
		Main.mainController.importLesson();
		loadLessons();
		lessonsListView.setItems(lessonObservableList);
		lessonsListView.setCellFactory(lesson -> new LessonListCell(lessonsListView));
	}
}
