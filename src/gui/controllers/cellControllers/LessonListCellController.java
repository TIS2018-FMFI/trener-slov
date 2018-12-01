package gui.controllers.cellControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import application.Main;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.controllers.sceneControllers.LessonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class LessonListCellController extends ControllerBase {
	
	@FXML
	Pane pane;
	
	@FXML
	Label lessonName;
	
	@FXML
	Button editBtn, deleteBtn;
	
	ListView<Lesson> listview;
	FXMLLoader loader;
	Lesson lesson;
	
	
	public LessonListCellController(ListView<Lesson> listview, Lesson lesson) {
		this.listview = listview;
		this.lesson = lesson;
        loader = new FXMLLoader(getClass().getResource("/gui/fxml/cells/lessonListCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void update() {
        lessonName.setText(lesson.getName());	
        
        editBtn.setOnMouseClicked(e -> {
        	FXMLLoader loader = redirect(Scenes.LESSON, e); 
        	LessonController controller = loader.getController();
        	controller.setLesson(lesson);
        });
        deleteBtn.setOnMouseClicked(e -> {
        	Main.mainController.removeLesson(lesson);
        	try {
				Main.mainController.saveData();
			} catch (JAXBException e1) {e1.printStackTrace();}
        	listview.getItems().remove(lesson);
        });
	}

	public Node getGraphics() {
		return pane;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub
	}
}
