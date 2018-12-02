package gui.customCells;

import data.Group;
import data.Lesson;
import gui.controllers.cellControllers.GroupListCellController;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;

public class GroupListCell extends CustomCellBase<Group>{
	
	ListView<Group> listview;
	Lesson lesson;
	GroupListCellController controller;
	
	public GroupListCell(ListView<Group> listview, Lesson lesson) {
		this.listview = listview;
		this.lesson = lesson;
	}
	
	@Override
	protected void updateItem(Group group, boolean empty) {
		super.updateItem(group, empty);
        if(empty || group == null) {
        	setText(null);
            setGraphic(null);
            return;
        } 
        
        if (controller == null) {
        	controller = new GroupListCellController(listview, lesson, group);
        }
        controller.update();
        setText(null);
        setGraphic(controller.getGraphics());
        prefWidthProperty().bind(listview.widthProperty().subtract(20));
        setMaxWidth(Control.USE_PREF_SIZE);
	}
}
