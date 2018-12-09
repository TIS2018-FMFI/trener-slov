package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.customCells.ExportLessonCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ExportLessonsController extends ControllerBase {
	
	@FXML
	BorderPane pane;
	
	@FXML
	Button backBtn, exportBtn;
	
	@FXML
	ListView<Lesson> listView;
	
	ObservableList<Lesson> lessonObservableList;
	List<ExportLessonCell> cells;
	
	public ExportLessonsController() {
		List<Lesson> lessons = Main.mainController.getLessons();	
		lessonObservableList = FXCollections.observableArrayList(lessons);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		cells = new ArrayList<>();
		listView.setItems(lessonObservableList);
		listView.setCellFactory(lesson -> new ExportLessonCell(listView, cells));
		backBtn.setOnMouseClicked(e -> redirect(Scenes.LESSON_LIST, e));

		exportBtn.setOnMouseClicked(e -> exportLessons(e));
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(backBtn, fontSize);
		setFontSizeToNode(exportBtn, fontSize);
	}
	

	private void exportLessons(MouseEvent e) {
		ArrayList<Lesson> lessonsToExport = new ArrayList<>();
		for (ExportLessonCell cell : cells) {
			if (cell.isChecked()) {
				lessonsToExport.add(cell.getLesson());
			}
		}
		Main.mainController.exportLesson(lessonsToExport);
		redirect(Scenes.LESSON_LIST, e);
	}
}
