package gui.customCells;

import data.Lesson;
import gui.controllers.cellControllers.StartLessonCellController;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;

public class StartLessonCell extends CustomCellBase<Lesson> {
	
	ListView<Lesson> listview;
	StartLessonCellController controller;
	
	public StartLessonCell(ListView<Lesson> listview) {
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
        	controller = new StartLessonCellController(lesson);
        }
        controller.update();
        setText(null);
        setGraphic(controller.getGraphics());
        prefWidthProperty().bind(listview.widthProperty().subtract(20));
        setMaxWidth(Control.USE_PREF_SIZE);
	}

}
