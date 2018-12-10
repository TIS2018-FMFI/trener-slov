package gui.customCells;

import data.Lesson;
import gui.controllers.cellControllers.LessonListCellController;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;

public class LessonListCell extends CustomCellBase<Lesson> {
	
	ListView<Lesson> listview;
	LessonListCellController controller;
	
	public LessonListCell(ListView<Lesson> listview) {
		this.listview = listview;
	}
	
	@Override
	protected void updateItem(Lesson lesson, boolean empty) {
		super.updateItem(lesson, empty);
        if(empty || lesson == null) {
        	setText(null);
            setGraphic(null);
            return;
        } 
        
        if (controller == null) {
        	controller = new LessonListCellController(listview, lesson);
        }
        controller.update();
        setText(null);
        setGraphic(controller.getGraphics());
        prefWidthProperty().bind(listview.widthProperty().subtract(20));
        setMaxWidth(Control.USE_PREF_SIZE);
	}
}
