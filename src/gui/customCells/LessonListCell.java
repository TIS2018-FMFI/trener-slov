package gui.customCells;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import application.Main;
import data.Lesson;
import gui.Scenes;
import gui.controllers.LessonController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class LessonListCell extends CustomCellBase<Lesson> {
	
	@FXML
	Pane pane;
	
	@FXML
	Label lessonName;
	
	@FXML
	Button editBtn, deleteBtn;
	
	ListView<Lesson> listview;
	FXMLLoader loader;
	
	public LessonListCell(ListView<Lesson> listview) {
		this.listview = listview;
        loader = new FXMLLoader(getClass().getResource("/gui/fxml/cells/lessonListCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prefWidthProperty().bind(listview.widthProperty().subtract(20));
        setMaxWidth(Control.USE_PREF_SIZE);
	}
	
	@Override
	protected void updateItem(Lesson lesson, boolean empty) {
		super.updateItem(lesson, empty);

        if(empty || lesson == null) {
        	setText(null);
            setGraphic(null);
            return;
        } 
        
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
        
        setText(null);
        setGraphic(pane);
	}

	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub
		
	}
}
