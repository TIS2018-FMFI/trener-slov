package gui.customCells;

import java.util.List;

import data.Lesson;
import gui.controllers.cellControllers.ExportLessonCellController;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;

public class ExportLessonCell extends CustomCellBase<Lesson> {

	ExportLessonCellController controller;
	ListView<Lesson> listview;
	
	public ExportLessonCell(ListView<Lesson> listview, List<ExportLessonCell> cells) {
		this.listview = listview;
		cells.add(this);
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
        	controller = new ExportLessonCellController(lesson);
        }
        controller.update();
        setText(null);
        setGraphic(controller.getGraphics());
        prefWidthProperty().bind(listview.widthProperty().subtract(20));
        setMaxWidth(Control.USE_PREF_SIZE);
	}
	
	public boolean isChecked() {
		return (controller != null && controller.isChecked());
	}
	
	public Lesson getLesson() {
		return controller.getLesson();
	}
}
