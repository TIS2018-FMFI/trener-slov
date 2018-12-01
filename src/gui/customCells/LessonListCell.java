package gui.customCells;

import java.io.IOException;

import data.Lesson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

public class LessonListCell extends ListCell<Lesson> {
	
	@FXML
	GridPane gridPane;
	
	@FXML
	Label lessonName;
	
	@FXML
	Button editBtn, deleteBtn;
	
	@Override
	protected void updateItem(Lesson lesson, boolean empty) {
		super.updateItem(lesson, empty);

        if(empty || lesson == null) {
            setGraphic(null);
        } else {
           	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/fxml/lessonListCell.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        lessonName.setText(lesson.getName());
        
        // TODO listenery na btns

        setText(null);
        setGraphic(gridPane);
	}
}
