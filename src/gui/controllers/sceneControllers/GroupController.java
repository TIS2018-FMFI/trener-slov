package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import application.Main;
import data.Group;
import data.Item;
import data.Lesson;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.customCells.ItemListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class GroupController extends ControllerBase {
	
	@FXML
	Button backBtn, newItemBtn, okBtn;
	
	@FXML
	Label nameLabel;
	
	@FXML
	TextField name, search;
	
	@FXML
	ListView<Item> itemsListView;
	
	Lesson lesson;
	Group group;
	boolean isNewGroup;
	ObservableList<Item> itemObservableList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		backBtn.setOnMouseClicked(e -> backToParentLesson(e));	
		okBtn.setOnMouseClicked(e -> saveGroup(e));
		search.textProperty().addListener((observable, oldValue, newValue) -> filterItems(newValue) );
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(backBtn, fontSize);
		setFontSizeToNode(newItemBtn, fontSize);
		setFontSizeToNode(okBtn, fontSize);
		setFontSizeToNode(nameLabel, fontSize);
		setFontSizeToNode(name, fontSize);
		setFontSizeToNode(search, fontSize);
	}

	public void setGroup(Lesson lesson, Group group) {
		this.lesson = lesson;
		this.group = group;
		isNewGroup = false;
		name.setText(group.getName());
		listItems();
	}
	
	public void createGroup() {
		// TODO group = new Group("", new ArrayList<>());
		isNewGroup = true;
	}
	
	private void listItems() {
		List<Item> items = group.getItemsInGroup();
		itemObservableList = FXCollections.observableArrayList(items);
		itemsListView.setItems(itemObservableList);
		itemsListView.setCellFactory(group -> new ItemListCell(itemsListView));
	}
	
	private void filterItems(String search) {
		List<Item> items = group.getItemsInGroup();	
		itemObservableList = FXCollections.observableArrayList();
		for (Item item : items) {
			if (true) {		// TODO filter
				itemObservableList.add(item);		
			}
		}
		itemsListView.setItems(itemObservableList);
	}
	
	private void saveGroup(MouseEvent event) {
		group.setName(name.getText());
		if (isNewGroup) {
			lesson.addGroup(group);
		}
		try {
			Main.mainController.saveData();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		backToParentLesson(event);
	}
	
	private void backToParentLesson(MouseEvent event) {
		LessonController controller = (LessonController) redirect(Scenes.LESSON, event);
		controller.setLesson(lesson);
	}

}
