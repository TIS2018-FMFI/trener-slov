package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.Scenes;
import gui.controllers.ControllerBase;
import data.Lesson;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class LessonController extends ControllerBase {
	
	@FXML
	Button backBtn, newGroupBtn, okBtn;
	
	@FXML
	TextField name;
	
	@FXML
	ListView<Lesson> groupsListView;
	
	Lesson lesson;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		backBtn.setOnMouseClicked(e -> redirect(Scenes.LESSON_LIST, e));
	}

	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
		name.setText(lesson.getName());
	}

}
