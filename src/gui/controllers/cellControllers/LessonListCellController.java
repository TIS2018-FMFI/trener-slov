package gui.controllers.cellControllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
		setFontSizeToTexts();
        lessonName.setText(lesson.getName());	
        
        editBtn.setOnMouseClicked(e -> {
        	LessonController controller = (LessonController) redirect(Scenes.LESSON, e); 
        	controller.setLesson(lesson);
        });
        deleteBtn.setOnMouseClicked(e -> delete());
	}

	public Node getGraphics() {
		return pane;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(editBtn, fontSize);
		setFontSizeToNode(deleteBtn, fontSize);
		setFontSizeToNode(lessonName, fontSize);
	}
	
	private void delete() {
    	if (!confirm()) {
    		return;
    	}
    	Main.mainController.removeLesson(lesson);
    	try {
			Main.mainController.saveData();
		} catch (JAXBException e1) {e1.printStackTrace();}
    	listview.getItems().remove(lesson);
	}
	
	private boolean confirm() {
	      Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle("");
	      alert.setHeaderText("Naozaj chcete vymazať lekciu ?");
	      alert.setContentText(lesson.getName());

	      Optional<ButtonType> option = alert.showAndWait();
	      if (option.get() == ButtonType.OK) {
	         return true;
	      }
	      return false;
	}
}
