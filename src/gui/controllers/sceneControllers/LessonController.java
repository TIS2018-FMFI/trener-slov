package gui.controllers.sceneControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.bind.JAXBException;

import application.Main;
import gui.Scenes;
import gui.controllers.ControllerBase;
import gui.customCells.GroupListCell;
import data.Group;
import data.Lesson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LessonController extends ControllerBase {
	
	@FXML
	Button backBtn, newGroupBtn, okBtn;
	
	@FXML
	Label nameLabel;
	
	@FXML
	TextField name, search;
	
	@FXML
	ListView<Group> groupsListView;
	
	Lesson lesson;
	boolean isNewLesson;
	ObservableList<Group> groupObservableList;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setFontSizeToTexts();
		backBtn.setOnMouseClicked(e -> redirect(Scenes.LESSON_LIST, e));
		okBtn.setOnMouseClicked(e -> saveLesson(e));
		newGroupBtn.setOnMouseClicked(e -> newGroup(e));
		search.textProperty().addListener((observable, oldValue, newValue) -> filterGroups(newValue) );
	}

	@Override
	protected void setFontSizeToTexts() {
		int fontSize = Main.mainController.getFontSize();
		setFontSizeToNode(backBtn, fontSize);
		setFontSizeToNode(newGroupBtn, fontSize);
		setFontSizeToNode(okBtn, fontSize);
		setFontSizeToNode(nameLabel, fontSize);
		setFontSizeToNode(name, fontSize);
		setFontSizeToNode(search, fontSize);
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
		isNewLesson = false;
		name.setText(lesson.getName());
		listGroups();
	}
	

	public void createLesson() {
		lesson = new Lesson("", new ArrayList<>());
		isNewLesson = true;
	}
	
	private void listGroups() {
		List<Group> groups = lesson.getGroupsInLesson();	
		groupObservableList = FXCollections.observableArrayList(groups);
		groupsListView.setItems(groupObservableList);
		groupsListView.setCellFactory(group -> new GroupListCell(groupsListView, lesson));
	}
	
	private void filterGroups(String search) {
		List<Group> groups = lesson.getGroupsInLesson();	
		groupObservableList = FXCollections.observableArrayList();
		for (Group group : groups) {
			if (group.getName().toLowerCase().contains(search.toLowerCase())) {
				groupObservableList.add(group);
			}
		}
		groupsListView.setItems(groupObservableList);
	}
	
	private void saveLesson(MouseEvent event) {
		lesson.setName(name.getText());
		updateGroupsOrder();
		if (isNewLesson) {
			Main.mainController.addLesson(lesson);
		}
		try {
			Main.mainController.saveData();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		redirect(Scenes.LESSON_LIST, event);
	}
	
	private void updateGroupsOrder() {
		for (int i = 0; i < groupObservableList.size(); i++) {
			Group group = groupObservableList.get(i);
			group.setOrder(i+1);
		}	
	}

	private void newGroup(MouseEvent event) {
		GroupController controller = (GroupController) redirect(Scenes.GROUP, event);
		controller.createGroup(lesson);
	}

}
