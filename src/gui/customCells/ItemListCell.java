package gui.customCells;

import data.Group;
import data.Item;
import data.Lesson;
import gui.controllers.cellControllers.GroupListCellController;
import gui.controllers.cellControllers.ItemListCellController;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;

public class ItemListCell extends CustomCellBase<Item> {
	
	ListView<Item> itemsListView;
	Lesson lesson;
	Group group;
	ItemListCellController controller;

	public ItemListCell(ListView<Item> itemsListView, Lesson lesson, Group group) {
		this.itemsListView = itemsListView;
		this.lesson = lesson;
		this.group = group;
	}
	
	@Override
	protected void updateItem(Item item, boolean empty) {
		super.updateItem(item, empty);
        if(empty || item == null) {
        	setText(null);
            setGraphic(null);
            return;
        } 
        
        if (controller == null) {
        	controller = new ItemListCellController(itemsListView, lesson, group, item);
        }
        controller.update();
        setText(null);
        setGraphic(controller.getGraphics());
        prefWidthProperty().bind(itemsListView.widthProperty().subtract(20));
        setMaxWidth(Control.USE_PREF_SIZE);
	}
}
