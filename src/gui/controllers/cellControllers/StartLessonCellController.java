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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class StartLessonCellController extends ControllerBase {
	
	@FXML
	BorderPane pane;
	
	@FXML
	Label lessonName;
	
	Lesson lesson;
	FXMLLoader loader;

	public StartLessonCellController(Lesson lesson) {
		this.lesson = lesson;
        loader = new FXMLLoader(getClass().getResource("/gui/fxml/cells/startLessonCell.fxml"));
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
	}

	public void update() {
		setFontSizeToTexts();
		lessonName.setText(lesson.getName());
	}

	public Node getGraphics() {
		return pane;
	}

}
