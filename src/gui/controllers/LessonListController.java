package gui.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import data.Lesson;
import gui.customCells.LessonListCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class LessonListController extends ControllerBase {
	
	@FXML
	ListView<Lesson> lessonsListView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Lesson> lessons = Main.mainController.getLessons();
		System.out.println(lessons);
		lessonsListView.setItems(FXCollections.observableArrayList(lessons));
		lessonsListView.setCellFactory(cell -> new LessonListCell());
	}

	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub

	}
}
