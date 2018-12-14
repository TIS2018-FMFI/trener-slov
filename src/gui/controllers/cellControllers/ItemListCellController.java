package gui.controllers.cellControllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import application.Main;
import data.Group;
import data.Item;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.controllers.sceneControllers.ItemController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;

public class ItemListCellController extends ControllerBase {
	
	@FXML
	Pane pane;
	
	@FXML
	Label itemName;
	
	@FXML
	Button editBtn, deleteBtn;
	
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
        loader = new FXMLLoader(getClass().getResource("/gui/fxml/cells/itemListCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(editBtn, fontSize);
		setFontSizeToNode(deleteBtn, fontSize);
		setFontSizeToNode(itemName, fontSize);
	}

	public void update() {
		setFontSizeToTexts();
		itemName.setText(item.getQuestionText());	
		
        editBtn.setOnMouseClicked(e -> {
        	ItemController controller = (ItemController) redirect(Scenes.ITEM, e); 
        	controller.setItem(lesson, group, item);
        });
        deleteBtn.setOnMouseClicked(e -> delete());
	}

	public Node getGraphics() {
		return pane;
	}
	
	private void delete() {
    	if (!confirm()) {
    		return;
    	}
    	group.removeItem(item);
    	try {
			Main.mainController.saveData();
		} catch (JAXBException e1) {e1.printStackTrace();}
    	listview.getItems().remove(item);
	}
	
	private boolean confirm() {
	      Alert alert = new Alert(AlertType.CONFIRMATION);
	      alert.setTitle("");
	      alert.setHeaderText("Naozaj chcete vymazať túto položku ?");

	      Optional<ButtonType> option = alert.showAndWait();
	      if (option.get() == ButtonType.OK) {
	         return true;
	      }
	      return false;
	}
}
