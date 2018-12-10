package gui.controllers.cellControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import data.Lesson;
import gui.controllers.ControllerBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ExportLessonCellController extends ControllerBase {
	
	@FXML
	Pane pane;
	
	@FXML
	Label lessonName;
	
	@FXML
	CheckBox checkBox;

	FXMLLoader loader;
	Lesson lesson;

	public ExportLessonCellController(Lesson lesson) {
		this.lesson = lesson;
        loader = new FXMLLoader(getClass().getResource("/gui/fxml/cells/exportLessonListCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(lessonName, fontSize);
		setFontSizeToNode(checkBox, fontSize);
	}

	public void update() {
		setFontSizeToTexts();
		lessonName.setText(lesson.getName());
	}

	public Node getGraphics() {
		return pane;
	}
	
	public boolean isChecked(){
		return checkBox.isSelected();
	}

	public Lesson getLesson() {
		return lesson;
	}

}
