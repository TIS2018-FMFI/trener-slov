package gui.customCells;

import data.Item;
import javafx.scene.control.ListView;

public class ItemListCell extends CustomCellBase<Item> {
	
	ListView<Item> itemsListView;

	public ItemListCell(ListView<Item> itemsListView) {
		this.itemsListView = itemsListView;
	}
	
	// TODO
}
