package gui.controllers.cellControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import data.Group;
import data.Item;
import data.Lesson;
import gui.controllers.ControllerBase;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListView;

public class ItemListCellController extends ControllerBase {
	
	ListView<Item> listview;
	FXMLLoader loader;
	Lesson lesson;
	Group group;
	Item item;

	public ItemListCellController(ListView<Item> itemsListView, Lesson lesson, Group group, Item item) {
		this.listview = itemsListView;
		this.lesson = lesson;
		this.group = group;
		this.item = item;
        loader = new FXMLLoader(getClass().getResource("/gui/fxml/cells/itemListCell.fxml"));	// TODO
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setFontSizeToTexts() {
		// TODO Auto-generated method stub

	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public Node getGraphics() {
		// TODO Auto-generated method stub
		return null;
	}

}
